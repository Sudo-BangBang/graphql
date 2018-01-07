package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.Vote;

import java.util.List;

public interface VoteRepo {
    List<Vote> findByUserId(String userId);
    List<Vote> findByLinkId(String linkId);
    List<Vote> getAllVotes();
    Vote saveVote(Vote vote);
}
