package models;

public class User {
    private String username;
    private String password;
    private String noTelp; 

    public User(String username, String password, String noTelp) {
        this.username = username;
        this.password = password;
        this.noTelp = noTelp;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNoTelp() { return noTelp; }
}