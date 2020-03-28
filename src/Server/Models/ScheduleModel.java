package Server.Models;

import Server.Utilities.Database;
import org.w3c.dom.Document;

/**
* Allows access to scheduling data.
* */
public class ScheduleModel {
    private Database dbConn;

    /**
     *  Constructs the Schedule object
     * @param dbConnection allows access to the database via the connection established at server start*/
    public ScheduleModel(Database dbConnection){
        this.dbConn = dbConnection;
    }
    // time needs to have 00 added onto it for it to insert correctly (accounts for seconds)

    /**
     * Gets the schedule for a billboard.
     * @param billboardID  the billboard to retrieve the schedule for
     * @return  a Document xml object containing the schedule details. The schedule details are
     * enclosed in a <data></data> root element. There may be more than one schedule item in the
     * Document (ie the billboard shows at 900 and 1500 daily for 30 minutes.
     */
    public Document getBillboardSchedule(int billboardID){
        return null;
    }

    /**
     * Gets the schedule data for every billboard in the system.
     * @return  a Document xml object containing the schedules inside a root <data></data> element
     */
    public Document getSchedule(){
        return null;
    }

    /**
     * Removes a schedule for a specific billboard
     * @param billboardID  the billboard to remove the schedule for
     * @param startTime  the start time of the schedule to be removed
     * @return  an int containing the number of affected rows. Generally going to be 1 or 0.
     */
    public int removeSchedule(int billboardID, int startTime){
        return 0;
    }

    /**
     * Sets a schedule for the specified billboard
     * @param billboardID  the billboard to schedule
     * @param startTime  the start time in 24 hour format (1500 or 900 for 3pm and 9 am respectively)
     * @param duration  the duration to show it in minutes
     * @param recurs  the number of minutes to wait before reshowing the billboard. Set to 0 to display once, 60 for
     *                every hour, and 1440 for every day.
     * @return an int containing the number of rows affected by the operation
     */
    public int setSchedule(int billboardID, int startTime, int duration, int recurs){
        // Needs to a) add 00 to the startTime before inserting (startTime * 100), and b) calculate
        // end_time (startTime + duration)*100
        return 0;
    }
}
