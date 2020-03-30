package Server.Models;

import Server.Utilities.Database;
/*
* Allows access to scheduling data.
* */
public class ScheduleModel {
    private Database dbConn;

    /* Constructs the Schedule object
     * @param dbConnection allows access to the database via the connection established at server start*/
    public ScheduleModel(Database dbConnection){
        this.dbConn = dbConnection;
    }
}
