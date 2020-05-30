package ControlPanel.Models;

import java.util.HashMap;
import java.util.Map;

public class ScheduleModel {
    private Map<String, Integer> sunday;

    public ScheduleModel(){
        this.sunday = new HashMap<String, Integer>();
    }
    public void addToMap(String day, String key, int sourceIdx){
        switch(day){
            case "Sunday":
                sunday.put(key, sourceIdx);
                break;
            default:
                break;
        }
    }
    public void removeFromMap(String day, String key){
        switch(day){
            case "Sunday":
                sunday.remove(key);
                break;
            default:
                break;
        }
    }
    public void saveSchedule(int billboardID, int startTime, int duration, int recurs){}

}
