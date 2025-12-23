package pl.edu.ug.hiczhicz;

import javax.swing.*;
import java.awt.*;

public class Square extends Rectangle{

    public Square(){
        super();
        name="Square";
    }

    public Square(Color color, Point center, double side){
        super(color, center, side, side);
        name="Square";
    }

    protected void getExtraData(JFrame frame){
        a=Double.parseDouble(JOptionPane.showInputDialog(frame,
                "Enter side length: ", "80"));
        b=a;
    }

    public Circle getInscribedCircle(Color color){
        return new Circle(color, center, a/2);
    }
}
