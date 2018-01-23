package com.howtographql.hackernews;

import java.time.ZonedDateTime;

public class Vote {
	private final int id;
	private final ZonedDateTime createdAt;
	private final int userId;
	private final int linkId;

	public Vote(ZonedDateTime createdAt, int userId, int linkId) {
		this(-1, createdAt, userId, linkId);
	}

	public Vote(int id, ZonedDateTime createdAt, int userId, int linkId) {
		this.id = id;
		this.createdAt = createdAt;
		this.userId = userId;
		this.linkId = linkId;
	}

	public int getId() {
		return id;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public int getUserId() {
		return userId;
	}

	public int getLinkId() {
		return linkId;
	}
}
