//import java.awt.Color;
//
//import pl.edu.ug.hiczhicz.Circle;
//import pl.edu.ug.hiczhicz.Ellipse;
//import pl.edu.ug.hiczhicz.Point;
//import pl.edu.ug.hiczhicz.Rectangle;
//import pl.edu.ug.hiczhicz.Shape;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//public class Main {
//    public static void main(String[] args) {
//        Circle c1=new Circle(Color.BLUE, new Point(20,20),10);
//        System.out.println(c1);
//        System.out.println(c1.getArea());
//        System.out.println(c1.getPerimeter());
//        System.out.println(c1.getCircumscribedSquare(Color.RED));
//        Shape c2=new Circle(Color.BLUE, new Point(20,20),10);
//        System.out.println(c2);
//        //This won't compile, because c2 is declared as Shape.
//        //c2.getCircumscribedSquare(Color.RED);
//        //but this will compile ok.
//        System.out.println(c2);
//        Shape c3=((Circle) c2).getCircumscribedSquare(Color.RED);
//        System.out.println(c3);
//        Rectangle r=new Rectangle(Color.PINK,new Point(15,15),10,20);
//        System.out.println(r);
//        System.out.println(r.getCircumscribedCircle(Color.CYAN));
//        System.out.println(new Ellipse(Color.YELLOW,new Point(10,10),20,30));
//    }
//}

import pl.edu.ug.hiczhicz.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainWindow frame = new MainWindow();
        frame.setTitle("Shapes");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}