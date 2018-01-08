package com.howtographql.hackernews;

import org.apache.ibatis.session.*;

public class UserRepository {
    public User findByEmail(String email) {
    	SqlSessionFactory factory = SessionFactory.getFactory();
    	try(SqlSession session = factory.openSession()) {
    		UserMapper mapper = session.getMapper(UserMapper.class);
    		return mapper.readByEmail(email);
    	}
    }

    public User findById(int id) {
    	SqlSessionFactory factory = SessionFactory.getFactory();
    	try(SqlSession session = factory.openSession()) {
    		UserMapper mapper = session.getMapper(UserMapper.class);
    		return mapper.read(id);
    	}
    }
    
    public User findByToken(String token) {
    	SqlSessionFactory factory = SessionFactory.getFactory();
    	try(SqlSession session = factory.openSession()) {
    		UserMapper mapper = session.getMapper(UserMapper.class);
    		return mapper.readByToken(token);
    	}
    }
    
    public User saveUser(User user) {
    	SqlSessionFactory factory = SessionFactory.getFactory();
    	try(SqlSession session = factory.openSession()) {
    		UserMapper mapper = session.getMapper(UserMapper.class);
    		mapper.create(user);
    		session.commit();
    		return user;
    	}
    }
}
