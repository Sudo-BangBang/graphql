package com.sudobangbang.graphql.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.sudobangbang.graphql.model.Post;
import com.sudobangbang.graphql.model.Scalars;
import com.sudobangbang.graphql.model.Sort;
import com.sudobangbang.graphql.model.filter.PostFilter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class PostRepoMongo implements PostRepo {

    private final MongoCollection<Document> posts;

    public PostRepoMongo(MongoCollection<Document> posts) {
        this.posts = posts;
    }

    @Override
    public List<Post> getAllPosts(PostFilter filter, Sort sort, int skip, int first) {

        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);


        Bson mongoSort = descending("voteTotal");

        if(sort != null){
            if(sort.getAscending()){
                mongoSort = ascending(sort.getField());
            }else {
                mongoSort = descending(sort.getField());
            }
        }

        List<Post> allPosts= new ArrayList<>();
        FindIterable<Document> documents = mongoFilter.map(posts::find).orElseGet(posts::find).sort(mongoSort);
        for (Document doc : documents.skip(skip).limit(first)) {
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
        doc.append("voteTotal", post.getVoteTotal());
        posts.insertOne(doc);

        return post(doc);
    }

    @Override
    public Post updatePost(Post post) {
        List<Bson> updates = new ArrayList<>();
        if(post.getBlogId()!=null){
            updates.add(set("blogId", post.getBlogId()));
        }
        if(post.getLinkId()!=null){
            updates.add(set("linkId", post.getLinkId()));
        }
        if(post.getVoteTotal()!=null){
            updates.add(set("voteTotal", post.getVoteTotal()));
        }
        posts.updateOne(eq("_id", new ObjectId(post.getId())), combine(updates));

        return findById(post.getId());
    }

    private Post post(Document doc) {
        return new Post(
                doc.get("_id").toString(),
                doc.getString("blogId"),
                doc.getString("linkId"),
                ZonedDateTime.parse(doc.getString("createdAt")),
                doc.getInteger("voteTotal")
        );
    }

    private Bson buildFilter(PostFilter filter) {
        String blogId = filter.getBlogId();
        Bson blogIdCondition = null;
        if (blogId != null && !blogId.isEmpty()) {
            blogIdCondition  = regex("blogId", blogId, "i");
        }
        return blogIdCondition;
    }
}
