package pl.edu.ug.hiczhicz;

import javax.swing.*;
import java.awt.*;

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
        return Math.PI*ax2*ax2;
    }
    @Override
    protected double computePerimeter(){
        //..simple lower bound, return Math.PI*(ax1+ax2); slightly more accurate aproximate
        return Math.PI*Math.sqrt(2*ax1*ax1+2*ax2*ax2);
    }

    @Override
    public Point getCorner(){
        //DOKONCZYC
    }
    @Override
    public void getExtraData(JFrame frame){
        /// ////////
    }
    @Override
    public void draw(Graphics2D g2d){
        /// ////////
    }
    @Override
    public String toString(){
        return super.toString()+
                (isEmpty ? "":", [ax1:"+(int) ax1+", ax2:"+(int)ax2+"]");
    }

    public Circle getInscribedCircle(Color color){
        /// ////////
    }
}
