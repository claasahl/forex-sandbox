package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

public class Subscription implements GraphQLRootResolver {
	public Link linkCreated() {
		return null;
	}
}
