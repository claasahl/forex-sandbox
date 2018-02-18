package org.github.claasahl.forex.database;

import java.util.*;
import org.github.claasahl.forex.model.*;

public class RateRepository {
	private final List<Rate> rates;

	public RateRepository() {
		rates = new ArrayList<>();
	}

	public List<Rate> getRates() {
		return rates;
	}
}
