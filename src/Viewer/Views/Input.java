package Viewer.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.SwingUtilities.getRootPane;

/**
 * Listener methods to check if a mouse or escape key has been clicked. Used to close Viewer if clicked.
 *
 */
public class Input {
    private final Frame frame;

    public Input(Frame frame){
        this.frame = frame;
    }

    /**
     * Listens for mouse clicks.
     */
    public void attachMouseEvent(){
        this.frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent exit) {
                System.exit(0);
            }
        });
    }

    /**
     * Listens for clicks of the escape key.
     */
    public void attachESCKeyEvent(){
        getRootPane(frame).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Close");
        getRootPane(frame).getActionMap().put("Close", new AbstractAction() {
            public void actionPerformed(ActionEvent exit)
            {
                System.exit(0);
            }
        });
    }
}
