package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;

public class SigninResolver implements GraphQLResolver<SigninPayload> {
	public User user(SigninPayload payload) {
		return payload.getUser();
	}
	
	public User user(DataFetchingEnvironment env) {
		SigninPayload payload = env.getSource();
		return user(payload);
	}
}
