import pl.edu.ug.hiczhicz.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainWindow frame = new MainWindow();
        frame.setTitle("Shapes");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //..wyrównanie do środka ekranu
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}