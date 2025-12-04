package views;

import javax.swing.*;
import java.awt.*;
import services.AuthService;
import views.components.LabeledPasswordInput;
import views.components.LabeledTextInput;
import views.components.Button;
import views.frames.AppFrame;

public class LoginView extends AppFrame {
    private AuthService authService;
    private LabeledTextInput usernameField;
    private LabeledPasswordInput passwordField;
    private Button loginButton;

    public LoginView(AuthService authService){
        super("Login", 350, 350);
        this.authService = authService;
        
        initComponents();   
    }
    @Override
    protected void initComponents(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        

        JLabel titlePanel = new JLabel("Login Kasir");
        titlePanel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        jPanel.add(titlePanel, gbc);
        gbc.gridwidth = 1;
       

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        usernameField = new LabeledTextInput("Username: ");
        jPanel.add(usernameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new LabeledPasswordInput("Password: ");
        jPanel.add(passwordField, gbc);
        

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new Button("Login");
        jPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());
        getRootPane().setDefaultButton(loginButton);

        add(jPanel);

    }
        

    private void handleLogin(){
        String username = usernameField.getText();
        String password = new String(passwordField.getText());

        boolean success = authService.login(username, password);
        if(success){
            JOptionPane.showMessageDialog(this, "Login berhasil, selamat datang" + username);

        }else{
            JOptionPane.showMessageDialog(this, "Login gagal, username atau password salah", "Error" ,JOptionPane.ERROR_MESSAGE);
        }
    }
}
