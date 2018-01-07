package com.sudobangbang.graphql.resolver;

import com.sudobangbang.graphql.model.SigninPayload;
import com.sudobangbang.graphql.model.User;

//public class SigninResolver implements GraphQLResolver<SigninPayload> {
public class SigninResolver {

    public User user(SigninPayload payload){
        return payload.getUser();
    }
}
