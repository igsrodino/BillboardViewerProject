package ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener {//inheriting JFrame
    JLabel test_Label, username_Label, password_Label;
    JTextField username_Textfield;
    JPasswordField passwordField;
    JButton login_Button;

    LoginScreen(){

        //frame
        setSize(1000,500);
        setBackground(Color.cyan);
        setLayout(null);
        setVisible(true);

        //Labels
        username_Label = new JLabel("Username:");
        username_Label.setBounds(400,100,100, 40);
        add(username_Label);

        password_Label = new JLabel("Password:");
        password_Label.setBounds(400,150,100, 40);
        add(password_Label);

        test_Label=new JLabel("");//create label
        test_Label.setBounds(150,100,500, 40);
        add(test_Label);

        //text fields
        username_Textfield = new JTextField();
        username_Textfield.setBounds(500,100,100, 40);
        add(username_Textfield);

        passwordField = new JPasswordField();
        passwordField.setBounds(500,150,100, 40);
        add(passwordField);

        //buttons
        login_Button = new JButton("Login");
        login_Button.setBounds(500,300,95,30);
        login_Button.addActionListener(this);
        add(login_Button);
    }

    public void actionPerformed(ActionEvent e) {
        String login_data = "Username: " + username_Textfield.getText();
        login_data += ", Password: " + new String(passwordField.getPassword());
        test_Label.setText(login_data);
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.setVisible(true);
    }

        public static void main(String[] args) {
            LoginScreen loginScreen = new LoginScreen();
        }
    }