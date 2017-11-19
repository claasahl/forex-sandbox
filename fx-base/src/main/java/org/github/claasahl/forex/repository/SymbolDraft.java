package org.github.claasahl.forex.repository;

import java.time.Duration;
import java.util.*;
import javax.persistence.*;
import org.github.claasahl.forex.*;

@Entity
class SymbolDraft {
	@Id
	private long id;
	@Basic
	private String name;
	@Basic
	private Duration duration;

	public SymbolDraft() {
		name = "EURUSD";
		duration = Duration.ofHours(1);
	}

	public SymbolDraft(Candle candle) {
		name = candle.getSymbol();
		duration = candle.getDuration();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, duration);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SymbolDraft))
			return false;
		SymbolDraft other = (SymbolDraft) obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(duration, other.duration);
	}

	@Override
	public String toString() {
		return "SymbolDraft [id=" + id + ", name=" + name + ", duration=" + duration + "]";
	}

	private static Map<String, SymbolDraft> drafts = new HashMap<>();
	static SymbolDraft flyWeight(Candle candle) {
		SymbolDraft draft = new SymbolDraft();
		draft.setName(candle.getSymbol());
		draft.setDuration(candle.getDuration());
		if(drafts.containsKey(draft.toString()))
			return drafts.get(draft.toString());
		else {
			drafts.putIfAbsent(draft.toString(), draft);
			return draft;
		}
	}
	
	static SymbolDraft flyWeight(Rate rate) {
		SymbolDraft draft = new SymbolDraft();
		draft.setName(rate.getSymbol());
		draft.setDuration(Duration.ZERO);
		if(drafts.containsKey(draft.toString()))
			return drafts.get(draft.toString());
		else {
			drafts.putIfAbsent(draft.toString(), draft);
			return draft;
		}
	}
}
