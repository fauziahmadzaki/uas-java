package views;

import javax.swing.*;
import java.awt.*;
import services.AuthService;
import views.components.*;
import views.components.Button;
import views.frames.AppFrame;

public class LoginView extends AppFrame {
    private AuthService authService;
    private LabeledTextInput usernameField;
    private LabeledPasswordInput passwordField;
    private Runnable onSuccess; 

    public LoginView(AuthService authService){
        super("Login Sistem", 400, 350);
        this.authService = authService;
        initComponents();   
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    @Override
    protected void initComponents(){
        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("LOGIN KASIR", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx=0; gbc.gridy=0;
        jPanel.add(title, gbc);

        usernameField = new LabeledTextInput("Username:");
        gbc.gridy=1;
        jPanel.add(usernameField, gbc);

        passwordField = new LabeledPasswordInput("Password:");
        gbc.gridy=2;
        jPanel.add(passwordField, gbc);

        Button loginButton = new Button("MASUK");
        gbc.gridy=3;
        jPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());
        
        add(jPanel);
    }

    private void handleLogin(){
        String username = usernameField.getText();
        String password = new String(passwordField.getText());

        if(authService.login(username, password)){
            JOptionPane.showMessageDialog(this, "Login Berhasil!");
            this.dispose(); 
            if(onSuccess != null) onSuccess.run();
        } else {
            JOptionPane.showMessageDialog(this, "Username/Password Salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}