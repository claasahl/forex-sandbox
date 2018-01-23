package com.howtographql.hackernews;

import java.util.List;
import org.apache.ibatis.session.*;

public class VoteRepository {
	public List<Vote> findByUserId(int userId) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			VoteMapper mapper = session.getMapper(VoteMapper.class);
			return mapper.readByUserId(userId);
		}
	}

	public List<Vote> findByLinkId(int linkId) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			VoteMapper mapper = session.getMapper(VoteMapper.class);
			return mapper.readByLinkId(linkId);
		}
	}

	public Vote saveVote(Vote vote) {
		SqlSessionFactory factory = SessionFactory.getFactory();
		try (SqlSession session = factory.openSession()) {
			VoteMapper mapper = session.getMapper(VoteMapper.class);
			mapper.create(vote);
			session.commit();
			return vote;
		}
	}
}
