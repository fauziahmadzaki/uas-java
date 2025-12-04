package views.components;

import javax.swing.JPasswordField;
import java.awt.Cursor;
import java.awt.Dimension;

public class PasswordInput extends JPasswordField{
    public PasswordInput(){
        setPreferredSize(new Dimension(200, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
