package com.howtographql.hackernews;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import graphql.schema.DataFetchingEnvironment;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.*;

public class Subscription {
	private final Subject<Link> links = BehaviorSubject.create();

	public Publisher<Link> linkCreated(DataFetchingEnvironment env) {
		Observable<Link> linkGenerator = Observable.interval(1, TimeUnit.SECONDS)
				.map(num -> new Link(num.intValue(), "url#" + num, "desc#" + num, num.intValue()))
				.doOnNext(System.out::println)
				.doOnNext(links::onNext);
		AtomicReference<Disposable> disposable = new AtomicReference<>();
		return links
				.doOnSubscribe(d -> disposable.set(linkGenerator.subscribe()))
				.doOnDispose(() -> disposable.updateAndGet(d -> {
					if(d != null)
						d.dispose();
					return null;
				}))
				.toFlowable(BackpressureStrategy.BUFFER);
	}
}
