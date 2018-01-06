package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.*;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.UserRepo;
import com.sudobangbang.graphql.repository.VoteRepo;
import graphql.GraphQLException;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Mutation {

    private final LinkRepo linkRepo;
    private final UserRepo userRepo;
    private final VoteRepo voteRepo;

    public Mutation(LinkRepo linkRepo, UserRepo userRepo, VoteRepo voteRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
        this.voteRepo = voteRepo;
    }

    //Using GraphQLArgument can be avoided by adding "-parameters" as a compiler arg
    @GraphQLMutation
    public Link createLink(
            @GraphQLArgument(name = "url", description = "The url of the link") String url,
            @GraphQLArgument(name = "description") String description,
            @GraphQLRootContext AuthContext context) {
        Link newLink = new Link(url, description, context.getUser().getId());
        return linkRepo.saveLink(newLink);
    }

    @GraphQLMutation
    public User createUser(
            @GraphQLArgument(name = "name") String name,
            @GraphQLArgument(name = "auth") AuthData auth){
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepo.saveUser(newUser);
    }

    @GraphQLMutation
    public SigninPayload signinUser(
            @GraphQLArgument(name = "auth") AuthData auth) throws IllegalAccessException {
        User user = userRepo.findByEmail(auth.getEmail());
        if(user.getPassword().equals(auth.getPassword())){
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }

    @GraphQLMutation
    public Vote createVote(
            @GraphQLArgument(name = "linkId") String linkId,
            @GraphQLArgument(name = "userId") String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepo.saveVote(new Vote(now, userId, linkId));
    }
}
