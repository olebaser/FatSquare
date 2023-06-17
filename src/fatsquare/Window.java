package fatsquare;

import javax.swing.JFrame;

public class Window extends JFrame {
    // initialize JFrame
    Window() {
        this.setTitle("Fat Square");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new Game());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

