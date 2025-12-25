package pl.edu.ug.hiczhicz;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class FileManager {

    //..serialization - SAVING
    public static void saveShapes(ArrayList<Shape> shapes, String filename) throws IOException {
        //..konwersja na dane binarne - zapenwienie, że plik zostanie zapisany w tej formie
        if (!filename.endsWith(".dat")) {
            filename += ".dat";
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(shapes);
        }
    }

    //..deserialization - LOADING
    public static ArrayList<Shape> loadShapes(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<Shape>) in.readObject();
        }
    }

    //..eksport do .svg -> svg rysuje od lewego górnego rogu
    public static void exportToSVG(ArrayList<Shape> shapes, String filename, int width, int height) throws IOException {
        //..zapewnienie, że plik zapisze się jako svg
        if (!filename.endsWith(".svg")) {
            filename += ".svg";
        }

        StringBuilder svg = new StringBuilder();
        //..nagłówek .svg
        svg.append(String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">\n", width, height));
        //..tło
        svg.append("<rect width=\"100%\" height=\"100%\" fill=\"#FAFAF0\" />\n");

        for (Shape s : shapes) {
            String colorHex = String.format("#%02x%02x%02x", s.color.getRed(), s.color.getGreen(), s.color.getBlue());
            //..przerzoczystość
            double opacity = s.color.getAlpha() / 255.0;

            if (s instanceof Ellipse) {
                Ellipse e = (Ellipse) s;
                double rx, ry;
                if (s instanceof Circle) {
                    //..dzielimy przez 2 - u nas ax1
                    rx = e.ax1/2.0;
                    ry = e.ax1/2.0;
                } else {
                    rx = e.ax1/2.0;
                    ry = e.ax2/2.0;
                }
                svg.append(String.format(
                        "<ellipse cx=\"%d\" cy=\"%d\" rx=\"%.2f\" ry=\"%.2f\" fill=\"%s\" fill-opacity=\"%.2f\" />\n",
                        (int)s.center.x, (int)s.center.y, rx, ry, colorHex, opacity
                ));
            }
            else if (s instanceof Rectangle) {
                Rectangle r = (Rectangle) s;
                //..svg od lewego górnego rogu - my mamy środek i dwa boki
                double x = r.center.x - (r.a / 2.0);
                double y = r.center.y - (r.b / 2.0);

                svg.append(String.format(
                        "<rect x=\"%.2f\" y=\"%.2f\" width=\"%.2f\" height=\"%.2f\" fill=\"%s\" fill-opacity=\"%.2f\" />\n",
                        x, y, r.a, r.b, colorHex, opacity
                ));
            }
        }

        svg.append("</svg>");

        //..zapis string do pliku z try with resources
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(svg.toString());
        }
    }
}