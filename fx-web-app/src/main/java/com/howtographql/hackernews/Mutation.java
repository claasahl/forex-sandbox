package com.howtographql.hackernews;

import java.time.ZonedDateTime;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation {
	private final LinkRepository linkRepository;
	private final UserRepository userRepository;
	private final VoteRepository voteRepository;

	public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
		this.voteRepository = voteRepository;
	}

	public Link createLink(DataFetchingEnvironment env) {
		String url = env.getArgument("url");
		String description = env.getArgument("description");
		AuthContext context = env.getContext();
		Link newLink = new Link(url, description, context.getUser().map(User::getId).orElse(-1));
		return linkRepository.saveLink(newLink);
	}

	public User createUser(DataFetchingEnvironment env) {
		String name = env.getArgument("name");
		AuthData auth = env.getArgument("env");
		User newUser = new User(name, auth.getEmail(), auth.getPassword());
		return userRepository.saveUser(newUser);
	}

	public SigninPayload signinUser(DataFetchingEnvironment env) {
		AuthData auth = env.getArgument("auth");
		User user = userRepository.findByEmail(auth.getEmail());
		if (user.getPassword().equals(auth.getPassword())) {
			// TODO consider using: https://jwt.io
			user = userRepository.generateToken(user);
			return new SigninPayload(user.getToken(), user);
		}
		throw new GraphQLException("Invalid credentials");
	}

	public Vote createVote(DataFetchingEnvironment env) {
		int linkId = env.getArgument("linkId");
		AuthContext context = env.getContext();
		int userId = context.getUser().map(User::getId).orElse(-1);
		if (userId >= 0) {
			ZonedDateTime createdAt = ZonedDateTime.now();
			Vote vote = new Vote(createdAt, userId, linkId);
			return voteRepository.saveVote(vote);
		}
		throw new GraphQLException("not signed in");
	}
}
