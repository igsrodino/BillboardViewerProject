package ControlPanel.View;

import javax.swing.*;

/**
 * Getters for panel and schedule billboard in schedule view.
 */
public class ScheduleView {
    private JPanel containerPanel;
    private JButton scheduleBillboard;
    private JList sundayList;
    private JList mondayList;
    private JList tuesdayList;
    private JList wednesdayList;
    private JList thursdayList;
    private JList fridayList;
    private JList saturdayList;

    public JPanel getPanel() {
        return containerPanel;
    }

    public JButton getScheduleBillboard() {
        return scheduleBillboard;
    }

    public JList getSundayList() {
        return sundayList;
    }

    public JList getMondayList() {
        return mondayList;
    }

    public JList getTuesdayList() {
        return tuesdayList;
    }

    public JList getWednesdayList() {
        return wednesdayList;
    }

    public JList getThursdayList() {
        return thursdayList;
    }

    public JList getFridayList() {
        return fridayList;
    }

    public JList getSaturdayList() {
        return saturdayList;
    }
}
