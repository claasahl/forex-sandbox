package com.howtographql.hackernews;

public class Link {
	private final int id;
	private final String url;
	private final String description;
	private final int userId;

	public Link(String url, String description, int userId) {
		this(-1, url, description, userId);
	}

	public Link(int id, String url, String description, int userId) {
		this.id = id;
		this.url = url;
		this.description = description;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "Link [id=" + id + ", url=" + url + ", description=" + description + ", userId=" + userId + "]";
	}
}
