package com.howtographql.hackernews;

import graphql.schema.DataFetchingEnvironment;

public class LinkResolver {
	private final UserRepository userRepository;

	public LinkResolver(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User postedBy(Link link) {
		if (link.getUserId() <= 0) {
			return null;
		}
		return userRepository.findById(link.getUserId());
	}

	public User postedBy(DataFetchingEnvironment env) {
		Link link = env.getSource();
		return postedBy(link);
	}
}
