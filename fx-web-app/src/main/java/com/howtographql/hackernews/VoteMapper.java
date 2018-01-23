package com.howtographql.hackernews;

import java.util.List;

public interface VoteMapper {
	void create(Vote vote);
	Vote read(int id);
	List<Vote> readByUserId(int userId);
	List<Vote> readByLinkId(int linkId);
	List<Vote> readAll();
	void update(Vote vote);
	void delete(int id);
}
