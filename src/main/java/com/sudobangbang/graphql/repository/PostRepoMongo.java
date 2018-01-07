package com.sudobangbang.graphql.repository;

import com.mongodb.client.MongoCollection;
import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.model.Scalars;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PostRepoMongo implements PostRepo {

    private final MongoCollection<Document> posts;

    public PostRepoMongo(MongoCollection<Document> posts) {
        this.posts = posts;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> allPosts = new ArrayList<>();
        for (Document doc : posts.find()) {
            allPosts.add(post(doc));
        }
        return allPosts;
    }

    @Override
    public List<Post> findByBlogId(String blogId) {
        List<Post> list = new ArrayList<>();
        for (Document doc : posts.find(eq("blogId", blogId))) {
            list.add(post(doc));
        }
        return list;
    }

    @Override
    public List<Post> findByLinkId(String linkId) {
        List<Post> list = new ArrayList<>();
        for (Document doc : posts.find(eq("linkId", linkId))) {
            list.add(post(doc));
        }
        return list;
    }

    @Override
    public Post findById(String postId) {
        Document doc = posts.find(eq("_id", new ObjectId(postId))).first();
        return post(doc);
    }

    @Override
    public Post savePost(Post post) {
        Document doc = new Document();
        doc.append("blogId", post.getBlogId());
        doc.append("linkId", post.getLinkId());
        doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(post.getCreatedAt()));
        posts.insertOne(doc);

        return post(doc);
    }

    private Post post(Document doc) {
        return new Post(
                doc.get("_id").toString(),
                doc.getString("blogId"),
                doc.getString("linkId"),
                ZonedDateTime.parse(doc.getString("createdAt"))
        );
    }
}
