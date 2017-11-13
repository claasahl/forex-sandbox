package org.github.claasahl.forex.repository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import javax.persistence.*;
import org.flywaydb.core.Flyway;
import org.github.claasahl.forex.*;
import org.github.claasahl.forex.function.CandleAccumulator;
import org.github.claasahl.forex.generator.*;

public class Main {

	public static void main(String[] args) {
		Flyway fw = new Flyway();
		fw.setDataSource("jdbc:h2:~/forex", "sa", null);
		fw.repair();
		fw.migrate();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("forex");
		incorrectAccumulation(emf);
		//randomAccumulation();
		// candles(emf);
		// rates(emf);
		emf.close();
	}

	private static void incorrectAccumulation(EntityManagerFactory factory) {
		CandleService service = new CandleService(factory);
		OffsetDateTime from = OffsetDateTime.of(2017, 07, 17, 7, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime to = OffsetDateTime.of(2017, 07, 17, 9, 0, 0, 0, ZoneOffset.UTC);
		service.findByDateTime(from, to)
				.filter(c -> c.getSymbol().equals("EURUSD"))
				.doOnNext(c -> System.out.format("%s;%f;%f;%f;%f%n", c.getDateTime(), c.getOpen(), c.getHigh(), c.getLow(), c.getClose()))
				.compose(new CandleAccumulator(Duration.ofHours(1)))
				.doOnNext(System.err::println)
				.blockingSubscribe();
	}

	private static void randomAccumulation() {
		OffsetDateTime midnight = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS);
		Duration duration = Duration.ofMinutes(1);
		Generator<Candle> generator = new Generator<>(() -> duration, new RandomCandles("EURUSD", duration));
		generator.generate(midnight).take(15)
				.doOnNext(System.out::println)
				.compose(new CandleAccumulator(Duration.ofMinutes(5)))
				.doOnNext(System.err::println)
				.blockingSubscribe();
	}

	private static void candles(EntityManagerFactory factory) {
		CandleService service = new CandleService(factory);
		OffsetDateTime midnight = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS);
		Duration duration = Duration.ofHours(1);
		Generator<Candle> generator = new Generator<>(() -> duration, new RandomCandles("EURUSD", duration));
		service.persist(generator.generate(midnight).take(500)).blockingAwait();
		service.findByDateTime(midnight.plusDays(90), midnight.plusDays(91)).doOnNext(System.out::println)
				.blockingSubscribe();
	}

	private static void rates(EntityManagerFactory factory) {
		RateService service = new RateService(factory);
		OffsetDateTime midnight = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS);
		Duration duration = Duration.ofSeconds(45);
		Generator<Rate> generator = new Generator<>(() -> duration, new RandomRates("GBPCHF"));
		service.persist(generator.generate(midnight).take(500)).blockingAwait();
		service.findByDateTime(midnight.plusDays(90), midnight.plusDays(91)).doOnNext(System.out::println)
				.blockingSubscribe();
	}

}
