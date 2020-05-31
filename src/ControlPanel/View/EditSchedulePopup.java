package ControlPanel.View;

import javax.swing.*;
import java.awt.*;

/**
 * Edit the schedule pop up panel.
 */
public class EditSchedulePopup {
    public static String display(String title) {
        String[] items = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                "Saturday"};
        JComboBox<String> day = new JComboBox<>(items);
        JTextField start = new JTextField("0800");
        JTextField duration = new JTextField("60");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JCheckBox delete = new JCheckBox("Delete Scheduled Item");
        panel.add(new JLabel("Day:"));
        panel.add(day);
        panel.add(new JLabel("Start time:"));
        panel.add(start);
        panel.add(new JLabel("Duration in minutes:"));
        panel.add(duration);
        panel.add(new JLabel(" "));
        panel.add(delete);
        panel.add(new JLabel(" "));
        int result = JOptionPane.showConfirmDialog(null, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return day.getSelectedItem() + "," + start.getText() + "," + duration.getText() + "," + delete.isSelected();
        } else {
            return null;
        }
    }
}
