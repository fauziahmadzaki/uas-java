package views.frames;

import javax.swing.JFrame;

public abstract class AppFrame extends JFrame {

    public AppFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    protected abstract void initComponents();
}
