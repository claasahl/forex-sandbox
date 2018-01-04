package com.howtographql.hackernews;

import java.util.List;
import org.apache.ibatis.session.*;

public class LinkRepository {
	public Link findById(int id) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			LinkMapper mapper = session.getMapper(LinkMapper.class);
			return mapper.read(id);
		}
	}

	public List<Link> getAllLinks() {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			LinkMapper mapper = session.getMapper(LinkMapper.class);
			return mapper.readAll();
		}
	}

	public void saveLink(Link link) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			LinkMapper mapper = session.getMapper(LinkMapper.class);
			mapper.create(link);
			session.commit();
		}
	}
}
