package blockgames;

import javax.swing.*;
public class BlockGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Gameplay gameplay = new Gameplay();
        frame.add(gameplay);
        frame.setBounds(10, 0, 700, 600);
        frame.setTitle("Msblocks");
        frame.setResizable(false); // Changed to false to prevent resizing
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
