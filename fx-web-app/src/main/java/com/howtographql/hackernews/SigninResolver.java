package com.howtographql.hackernews;

import graphql.schema.DataFetchingEnvironment;

public class SigninResolver {
	public User user(DataFetchingEnvironment env) {
		SigninPayload payload = env.getSource();
		return payload.getUser();
	}
}
