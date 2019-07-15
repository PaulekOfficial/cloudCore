package com.paulek.core.basic.data;

import com.paulek.core.basic.database.Database;

public abstract class Storage {

    public abstract void loadFromDatabase(Database database);

    public abstract void init();

    public abstract void saveObjectToDatabase(Object object, Database database);

    public abstract void saveDirtyObjects(Database database);

    public abstract void saveAllToDatabase(Database database);

}
