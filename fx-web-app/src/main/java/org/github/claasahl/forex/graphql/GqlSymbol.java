package org.github.claasahl.forex.graphql;

import java.time.Duration;
import org.github.claasahl.forex.database.Symbol;

/**
 * The class {@link GqlSymbol} is exposed through GraphQL. It wraps internal
 * objects and (re-)shapes them as described in the GraphQL schema. This class
 * also adds an extra layer of abstraction which makes it more difficult to leak
 * (i.e. unintentionally expose) details about the wrapped internal objects.
 * 
 * @author Claas Ahlrichs
 *
 */
class GqlSymbol {
	private final Symbol symbol;

	protected GqlSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns this symbol's id.
	 * 
	 * @see Symbol#getId()
	 * @return this symbol's id
	 */
	protected String getId() {
		return symbol.getId();
	}

	/**
	 * Returns this symbol's name.
	 * 
	 * @see Symbol#getName()
	 * @return this symbol's name
	 */
	protected String getName() {
		return symbol.getName();
	}

	/**
	 * Returns this symbol's duration (if any).
	 * 
	 * @see Symbol#getDuration()
	 * @return this symbol's duration
	 */
	protected Duration getDuration() {
		return symbol.getDuration();
	}
}
