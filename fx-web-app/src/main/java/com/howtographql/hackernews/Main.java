package com.howtographql.hackernews;

import java.io.*;
import java.util.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.flywaydb.core.Flyway;

public class Main {
	public static void main(String[] args) throws IOException {
		String settings = "db/settings.properties";
		Properties props = new Properties();
		props.load(Resources.getResourceAsStream(settings));
		
		Flyway flyway = new Flyway();
		flyway.configure(props);
		flyway.repair();
		flyway.migrate();
		
		String resource = "db/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, props);
		try(SqlSession session = sqlSessionFactory.openSession()) {
			LinkMapper mapper = session.getMapper(LinkMapper.class);
			Link link = new Link("abc", "def");
			mapper.create(link);
			List<Link> links = mapper.readAll();
			System.out.println(links);
			mapper.delete(link.getId());
			session.commit();
		}
	}
}
