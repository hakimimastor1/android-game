package fall2018.csc2017.gamecentre.database;

import fall2018.csc2017.gamecentre.abstractClasses.BoardManager;

abstract class GameDatabaseTools {

    abstract String serializeBoardManager(BoardManager boardManager);

    abstract BoardManager deSerializeBoardManager(String serializedBoardManager);

    abstract BoardManager retrieveBoardManager(String gameKeyValue);

    abstract void saveToDatabase(BoardManager boardManager);
}