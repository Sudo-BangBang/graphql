package com.sudobangbang.graphql.repository;

import com.mongodb.client.MongoCollection;
import com.sudobangbang.graphql.model.Scalars;
import com.sudobangbang.graphql.model.vote.Vote;
import org.bson.Document;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class VoteRepoMongo implements VoteRepo {

    private final MongoCollection<Document> votes;

    public VoteRepoMongo(MongoCollection<Document> votes) {
        this.votes = votes;
    }

    @Override
    public List<Vote> getAllVotes() {
        List<Vote> list = new ArrayList<>();

        for (Document doc : votes.find()) {
            list.add(vote(doc));
        }
        return list;
    }

    @Override
    public List<Vote> findByUserId(String userId) {
        List<Vote> list = new ArrayList<>();
        for (Document doc : votes.find(eq("userId", userId))) {
            list.add(vote(doc));
        }
        return list;
    }

    @Override
    public List<Vote> findBySubjectId(String subjectId) {
        List<Vote> list = new ArrayList<>();
        for (Document doc : votes.find(eq("subjectId", subjectId))) {
            list.add(vote(doc));
        }
        return list;
    }

    @Override
    public Vote saveVote(Vote vote) {
        Document doc = new Document();
        doc.append("userId", vote.getUserId());
        doc.append("subjectId", vote.getSubjectId());
        doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(vote.getCreatedAt()));
        votes.insertOne(doc);
        return new Vote(
                doc.get("_id").toString(),
                vote.getCreatedAt(),
                vote.getUserId(),
                vote.getSubjectId());
    }

    private Vote vote(Document doc) {
        return new Vote(
                doc.get("_id").toString(),
                ZonedDateTime.parse(doc.getString("createdAt")),
                doc.getString("userId"),
                doc.getString("subjectId")
        );
    }
}
