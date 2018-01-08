package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.GraphQLException;

public class Mutation implements GraphQLRootResolver {
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;

	public Mutation(LinkRepository linkRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
	}

	public Link createLink(String url, String description) {
		Link newLink = new Link(url, description);
		return linkRepository.saveLink(newLink);
	}

	public User createUser(String name, AuthData auth) {
		User newUser = new User(name, auth.getEmail(), auth.getPassword());
		return userRepository.saveUser(newUser);
	}
	
	public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
	    User user = userRepository.findByEmail(auth.getEmail());
	    if (user.getPassword().equals(auth.getPassword())) {
	    	// TODO consider using: https://jwt.io
	    	user = userRepository.generateToken(user);
	        return new SigninPayload(user.getToken(), user);
	    }
	    throw new GraphQLException("Invalid credentials");
	}
}
