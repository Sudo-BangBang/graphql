package com.sudobangbang.graphql.repository;

import com.mongodb.client.MongoCollection;
import com.sudobangbang.graphql.model.Blog;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BlogRepoMongo implements BlogRepo {
    private final MongoCollection<Document> blogs;

    public BlogRepoMongo(MongoCollection<Document> blogs) {
        this.blogs = blogs;
    }

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> allBlogs = new ArrayList<>();
        for (Document doc : blogs.find()) {
            allBlogs.add(blog(doc));
        }
        return allBlogs;
    }

    @Override
    public Blog findById(String blogId) {
        Document doc = blogs.find(eq("_id", new ObjectId(blogId))).first();
        return blog(doc);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        Document doc = new Document();
        doc.append("name", blog.getName());
        doc.append("description", blog.getDescription());
        blogs.insertOne(doc);

        return blog(doc);
    }

    private Blog blog(Document doc) {
        return new Blog(
                doc.get("_id").toString(),
                doc.getString("name"),
                doc.getString("description"));
    }
}
