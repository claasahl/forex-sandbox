package com.howtographql.hackernews;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/blub")
public class GraphQLWebSocket {
	@OnOpen
	public void onOpen(Session session) {

	}

	@OnMessage
	public void onMessage(String message, Session session) {

	}

	@OnError
	public void onError(Throwable throwable, Session session) {

	}

	@OnClose
	public void onClose(Session session) {

	}
}
