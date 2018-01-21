package com.howtographql.hackernews;

import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import graphql.schema.DataFetchingEnvironment;
import io.reactivex.*;
import io.reactivex.subjects.*;

public class Subscription {
	private final Subject<Link> links = BehaviorSubject.create();

	public Subscription() {
		Observable.interval(10, TimeUnit.SECONDS)
				.map(num -> new Link(num.intValue(), "url#" + num, "desc#" + num, num.intValue()))
				.doOnNext(links::onNext)
				.subscribe();
	}

	public Publisher<Link> linkCreated(DataFetchingEnvironment env) {
		return links.toFlowable(BackpressureStrategy.BUFFER);
	}
}
