package com.howtographql.hackernews;

import java.util.List;

public interface LinkMapper {
	void create(Link link);
	Link read(String id);
	List<Link> readAll();
	void update(Link link);
	void delete(String id);
}
