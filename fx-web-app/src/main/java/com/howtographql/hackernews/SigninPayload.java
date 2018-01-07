package com.howtographql.hackernews;

public class SigninPayload {
	private final int token;
	private final User user;

	public SigninPayload(int token, User user) {
		this.token = token;
		this.user = user;
	}

	public int getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}
}
