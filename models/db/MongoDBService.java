package models.db;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.List;
import java.util.ArrayList;

public class MongoDBService {
    private final MongoDatabase database;

    public MongoDBService() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase("gameDB");
    }

    public List<Document> loadCountries() {
        MongoCollection<Document> collection = database.getCollection("countries");
        return collection.find().into(new ArrayList<>());
    }

    public List<Document> loadRegions() {
        MongoCollection<Document> collection = database.getCollection("regions");
        return collection.find().into(new ArrayList<>());
    }

    public List<Document> loadLeaders() {
        MongoCollection<Document> collection = database.getCollection("leaders");
        return collection.find().into(new ArrayList<>());
    }
}
