package org.github.claasahl.forex.util;

import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public class DecoratedEmitter<V> implements ObservableEmitter<V> {
	private final ObservableEmitter<V> emitter;

	public DecoratedEmitter(ObservableEmitter<V> emitter) {
		this.emitter = emitter;
	}

	@Override
	public void onNext(V value) {
		emitter.onNext(value);
	}

	@Override
	public void onError(Throwable error) {
		emitter.onError(error);
	}

	@Override
	public void onComplete() {
		emitter.onComplete();
	}

	@Override
	public void setDisposable(Disposable d) {
		emitter.setDisposable(d);
	}

	@Override
	public void setCancellable(Cancellable c) {
		emitter.setCancellable(c);
	}

	@Override
	public boolean isDisposed() {
		return emitter.isDisposed();
	}

	@Override
	public ObservableEmitter<V> serialize() {
		return emitter.serialize();
	}
}
