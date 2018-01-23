package com.howtographql.hackernews;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ServerEndpoint("/graphql")
public class GraphQLWebSocket {
	private final AtomicReference<Disposable> disposable = new AtomicReference<>();

	@OnOpen
	public void onOpen(Session session) {
		info("Websocket: onOpen");
	}

	@OnMessage
	public void onMessage(String graphqlQuery, Session session) {
		info("Websocket: onMessage -- " + graphqlQuery);
		disposable.set(Observable.interval(3333, TimeUnit.MILLISECONDS, Schedulers.io())
			.map(l -> "{\"data\":{\"abc\":3333, \"index\":" + l + "}}")
	        .doOnNext(session.getBasicRemote()::sendText)
	        .doOnNext(this::info)
	        .subscribe());
	}

	@OnError
	public void onError(Throwable throwable, Session session) {
		info("Websocket: onError", throwable);
		dispose();
	}

	@OnClose
	public void onClose(Session session) {
		info("Websocket: onClose");
		dispose();
	}
	
	private void info(String msg) {
		System.err.println("############################## " + msg);
	}
	
	private void info(String msg, Throwable t) {
		info(msg);
		t.printStackTrace();
	}
	
	private void dispose() {
		disposable.updateAndGet(d -> {
			if (d != null)
				d.dispose();
			return null;
		});
	}
}
