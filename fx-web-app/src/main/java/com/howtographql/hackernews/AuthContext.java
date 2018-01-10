package com.howtographql.hackernews;

import java.util.Optional;
import javax.servlet.http.*;
import graphql.servlet.GraphQLContext;

public class AuthContext extends GraphQLContext {
	private final User user;

	public AuthContext(User user, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		super(request, response);
		this.user = user;
	}

	public Optional<User> getUser() {
		return Optional.ofNullable(user);
	}
}
