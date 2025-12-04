package views.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

public class TextInput extends JTextField{
     public TextInput() {
        super();
        setFont(new Font("Arial", Font.PLAIN, 14));
        setPreferredSize(new Dimension(200, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
     }
}
