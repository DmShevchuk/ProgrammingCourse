package commands;

import collection.CollectionManager;

public interface UsesCollectionManager {
    CollectionManager collectionManager = CollectionManager.getInstance();
}
