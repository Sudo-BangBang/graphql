package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.sudobangbang.graphql.endpoint.AuthContext;
import com.sudobangbang.graphql.model.AuthData;
import com.sudobangbang.graphql.model.Link;
import com.sudobangbang.graphql.model.SigninPayload;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.repository.LinkRepo;
import com.sudobangbang.graphql.repository.UserRepo;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepo linkRepo;
    private final UserRepo userRepo;

    public Mutation(LinkRepo linkRepo, UserRepo userRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
    }

    public Link createLink(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
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
}
