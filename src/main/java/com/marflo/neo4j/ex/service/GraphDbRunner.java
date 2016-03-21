package com.marflo.neo4j.ex.service;

import com.marflo.neo4j.ex.db.node.Person;
import com.marflo.neo4j.ex.db.relationship.RelationshipTypes;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.Date;

public class GraphDbRunner {
    static final String DB_PATH = "C:\\Users\\Martin\\Documents\\Neo4j\\default.graphdb";
    public static void main(String[] args) {
        File dbFile = new File(DB_PATH);
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( dbFile );
        Node firstNode;
        Node secondNode;
        Relationship relationship;

        try (Transaction tx = graphDb.beginTx()) {

            firstNode = graphDb.createNode(new Person());
            firstNode.setProperty("name", "Martin1");

            secondNode = graphDb.createNode(new Person());
            secondNode.setProperty("name", "Martin2");

            relationship = firstNode.createRelationshipTo(secondNode, RelationshipTypes.KNOWS);
            relationship.setProperty("created", new Date().toString());

            tx.success();
        }

    }
}
