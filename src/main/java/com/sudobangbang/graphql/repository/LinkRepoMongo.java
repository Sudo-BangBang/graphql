package com.sudobangbang.graphql.repository;

import com.mongodb.client.MongoCollection;
import com.sudobangbang.graphql.model.Link;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class LinkRepoMongo implements LinkRepo {
    private final MongoCollection<Document> links;

    public LinkRepoMongo(MongoCollection<Document> links) {
        this.links = links;
    }

    @Override
    public Link findById(String id) {
        Document doc = links.find(eq("_id", new ObjectId(id))).first();
        return link(doc);
    }

    @Override
    public List<Link> getAllLinks() {
        List<Link> allLinks = new ArrayList<>();
        for (Document doc : links.find()) {
            //TODO im going to assume the tutorial has not used the link function for a good reason and the findById will get updated later
            Link link = new Link(
                    doc.get("_id").toString(),
                    doc.getString("url"),
                    doc.getString("description"),
                    doc.getString("postedBy")
            );
            allLinks.add(link);
        }
        return allLinks;
    }

    @Override
    public void saveLink(Link link) {
        Document doc = new Document();
        doc.append("url", link.getUrl());
        doc.append("description", link.getDescription());
        doc.append("postedBy", link.getUserId());
        links.insertOne(doc);
    }

    private Link link(Document doc) {
        return new Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"));
    }
}
