package org.github.claasahl.forex;

import io.reactivex.*;

public interface OrderManager {
	Observable<Object> orders();

	Completable createOrder(Object order);

	Completable modifyOrder(Object order);

	Completable deleteOrder(Object order);
}
