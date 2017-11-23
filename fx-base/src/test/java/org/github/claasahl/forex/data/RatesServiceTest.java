package org.github.claasahl.forex.data;

import org.junit.Test;
import io.reactivex.observers.TestObserver;

public class RatesServiceTest {
	@Test
	public void mustIncludeLocalSpiImplementations() {
		TestObserver<String> test = new RatesService().getProviders().test();
		test.assertValue(RandomRatesProvider.class.getSimpleName()).assertComplete();
	}
}
