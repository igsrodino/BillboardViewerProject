package Server.Controllers;

import Server.Models.ScheduleModel;
import org.w3c.dom.Document;

/**
 * Provides access to scheduling for billboards
 */
public class ScheduleController {
    ScheduleModel model;
    public ScheduleController(ScheduleModel model){
        this.model = model;
    }

    /**
     * Gets the schedule for a specific billboard
     * @param billboardID  the id of the billboard to retrieve the schedule for
     * @return  a Response string
     */
    public String getBillboardSchedule(int billboardID){
        return "Response";
    }

    /**
     * Gets the entire schedule for the system.
     * @return  a Response string, with the list of schedules in the data element
     */
    public String getSchedule(){
        // model.getSchedule
        return "Response";
    }

    /**
     * Gets the id of the Billboard that should be displayed at the time of the request
     * @return  int, the BillboardID of the billboard that should be displayed right now, or 0 if
     * no billboards are scheduled
     */
    public int getCurrentBillboard(){
        return 1;
    }

    /**
     * Sets the schedule for a specific billboard
     * @param billboardID  the billboard to schedule
     * @param startTime  the start time in 24 hour format (1500 or 900 for 3pm and 9 am respectively)
     * @param duration  the duration to show it in minutes
     * @param recurs  the number of minutes to wait before reshowing the billboard. Set to 0 to display once, 60 for
     *                every hour, and 1440 for every day.
     * @return  a Response string
     */
    public String setBillboardSchedule(int billboardID, int startTime, int duration, int recurs){
        // End time is start time + duration
        // StartTime is in 24 hour time to make the math easier.
        return "Response";
    }

    /**
     * Removes a billboard schedule
     * @param billboardID  the billboard to de-schedule
     * @param startTime  the scheduled start time to be removed.
     * @return  a Response string.
     */
    public String removeSchedule(int billboardID, int startTime){
        return "Response";
    }
}
