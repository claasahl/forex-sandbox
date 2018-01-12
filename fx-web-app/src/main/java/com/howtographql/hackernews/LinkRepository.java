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
	
	public List<Link> getAllLinks(LinkFilter filter) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			LinkMapper mapper = session.getMapper(LinkMapper.class);
			if(filter == null)
				return mapper.readAll(new LinkFilter());
			return mapper.readAll(filter);
		}
	}

	public Link saveLink(Link link) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			LinkMapper mapper = session.getMapper(LinkMapper.class);
			mapper.create(link);
			session.commit();
			return link;
		}
	}
}
