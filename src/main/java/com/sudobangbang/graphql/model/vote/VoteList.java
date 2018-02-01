package com.sudobangbang.graphql.model.vote;

import java.util.List;

public class VoteList {

    private final Integer count;

    private final List<Vote> votes;

    public VoteList(Integer count, List<Vote> votes) {
        this.count = count;
        this.votes = votes;
    }

    public Integer getCount() {
        return count;
    }

    public List<Vote> getVotes() {
        return votes;
    }
}
