package org.github.claasahl.forex.repository;

import java.time.OffsetDateTime;
import javax.persistence.*;
import javax.persistence.criteria.*;
import org.github.claasahl.forex.Candle;
import io.reactivex.*;

public class CandleService {
	private final EntityManagerFactory factory;

	public CandleService(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public Observable<Candle> findByDateTime(OffsetDateTime from, OffsetDateTime to) {
		return Observable.using(factory::createEntityManager, em -> findByDateTimeUsingManager(em, from, to),
				EntityManager::close);
	}

	private static Observable<Candle> findByDateTimeUsingManager(EntityManager em, OffsetDateTime from,
			OffsetDateTime to) {
		return Observable.<Candle>create(emitter -> {
			EntityTransaction et = em.getTransaction();
			try {
				et.begin();
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<CandleDraft> query = builder.createQuery(CandleDraft.class);
				Root<CandleDraft> root = query.from(CandleDraft.class);
				query.select(root).orderBy(builder.asc(root.get("dateTime")));
				query.where(builder.between(root.get("dateTime"), from, to));

				em.createQuery(query).getResultList().stream()
						.map(CandleDraft::build)
						.forEach(emitter::onNext);

				et.commit();
				emitter.onComplete();
			} catch (Exception e) {
				emitter.onError(e);
			} finally {
				if (et.isActive())
					et.rollback();
			}
		});
	}

	public Completable persist(Observable<Candle> source) {
		return Completable.using(factory::createEntityManager,
				em -> persistUsingManager(em, source),
				EntityManager::close);
	}

	private static Completable persistUsingManager(EntityManager em, Observable<Candle> source) {
		return Completable.using(() -> CandleService.getAndBeginTransaction(em),
						et -> Completable.fromObservable(source
								.map(c -> new CandleDraft(c, findOrFlyWeight(em, c)))
								.doOnNext(em::persist)
								.doOnComplete(et::commit)),
						CandleService::rollbackIfStillActive);
	}

	private static EntityTransaction getAndBeginTransaction(EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		return transaction;
	}
	
	private static void rollbackIfStillActive(EntityTransaction et) {
		if (et.isActive())
			et.rollback();
	}

	private static SymbolDraft findOrFlyWeight(EntityManager em, Candle candle) {
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SymbolDraft> query = builder.createQuery(SymbolDraft.class);
			Root<SymbolDraft> root = query.from(SymbolDraft.class);
			query.select(root);
			query.where(builder.and(
					builder.equal(root.get("name"), candle.getSymbol()),
					builder.equal(root.get("duration"), candle.getDuration().getSeconds())));
			SymbolDraft symbolDraft = em.createQuery(query).setMaxResults(1).getSingleResult();
			return symbolDraft;
		} catch (NoResultException e) {
			return SymbolDraft.flyWeight(candle);
		}
	}
}
