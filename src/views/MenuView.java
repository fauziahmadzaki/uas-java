package views;

public class MenuView {
    public int id;
    public String name;
    public Runnable action;
    public MenuView(int id, String name, Runnable action) {
        this.id = id;
        this.name = name;
        this.action = action;
    }
}