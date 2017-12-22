package com.howtographql.hackernews;

public class Link {

	private final int id;
	private final String url;
	private final String description;

	public Link(String url, String description) {
		this(-1, url, description);
	}

	public Link(int id, String url, String description) {
		this.id = id;
		this.url = url;
		this.description = description;
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

	@Override
	public String toString() {
		return "Link [id=" + id + ", url=" + url + ", description=" + description + "]";
	}

}
