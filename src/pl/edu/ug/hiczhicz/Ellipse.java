package pl.edu.ug.hiczhicz;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Shape{
    protected double ax1, ax2;

    public Ellipse(){
        super();
        name="Ellipse";
    }

    public Ellipse(Color color, Point center,
                   double ax1, double ax2){
        super(color, center);
        this.ax1=ax1;
        this.ax2=ax2;
        name="Ellipse";
    }

    @Override
    protected double computeArea(){
        return Math.PI*ax1*ax2;
    }
    @Override
    protected double computePerimeter(){
        //..simple lower bound, return Math.PI*(ax1+ax2); slightly more accurate aproximate
        return Math.PI*Math.sqrt(2*ax1*ax1+2*ax2*ax2);
    }

    @Override
    public Point getCorner(){
        Point pm=new Point(ax1,ax2);
        return center.subtract(pm);
    }
    @Override
    protected void getExtraData(JFrame frame){
        String axes= JOptionPane.showInputDialog(frame,
                "Enter axes lengths ax1:ax2",
                "105:55");
        String[] axesArray=axes.split(":");
        ax1=Double.parseDouble(axesArray[0]);
        ax2=Double.parseDouble(axesArray[1]);
    }
    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        Point corner=getCorner();
        Ellipse2D.Double ellipse=
                new Ellipse2D.Double(corner.x,corner.y,2*ax1,2*ax2);
        g2d.draw(ellipse);
        g2d.fill(ellipse);
    }
    @Override
    public String toString(){
        return super.toString()+
                (isEmpty ? "":", [ax1:"+(int) ax1+", ax2:"+(int)ax2+"]");
    }

    public Circle getInscribedCircle(Color color){
        double radius = Math.min(ax1, ax2);
        return new Circle(color, center, radius);
    }
}
