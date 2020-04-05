package ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends JFrame implements ActionListener{

    JButton createBillboards_Button, listBillboards_Button, scheduleBillboards_Button, editUsers_Button;

    MenuScreen() {
        setSize(1000,500);
        setBackground(Color.cyan);
        setLayout(null);
        setVisible(false);

        //buttons
        createBillboards_Button = new JButton("Create Billboards");
        createBillboards_Button.setBounds(450,100,150,30);
        createBillboards_Button.addActionListener(this);
        add(createBillboards_Button);

        listBillboards_Button = new JButton("List Billboards");
        listBillboards_Button.setBounds(450,150,150,30);
        listBillboards_Button.addActionListener(this);
        add(listBillboards_Button);

        scheduleBillboards_Button = new JButton("Schedule Billboards");
        scheduleBillboards_Button.setBounds(450,200,150,30);
        scheduleBillboards_Button.addActionListener(this);
        add(scheduleBillboards_Button);

        editUsers_Button = new JButton("Edit Users");
        editUsers_Button.setBounds(450,250,150,30);
        editUsers_Button.addActionListener(this);
        add(editUsers_Button);
    }

    public void actionPerformed(ActionEvent e){

    }}
