package com.sudobangbang.graphql.repository;

import com.mongodb.client.MongoCollection;
import com.sudobangbang.graphql.model.Comment;
import com.sudobangbang.graphql.model.Scalars;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CommentRepoMongo implements CommentRepo {
    private final MongoCollection<Document> comments;

    public CommentRepoMongo(MongoCollection<Document> comments) {
        this.comments = comments;
    }

    @Override
    public List<Comment> findBySubjectId(String subjectId) {
        List<Comment> list = new ArrayList<>();
        for (Document doc : comments.find(eq("subjectId", subjectId))) {
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
        comments.insertOne(doc);

        return comment(doc);
    }

    private Comment comment(Document doc) {
        return new Comment(
                doc.get("_id").toString(),
                doc.getString("userId"),
                doc.getString("subjectId"),
                doc.getString("text"),
                ZonedDateTime.parse(doc.getString("createdAt"))
        );
    }


}
