package model;

import java.util.HashSet;
import java.util.Set;

public class Database {
    private static Database database;
    private final Set<Group> groupsById = new HashSet<>();

    private Database() { }

    public synchronized static Database get() {
        if (database == null)
            database = new Database();
        return database;
    }



}
