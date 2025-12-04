package services;
import models.User;


public class AuthService {
    private User admin;
    private boolean isAuthenticated = false;

    public AuthService(){
        this.admin = new User("admin", "12345");
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public User getUser(){
        return this.admin;
    }

    public boolean login(String username, String password) {
        boolean result = this.admin.getUsername().equals(username) && this.admin.getPassword().equals(password);
        if(result){
            this.isAuthenticated = true;
        }
        return result;
    }

    public void logout(){
        this.isAuthenticated = false;
    }
}
