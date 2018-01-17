package com.howtographql.hackernews;

import graphql.schema.DataFetchingEnvironment;

public class Subscription {
	public Link linkCreated() {
		return null;
	}

	public Link linkCreated(DataFetchingEnvironment env) {
		return linkCreated();
	}
}
