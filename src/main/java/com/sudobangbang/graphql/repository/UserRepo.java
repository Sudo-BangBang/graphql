package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.User;

public interface UserRepo {

    User findByEmail(String email);

    User findById(String id);

    User saveUser(User user);
}
