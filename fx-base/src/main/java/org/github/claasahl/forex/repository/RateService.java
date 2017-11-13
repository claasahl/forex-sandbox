package org.github.claasahl.forex.repository;

import java.time.OffsetDateTime;
import javax.persistence.*;
import javax.persistence.criteria.*;
import org.github.claasahl.forex.Rate;
import io.reactivex.*;

public class RateService {
	private final EntityManagerFactory factory;

	public RateService(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public Observable<Rate> findByDateTime(OffsetDateTime from, OffsetDateTime to) {
		return Observable.using(factory::createEntityManager, em -> findByDateTimeUsingManager(em, from, to),
				EntityManager::close);
	}

	private static Observable<Rate> findByDateTimeUsingManager(EntityManager em, OffsetDateTime from,
			OffsetDateTime to) {
		return Observable.<Rate>create(emitter -> {
			EntityTransaction et = em.getTransaction();
			try {
				et.begin();
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<RateDraft> query = builder.createQuery(RateDraft.class);
				Root<RateDraft> root = query.from(RateDraft.class);
				query.select(root).orderBy(builder.asc(root.get("dateTime")));
				query.where(builder.between(root.get("dateTime"), from, to));

				em.createQuery(query).getResultList().stream()
						.map(RateDraft::build)
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

	public Completable persist(Observable<Rate> source) {
		return Completable.using(factory::createEntityManager,
				em -> persistUsingManager(em, source),
				EntityManager::close);
	}

	private static Completable persistUsingManager(EntityManager em, Observable<Rate> source) {
		return Completable.using(() -> RateService.getAndBeginTransaction(em),
				et -> Completable.fromObservable(source
						.map(r -> new RateDraft(r, findOrFlyWeight(em, r)))
						.doOnNext(em::persist)
						.doOnComplete(et::commit)),
				RateService::rollbackIfStillActive);
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

	private static SymbolDraft findOrFlyWeight(EntityManager em, Rate rate) {
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SymbolDraft> query = builder.createQuery(SymbolDraft.class);
			Root<SymbolDraft> root = query.from(SymbolDraft.class);
			query.select(root);
			query.where(builder.and(
					builder.equal(root.get("name"), rate.getSymbol()),
					builder.equal(root.get("duration"), 0)));
			SymbolDraft symbolDraft = em.createQuery(query).setMaxResults(1).getSingleResult();
			return symbolDraft;
		} catch (NoResultException e) {
			return SymbolDraft.flyWeight(rate);
		}
	}
}
