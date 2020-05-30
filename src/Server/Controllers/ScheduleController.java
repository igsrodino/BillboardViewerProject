package Server.Controllers;

import Server.Models.ScheduleModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides access to scheduling for billboards
 */
public class ScheduleController {
    ScheduleModel model;

    public ScheduleController(ScheduleModel model) {
        this.model = model;
    }

    /**
     * Gets the schedule for a specific billboard
     *
     * @param billboardID the id of the billboard to retrieve the schedule for
     * @return a Response string
     */
    public String getBillboardSchedule(int billboardID) {
        return "Response";
    }

    /**
     * Gets the entire schedule for the system.
     *
     * @return a Response string, with the list of schedules in the data element
     */
    public String getSchedule() {
        String response = "";
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element resp = doc.createElement("response");
            Element data = doc.createElement("data");
            Element type = doc.createElement("type");
            Document schedule = model.getSchedule();

            if (schedule != null) {
                NodeList scheduleList = schedule.getDocumentElement().getChildNodes();
                for (int i = 0; i < scheduleList.getLength(); i++) {
                    Node bb = doc.importNode(scheduleList.item(i), true);
                    data.appendChild(bb);
                }
                type.appendChild(doc.createTextNode("success"));
            } else {
                type.appendChild(doc.createTextNode("failure"));
            }
            doc.appendChild(resp);
            resp.appendChild(data);
            resp.appendChild(type);
            response = BillboardController.convertDocumentToString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Gets the id of the Billboard that should be displayed at the time of the request
     *
     * @return int, the BillboardID of the billboard that should be displayed right now, or -1 if
     * no billboards are scheduled
     */
    public int getCurrentBillboard() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String startTime = sdf.format(now);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return model.getCurrentBillboard(day, startTime);
    }

    /**
     * Sets the schedule for a specific billboard
     *
     * @param billboardID the billboard to schedule
     * @param startTime   the start time in 24 hour format (1500 or 900 for 3pm and 9 am respectively)
     * @param duration    the duration to show it in minutes
     * @param recurs      the number of minutes to wait before reshowing the billboard. Set to 0 to display once, 60 for
     *                    every hour, and 1440 for every day.
     * @param weekday
     * @return a Response string
     */
    public String setBillboardSchedule(int billboardID, int startTime, int duration, int recurs,
                                       int weekday) {
        // End time is start time + duration
        // StartTime is in 24 hour time to make the math easier.
        if(startTime>2400){
            return "<response>\n" +
                    "\t<type>error</type>\n" +
                    "\t<data>Start time cannot be greater than 2400</data>\n"+
                    "</response>";
        }
        weekday = weekday%7;
        if (weekday <=0){
            weekday = 1;
        }

        Calendar cal =
                new Calendar.Builder().setTimeOfDay((startTime/100), (startTime%100), 0 ).build();
        cal.add(Calendar.MINUTE, duration);
        int endTime = ((cal.get(Calendar.HOUR_OF_DAY)*100)+cal.get(Calendar.MINUTE))*100;
        boolean result =  this.model.setSchedule(billboardID,startTime,endTime, duration, recurs,
                weekday);

        return "<response>\n" +
                "\t<type>"+result+"</type>\n" +
                "</response>";
    }

    /**
     * Removes a billboard schedule
     *
     * @param billboardID the billboard to de-schedule
     * @param startTime   the scheduled start time to be removed.
     * @return a Response string.
     */
    public String removeSchedule(int billboardID, int startTime) {
        String response = "";
        try {
            model.removeSchedule(billboardID, startTime);

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element resp = doc.createElement("response");
            Element type = doc.createElement("type");

            type.appendChild(doc.createTextNode("success"));
            doc.appendChild(resp);
            resp.appendChild(type);
            response = BillboardController.convertDocumentToString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
