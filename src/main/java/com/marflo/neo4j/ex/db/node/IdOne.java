package com.marflo.neo4j.ex.db.node;

import org.neo4j.graphdb.Label;

public class IdOne implements Label {
    @Override
    public String name() {
        return "Id1";
    }
}
