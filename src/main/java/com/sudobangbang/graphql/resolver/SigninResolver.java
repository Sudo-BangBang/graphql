package com.sudobangbang.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.sudobangbang.graphql.model.SigninPayload;
import com.sudobangbang.graphql.model.User;

public class SigninResolver implements GraphQLResolver<SigninPayload> {

    //TODO look into these complex object resolvers more, not 100% on them just yet
    public User user(SigninPayload payload){
        return payload.getUser();
    }
}
