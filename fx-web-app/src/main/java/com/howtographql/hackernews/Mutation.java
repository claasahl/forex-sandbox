package com.howtographql.hackernews;

import java.time.ZonedDateTime;
import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;
	private final VoteRepository voteRepository;

	public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
		this.voteRepository = voteRepository;
	}

	public Link createLink(String url, String description, DataFetchingEnvironment env) {
		AuthContext context = env.getContext();
		Link newLink = new Link(url, description, context.getUser().map(User::getId).orElse(-1));
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
	
	public Vote createVote(int linkId, DataFetchingEnvironment env) {
		AuthContext context = env.getContext();
		int userId = context.getUser().map(User::getId).orElse(-1);
		if(userId >= 0) {
			ZonedDateTime createdAt = ZonedDateTime.now();
			Vote vote = new Vote(createdAt, userId, linkId);
			return voteRepository.saveVote(vote);
		}
		throw new GraphQLException("not signed in");
	}
}
