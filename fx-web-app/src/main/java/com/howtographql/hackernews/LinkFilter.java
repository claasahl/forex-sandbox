package com.howtographql.hackernews;

import java.util.Map;

public class LinkFilter {
	private String descriptionContains;
	private String urlContains;

	public LinkFilter() {
	}

	public LinkFilter(String descriptionContains, String urlContains) {
		this.descriptionContains = descriptionContains;
		this.urlContains = urlContains;
	}

	public String getDescriptionContains() {
		return descriptionContains;
	}

	public void setDescriptionContains(String descriptionContains) {
		this.descriptionContains = descriptionContains;
	}

	public String getUrlContains() {
		return urlContains;
	}

	public void setUrlContains(String urlContains) {
		this.urlContains = urlContains;
	}

	@Override
	public String toString() {
		return "LinkFilter [descriptionContains=" + descriptionContains + ", urlContains=" + urlContains + "]";
	}

	public static LinkFilter fromMap(Map<String, Object> map) {
		if(map == null)
			return null;
		String descriptionContains = (String) map.get("descriptionContains");
		String urlContains = (String) map.get("urlContains");
		return new LinkFilter(descriptionContains, urlContains);
	}
}
