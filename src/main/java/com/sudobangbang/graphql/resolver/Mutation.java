package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.*;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.UserRepo;
import com.sudobangbang.graphql.repository.VoteRepo;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
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

    @GraphQLMutation
    public Link createLink(String url, String description, @GraphQLRootContext AuthContext context) {
        Link newLink = new Link(url, description, context.getUser().getId());
        linkRepo.saveLink(newLink);
        return newLink;
    }

    public User createUser(String name, AuthData auth){
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepo.saveUser(newUser);
    }

    public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
        User user = userRepo.findByEmail(auth.getEmail());
        if(user.getPassword().equals(auth.getPassword())){
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }

    public Vote createVote(String linkId, String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepo.saveVote(new Vote(now, userId, linkId));
    }
}
