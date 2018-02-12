package org.github.claasahl.forex.graphql;

import java.time.*;
import java.time.format.DateTimeFormatter;
import graphql.language.StringValue;
import graphql.schema.*;

public class Scalars {

	public static GraphQLScalarType dateTime = new GraphQLScalarType("DateTime", "DataTime scalar", new Coercing() {
		@Override
		public String serialize(Object input) {
			// serialize the ZonedDateTime into string on the way out
			return ((ZonedDateTime) input).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		}

		@Override
		public Object parseValue(Object input) {
			return serialize(input);
		}

		@Override
		public ZonedDateTime parseLiteral(Object input) {
			// parse the string values coming in
			if (input instanceof StringValue) {
				return ZonedDateTime.parse(((StringValue) input).getValue());
			} else {
				return null;
			}
		}
	});
	
	public static GraphQLScalarType duration = new GraphQLScalarType("Duration", "Duration scalar", new Coercing<Duration, String>() {
		@Override
		public String serialize(Object dataFetcherResult) {
			return ((Duration)dataFetcherResult).toString();
		}

		@Override
		public Duration parseValue(Object input) {
			return Duration.parse(input.toString());
		}

		@Override
		public Duration parseLiteral(Object input) {
			return Duration.parse(input.toString());
		}
	});
}
