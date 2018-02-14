package com.sudobangbang.graphql.repository;

import com.mongodb.client.MongoCollection;
import com.sudobangbang.graphql.model.comment.Comment;
import com.sudobangbang.graphql.model.Scalars;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class CommentRepoMongo implements CommentRepo {
    private final MongoCollection<Document> comments;

    public CommentRepoMongo(MongoCollection<Document> comments) {
        this.comments = comments;
    }

    @Override
    public List<Comment> findBySubjectId(String subjectId,  Integer skip, Integer first) {
        List<Comment> list = new ArrayList<>();
        for (Document doc : comments.find(eq("subjectId", subjectId)).skip(skip).limit(first)) {
            list.add(comment(doc));
        }
        return list;
    }

    @Override
    public List<Comment> findByUserId(String userId) {
        List<Comment> list = new ArrayList<>();
        for (Document doc : comments.find(eq("userId", userId))) {
            list.add(comment(doc));
        }
        return list;
    }

    @Override
    public Comment findById(String commentId) {
        Document doc = comments.find(eq("_id", new ObjectId(commentId))).first();
        return comment(doc);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Document doc = new Document();
        doc.append("userId", comment.getUserId());
        doc.append("subjectId", comment.getSubjectId());
        doc.append("text", comment.getText());
        doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(comment.getCreatedAt()));
        doc.append("voteTotal", comment.getVoteTotal());
        comments.insertOne(doc);

        return comment(doc);
    }

    @Override
    public Comment updateComment(Comment comment) {
        List<Bson> updates = new ArrayList<>();
        if(comment.getText()!=null){
            updates.add(set("text", comment.getText()));
        }
        if(comment.getVoteTotal()!=null){
            updates.add(set("voteTotal", comment.getVoteTotal()));
        }
        comments.updateOne(eq("_id", new ObjectId(comment.getId())), combine(updates));

        return findById(comment.getId());
    }

    private Comment comment(Document doc) {
        return new Comment(
                doc.get("_id").toString(),
                doc.getString("userId"),
                doc.getString("subjectId"),
                doc.getString("text"),
                ZonedDateTime.parse(doc.getString("createdAt")),
                doc.getInteger("voteTotal")
        );
    }


}
