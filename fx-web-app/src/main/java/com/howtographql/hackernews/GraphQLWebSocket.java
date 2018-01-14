package com.howtographql.hackernews;

import java.util.concurrent.atomic.AtomicReference;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import io.reactivex.disposables.Disposable;

@ServerEndpoint("/blub")
public class GraphQLWebSocket {
	private final AtomicReference<Disposable> disposable = new AtomicReference<>();

	@OnOpen
	public void onOpen(Session session) {

	}

	@OnMessage
	public void onMessage(String message, Session session) {

	}

	@OnError
	public void onError(Throwable throwable, Session session) {
		dispose();
	}

	@OnClose
	public void onClose(Session session) {
		dispose();
	}
	
	private void dispose() {
		disposable.updateAndGet(d -> {
			if (d != null)
				d.dispose();
			return null;
		});
	}
}
