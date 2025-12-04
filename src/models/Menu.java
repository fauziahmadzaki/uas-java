package models;

public class Menu {
    public int id;
    public String name;
    public Runnable action;

    public Menu(int id, String name, Runnable action) {
        this.id = id;
        this.name = name;
        this.action = action;
    }
}
