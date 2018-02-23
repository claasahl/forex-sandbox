package org.github.claasahl.forex.graphql;

import java.time.*;
import java.time.format.DateTimeFormatter;
import graphql.language.StringValue;
import graphql.schema.*;

public class Scalars {

	public static GraphQLScalarType dateTime = new GraphQLScalarType("DateTime", "DataTime scalar",
			new Coercing<OffsetDateTime, String>() {
				@Override
				public String serialize(Object input) {
					// serialize the ZonedDateTime into string on the way out
					return ((OffsetDateTime) input).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
				}

				@Override
				public OffsetDateTime parseValue(Object input) {
					return OffsetDateTime.parse(input.toString());
				}

				@Override
				public OffsetDateTime parseLiteral(Object input) {
					if (input instanceof StringValue) {
						return OffsetDateTime.parse(input.toString());
					}
					return null;
				}
			});

	public static GraphQLScalarType duration = new GraphQLScalarType("Duration", "Duration scalar",
			new Coercing<Duration, String>() {
				@Override
				public String serialize(Object dataFetcherResult) {
					return ((Duration) dataFetcherResult).toString();
				}

				@Override
				public Duration parseValue(Object input) {
					return Duration.parse(input.toString());
				}

				@Override
				public Duration parseLiteral(Object input) {
					if (input instanceof StringValue) {
						return Duration.parse(input.toString());
					}
					return null;
				}
			});
}
