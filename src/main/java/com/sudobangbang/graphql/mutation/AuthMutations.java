package com.sudobangbang.graphql.mutation;

import com.sudobangbang.graphql.model.AuthData;
import com.sudobangbang.graphql.model.SigninPayload;
import com.sudobangbang.graphql.model.User;
import com.sudobangbang.graphql.repository.UserRepo;
import graphql.GraphQLException;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;

public class AuthMutations {

    private final UserRepo userRepo;

    public AuthMutations(UserRepo userRepo) {
        this.userRepo = userRepo;
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
}
