package database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DBManagerTest {

    DBManager dbManager;

    @BeforeEach
    public void setUP(){

    }


    public void createDB() {
        DBManager.getInstance().createDB();
    }

    public void loadDB(){
        DBManager.getInstance().showDataBase();
    }

}
