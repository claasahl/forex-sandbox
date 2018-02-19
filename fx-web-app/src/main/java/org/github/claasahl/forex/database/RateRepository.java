package org.github.claasahl.forex.database;

import java.util.*;
import org.github.claasahl.forex.model.*;

public class RateRepository {
	private final List<Rate> rates;

	public RateRepository() {
		rates = new ArrayList<>();
		rates.add(new InternalRate.Builder().setSymbol("EURUSD").setBid(1.1).setAsk(1.5).build());
		rates.add(new InternalRate.Builder().setSymbol("EURUSD").build());
	}

	public List<Rate> getRates() {
		return rates;
	}
}
