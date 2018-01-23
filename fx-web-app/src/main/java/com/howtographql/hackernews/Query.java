package com.howtographql.hackernews;

import java.util.*;
import graphql.schema.DataFetchingEnvironment;

public class Query {
	private final LinkRepository linkRepository;

	public Query(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}

	public List<Link> allLinks(DataFetchingEnvironment env) {
		//
		// The graphql specification dictates that input object arguments MUST
		// be maps. You can convert them to POJOs inside the data fetcher if that
		// suits your code better
		//
		// See http://facebook.github.io/graphql/October2016/#sec-Input-Objects
		//
		Map<String, Object> filterMap = env.getArgument("filter");
		LinkFilter filter = LinkFilter.fromMap(filterMap);
		return linkRepository.getAllLinks(filter);
	}
}
