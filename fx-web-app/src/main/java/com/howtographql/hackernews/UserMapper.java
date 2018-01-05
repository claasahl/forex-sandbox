package com.howtographql.hackernews;

import java.util.List;

public interface UserMapper {
	void create(User user);
	User read(int id);
	User readByEmail(String email);
	List<User> readAll();
	void update(User user);
	void delete(int id);
}
