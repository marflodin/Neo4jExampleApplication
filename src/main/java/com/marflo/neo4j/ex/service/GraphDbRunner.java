package com.marflo.neo4j.ex.service;

import com.marflo.neo4j.ex.db.node.IdOne;
import com.marflo.neo4j.ex.db.node.IdTwo;
import com.marflo.neo4j.ex.db.node.Person;
import com.marflo.neo4j.ex.db.relationship.RelationshipTypes;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class GraphDbRunner {
    static final String DB_PATH = "C:\\Users\\Martin\\Documents\\Neo4j\\default.graphdb";
    public static void main(String[] args) {
        File dbFile = new File(DB_PATH);
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( dbFile );
        Node idOne;
        Node idTwo;

        for (int i = 0; i < 100; i++) {
            try (Transaction tx = graphDb.beginTx()) {
                int id = i % 10;
                Node firstNode = graphDb.createNode(new Person());
                firstNode.setProperty("id", UUID.randomUUID().toString());
                firstNode.setProperty("name", "Person" + i);


                ResourceIterator<Node> idOneForValue = graphDb.findNodes(new IdOne(), "value", id);
                if (idOneForValue.hasNext()) {
                    idOne = idOneForValue.next();
                } else {
                    idOne = graphDb.createNode(new IdOne());
                    idOne.setProperty("value", id);
                }
                Relationship relationshipOne = firstNode.createRelationshipTo(idOne, RelationshipTypes.HAS);
                relationshipOne.setProperty("created", new Date().toString());

                ResourceIterator<Node> idTwoForValue = graphDb.findNodes(new IdTwo(), "value", id);
                if (idTwoForValue.hasNext()) {
                    idTwo = idTwoForValue.next();
                } else {
                    idTwo = graphDb.createNode(new IdTwo());
                    idTwo.setProperty("value", id);
                }
                Relationship relationshipTwo = firstNode.createRelationshipTo(idTwo, RelationshipTypes.HAS);
                relationshipOne.setProperty("created", new Date().toString());

                tx.success();
            }
        }
    }
}
