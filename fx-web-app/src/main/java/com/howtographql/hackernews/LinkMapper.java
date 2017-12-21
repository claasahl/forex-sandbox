package com.howtographql.hackernews;

import java.util.List;

public interface LinkMapper {
	void create(Link link);
	Link read(int id);
	List<Link> readAll();
	void update(Link link);
	void delete(int id);
}
