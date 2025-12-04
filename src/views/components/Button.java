package views.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class Button extends JButton{
     public Button(String text ) {
        super(text);

        setFont(new Font("Arial", Font.BOLD, 14));
        setPreferredSize(new Dimension(200, 35));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
