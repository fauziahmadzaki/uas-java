package views.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class LabeledPasswordInput extends JPanel {
     private JLabel label;
    private PasswordInput passwordInput;
    public LabeledPasswordInput(String labelText) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        label = new JLabel(labelText);
        add(label, gbc);

        passwordInput = new PasswordInput();
        gbc.gridy = 1;

        add(passwordInput, gbc);
    }

    public char[] getText() {
        return passwordInput.getPassword();
    }

    public void setText(String text) {
        passwordInput.setText(text);
    }   

    public PasswordInput getTextInput() {
        return passwordInput;
    }
}
