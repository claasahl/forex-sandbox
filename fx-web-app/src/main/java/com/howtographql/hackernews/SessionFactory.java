package com.howtographql.hackernews;

import java.io.*;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.flywaydb.core.Flyway;

public class SessionFactory {
	private static SqlSessionFactory factory;

	public static SqlSessionFactory getFactory() {
		synchronized (factory) {
			if (factory == null) {
				try {
					factory = createFactory();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
			return factory;
		}
	}

	private static SqlSessionFactory createFactory() throws IOException {
		String settings = "db/settings.properties";
		Properties props = new Properties();
		props.load(Resources.getResourceAsStream(settings));

		Flyway flyway = new Flyway();
		flyway.configure(props);
		flyway.repair();
		flyway.migrate();

		String resource = "db/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream, props);
	}
}
