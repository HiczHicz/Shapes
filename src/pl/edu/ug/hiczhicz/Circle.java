package pl.edu.ug.hiczhicz;

import javax.swing.*;
import java.awt.*;

public class Circle extends Ellipse{

    public Circle(){
        super();
        name="Circle";
    }
    public Circle(Color color, Point center, double radius){
        super(color, center, radius, radius);
        name="Circle";
    }
    @Override
    protected void getExtraData(JFrame frame){
        ax1=Double.parseDouble(JOptionPane.showInputDialog(frame,
                "Enter radius of the circle r: ", "80"));
        ax2=ax1;
    }
    public Square getCircumscribedSquare(Color color){
        double side=2*ax1;
        return new Square(color, center, side);
    }
}
