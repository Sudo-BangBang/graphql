package com.sudobangbang.graphql.repository;

import com.sudobangbang.graphql.model.vote.Vote;

import java.util.List;

public interface VoteRepo {
    List<Vote> findByUserId(String userId);
    List<Vote> findBySubjectId(String subjectId);
    List<Vote> getAllVotes();
    Vote saveVote(Vote vote);
}
