package com.howtographql.hackernews;

import graphql.schema.DataFetchingEnvironment;

public class SigninResolver {
	public User user(SigninPayload payload) {
		return payload.getUser();
	}

	public User user(DataFetchingEnvironment env) {
		SigninPayload payload = env.getSource();
		return user(payload);
	}
}
