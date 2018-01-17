package com.howtographql.hackernews;

import java.util.List;
import graphql.schema.DataFetchingEnvironment;

public class Query {

	private final LinkRepository linkRepository;

	public Query(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}

	public List<Link> allLinks(LinkFilter filter) {
		return linkRepository.getAllLinks(filter);
	}

	public List<Link> allLinks(DataFetchingEnvironment env) {
		LinkFilter filter = env.getArgument("filter");
		return allLinks(filter);
	}
}
