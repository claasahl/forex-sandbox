package org.github.claasahl.forex;

import io.reactivex.*;

public interface PositionManager {
	Observable<Object> positions();

	Completable modifyPosition(Object position);

	Completable deletePosition(Object position);
}
