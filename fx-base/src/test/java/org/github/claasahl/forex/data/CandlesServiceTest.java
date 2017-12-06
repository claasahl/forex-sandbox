package org.github.claasahl.forex.data;

import org.junit.Test;
import io.reactivex.observers.TestObserver;

public class CandlesServiceTest {
	@Test
	public void mustIncludeLocalSpiImplementations() {
		TestObserver<String> test = new CandlesService().getProviders().test();
		test.assertValue(RandomCandlesProvider.class.getSimpleName()).assertComplete();
	}
}
