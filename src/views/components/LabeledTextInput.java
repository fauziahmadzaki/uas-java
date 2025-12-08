package views.components;

import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.JLabel;
import javax.swing.JTextField;

public class LabeledTextInput extends JPanel{
    private JLabel label;
    private TextInput textInput;
    
    public LabeledTextInput(String labelText) {
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        label = new JLabel(labelText);
        add(label, gbc);
       

        gbc.gridy = 1;
        textInput = new TextInput();
        add(textInput, gbc);        
    }

    public String getText(){
        return textInput.getText();
    }

    public void setText(String text){
        textInput.setText(text);
    }

    public JTextField getTextInput(){
        return textInput;
    }
}
