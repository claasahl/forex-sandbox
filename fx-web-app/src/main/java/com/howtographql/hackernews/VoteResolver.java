package com.howtographql.hackernews;

import graphql.schema.DataFetchingEnvironment;

public class VoteResolver {
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;

	public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
	}

	public User user(Vote vote) {
		return userRepository.findById(vote.getUserId());
	}
	
	public User user(DataFetchingEnvironment env) {
		Vote vote = env.getSource();
		return user(vote);
	}

	public Link link(Vote vote) {
		return linkRepository.findById(vote.getLinkId());
	}
	
	public Link link(DataFetchingEnvironment env) {
		Vote vote = env.getSource();
		return link(vote);
	}
}
