package org.github.claasahl.forex.quality;

import io.reactivex.Completable;

public abstract class Issue<T> {
	private final String description;
	private final Iterable<T> issueRoot;

	Issue(String description, Iterable<T> issueRoot) {
		this.description = description;
		this.issueRoot = issueRoot;
	}

	public Iterable<T> getIssueRoot() {
		return this.issueRoot;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.description);
		builder.append("; [");
		boolean addSeparator = false;
		for (T item : issueRoot) {
			if (addSeparator)
				builder.append(';');
			builder.append(item.toString());
			addSeparator = true;
		}
		return builder.toString();
	}

	public abstract Completable accept(IssueVisitor visitor);
}
