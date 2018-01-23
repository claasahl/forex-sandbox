package com.howtographql.hackernews;

import static java.util.Collections.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.reactivestreams.*;
import org.reactivestreams.Subscription;
import graphql.*;
import graphql.execution.instrumentation.*;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;

@ServerEndpoint("/graphql")
public class GraphQLWebSocket {
	private final AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();

	@OnOpen
	public void onOpen(Session session) {
		info("Websocket: onOpen");
	}

	@OnMessage
	public void onMessage(String graphqlQuery, Session session) {
		info("Websocket: onMessage -- " + graphqlQuery);

		QueryParameters parameters = QueryParameters.from(graphqlQuery);

		ExecutionInput executionInput = ExecutionInput.newExecutionInput()
				.query(parameters.getQuery())
				.variables(parameters.getVariables())
				.operationName(parameters.getOperationName())
				.build();

		Instrumentation instrumentation = new ChainedInstrumentation(
				singletonList(new TracingInstrumentation()));

		//
		// In order to have subscriptions in graphql-java you MUST use the
		// SubscriptionExecutionStrategy strategy.
		//
		GraphQL graphQL = GraphQL
				.newGraphQL(GraphQLLocalSchema.buildSchema())
				.instrumentation(instrumentation)
				.build();

		ExecutionResult executionResult = graphQL.execute(executionInput);

		Publisher<ExecutionResult> stockPriceStream = executionResult.getData();

		stockPriceStream.subscribe(new Subscriber<ExecutionResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				info("onSubscribe");
				subscriptionRef.set(s);
				request(1);
			}

			@Override
			public void onNext(ExecutionResult er) {
				info("Sending stick price update");
				try {
					Object data = er.getData();
					session.getBasicRemote().sendText(JsonKit.toJsonString(data));
				} catch (Exception e) {
					e.printStackTrace();
				}
				request(1);
			}

			@Override
			public void onError(Throwable t) {
				info("Subscription threw an exception", t);
				try {
					session.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onComplete() {
				info("Subscription complete");
				try {
					session.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
		subscriptionRef.updateAndGet(s -> {
			if (s != null)
				s.cancel();
			return null;
		});
	}

	private void request(int n) {
		Subscription subscription = subscriptionRef.get();
		if (subscription != null) {
			subscription.request(n);
		}
	}
}
