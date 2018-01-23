package com.howtographql.hackernews;

import graphql.schema.DataFetchingEnvironment;

public class VoteResolver {
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;

	public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
	}

	public User user(DataFetchingEnvironment env) {
		Vote vote = env.getSource();
		return userRepository.findById(vote.getUserId());
	}

	public Link link(DataFetchingEnvironment env) {
		Vote vote = env.getSource();
		return linkRepository.findById(vote.getLinkId());
	}
}
