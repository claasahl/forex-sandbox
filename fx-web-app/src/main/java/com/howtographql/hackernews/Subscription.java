package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.schema.DataFetchingEnvironment;

public class Subscription implements GraphQLRootResolver {
	public Link linkCreated() {
		return null;
	}
	
	public Link linkCreated(DataFetchingEnvironment env) {
		return linkCreated();
	}
}
