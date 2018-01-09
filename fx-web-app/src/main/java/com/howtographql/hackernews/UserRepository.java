package com.howtographql.hackernews;

import java.util.UUID;
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
    
    public User generateToken(User user) {
    	SqlSessionFactory factory = SessionFactory.getFactory();
    	try(SqlSession session = factory.openSession()) {
    		UUID uuid = UUID.randomUUID();
    		UserMapper mapper = session.getMapper(UserMapper.class);
    		User updatedUser = new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), uuid.toString());
    		mapper.update(updatedUser);
    		session.commit();
    		return updatedUser;
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
