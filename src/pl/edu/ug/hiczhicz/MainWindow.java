package pl.edu.ug.hiczhicz;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import static pl.edu.ug.hiczhicz.Shape.printColor;


public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JPanel canvasPanel;
    private JPanel controlPanel;
    private JButton buttonColor;
    private JButton buttonAdd;
    private JButton buttonClear;
    private JButton buttonShapes;
    private JButton buttonCredits;
    private JButton buttonClose;
    private JLabel pointLabel;
    private JLabel colorLabel;

    private DrawingCanvas canvas = new DrawingCanvas();
    private Color color = null;
    private Point point = null;

    public MainWindow() {
        super();
        this.setContentPane(mainPanel);
        canvasPanel.add(canvas);
        Border greyLine = BorderFactory.createLineBorder(Color.DARK_GRAY);
        canvasPanel.setBorder(greyLine);
        canvasPanel.setBackground(new Color(250, 250, 240));
        controlPanel.setBackground(new Color(245, 245, 250));
        buttonColor.setText("Choose color");
        buttonAdd.setText("Add shape");
        buttonClear.setText("Clear");
        buttonShapes.setText("List of shapes");
        buttonCredits.setText("Credits");
        buttonClose.setText("Close");
        updateLabels();
        drawInitialShapes();

        // All these event listeners should be added by IntelliJ semi-automatically
        // however in each listener we add our custom method 'action...'
        // responsible for this event
        buttonClose.addActionListener(actionEvent -> actionClose());
        buttonCredits.addActionListener(actionEvent -> actionCredits());
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                actionPointClicked(e);
            }
        });
        buttonColor.addActionListener(actionEvent -> actionChooseColor());
        buttonAdd.addActionListener(actionEvent -> actionAdd());
        buttonClear.addActionListener(actionEvent -> actionClear());
        buttonShapes.addActionListener(actionEvent -> actionShowShapes());

    }


    private void actionAdd(){
        if (color==null || point==null){
            JOptionPane.showMessageDialog(this, "Please select the point and the color", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            String[] shapes =
                    {
                            "Rectangle",
                            "Square",
                            "Ellipse",
                            "Circle"
                    };
            //..tworzymy JComboBox, który obsłuży stringi - lista rozwijana
            JComboBox<String> shapeList = new JComboBox<>(shapes);

            //..definicja własnych przycisków
            Object[] options = {"Draw", "Cancel"};

            int result = JOptionPane.showOptionDialog(
                    this,
                    shapeList,
                    "Please select shape",
                    JOptionPane.OK_CANCEL_OPTION, //..okno typu zatwierdź/odrzuć
                    JOptionPane.PLAIN_MESSAGE,    //brak ikonu
                    null,                    //..brak ikony
                    options,                 //..przyciski draw/cancel
                    options[0]               //..ustawienie domyślnego przycisku
            );

            if (result == 0) {
                //..pobranie tekstu z JCombobox
                String choice = (String) shapeList.getSelectedItem();

                switch (choice) {
                    case "Rectangle":
                        String sidesInput = (String) JOptionPane.showInputDialog(
                                this,
                                "Enter sides lengths a:b",
                                "Input",
                                JOptionPane.QUESTION_MESSAGE, //..question icon
                                null,
                                null,
                                "105:55"
                        );
                        if (sidesInput != null) {
                            try {
                                String[] sides = sidesInput.split(":");
                                double a = Double.parseDouble(sides[0]);
                                double b = Double.parseDouble(sides[1]);

                                //..czy boki są dodatnie?
                                if (a <= 0 || b <=0) {
                                    JOptionPane.showMessageDialog(this, "Sides lengths must be > 0", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    //..tworzymy nowy prostokąt
                                    Rectangle r = new Rectangle(this.color, this.point, a, b);

                                    //..dodajemy prostokąt na ekran
                                    canvas.shapes.add(r);
                                    canvas.rePaint();
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "Square":
                        String sideInput = (String) JOptionPane.showInputDialog(
                                this,
                                "Enter side length a",
                                "Input",
                                JOptionPane.QUESTION_MESSAGE, //..question icon
                                null,
                                null,
                                "80"
                        );
                        if (sideInput != null) {
                            try {
                                double a = Double.parseDouble(sideInput);

                                //..czy boki są dodatnie?
                                if (a <= 0) {
                                    JOptionPane.showMessageDialog(this, "Side length must be > 0", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    //..tworzymy nowy kwadrat
                                    Square s = new Square(this.color, this.point, a);

                                    //..dodajemy prostokąt na ekran
                                    canvas.shapes.add(s);
                                    canvas.rePaint();
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "Ellipse":
                        String axesInput = (String) JOptionPane.showInputDialog(
                                this,
                                "Enter axes ax1:ax2",
                                "Input",
                                JOptionPane.QUESTION_MESSAGE, //..question icon
                                null,
                                null,
                                "95:55"
                        );
                        if (axesInput != null) {
                            try {
                                String[] axes = axesInput.split(":");
                                double ax1 = Double.parseDouble(axes[0]);
                                double ax2 = Double.parseDouble(axes[1]);

                                //..czy osie są dodatnie?
                                if (ax1 <= 0 || ax2 <=0) {
                                    JOptionPane.showMessageDialog(this, "Axes must be > 0", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    //..tworzymy nowy Ellipse
                                    Ellipse e = new Ellipse(this.color, this.point, ax1, ax2);

                                    //..dodajemy Ellipse na ekran
                                    canvas.shapes.add(e);
                                    canvas.rePaint();
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "Circle":
                        String radiusInput = (String) JOptionPane.showInputDialog(
                                this,
                                "Enter radius:",
                                "Input",
                                JOptionPane.QUESTION_MESSAGE, //..question icon
                                null,
                                null,
                                "80"
                        );
                        if (radiusInput != null) {
                            try {
                                double radius = Double.parseDouble(radiusInput);

                                //..czy promień jest dodatni?
                                if (radius <= 0) {
                                    JOptionPane.showMessageDialog(this, "Radius must be > 0", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    //..tworzymy nowy Circle
                                    Circle c = new Circle(this.color, this.point, radius);

                                    //..dodajemy Circle na ekran
                                    canvas.shapes.add(c);
                                    canvas.rePaint();
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                }
            }

        }

    }

    private void actionClear(){
        canvas.shapes.clear();
        canvas.rePaint();
    }

    private void actionShowShapes() {

    }

    private void actionChooseColor() {
        //..generowanie losowego koloru na start
        Random rand = new Random();
        String defaultRgb = rand.nextInt(256) + ":" + rand.nextInt(256) + ":" + rand.nextInt(256);

        //..okno z zawartością
        Object resultObj = JOptionPane.showInputDialog(
                this,
                "Please input color components: r:g:b\nor leave empty for random color",
                "Input",
                JOptionPane.QUESTION_MESSAGE,
                null,                                  //..ikona (null = domyślna dla typu)
                null,                                       //..opcje wyboru (null = pole tekstowe)
                defaultRgb                                  //..domyślna wartość w polu
        );

        //..sprawdzenie, czy użytkownik kliknął OK (resultObj nie jest null)
        if (resultObj != null) {
            String result = resultObj.toString();

            if (result.trim().isEmpty()) {
                //..jeśli użytkownik wykasował tekst i dał OK -> losowanie nowego koloru
                this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            } else {
                //..odczytanie rgb
                try {
                    String[] parts = result.split(":");
                    int r = Integer.parseInt(parts[0].trim());
                    int g = Integer.parseInt(parts[1].trim());
                    int b = Integer.parseInt(parts[2].trim());
                    this.color = new Color(r, g, b);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Wrong format! Use r:g:b", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            updateLabels();
        }
    }

    private void actionPointClicked(MouseEvent e){
        this.point = new Point(e.getX(), e.getY());
        updateLabels();
    }

    private void actionClose() {
        this.dispose();
    }

    private void actionCredits(){
        JOptionPane.showMessageDialog(this, "Developed by Martyna Pieczka", "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateLabels() {
        if (color != null) {
            colorLabel.setText("Color: " + printColor(color));
        } else {
            colorLabel.setText("Color: ---");
        }

        if (point != null){
            pointLabel.setText("Center: " + point.toString());
        } else {
            pointLabel.setText("Center: ---");
        }
    }

    private void drawInitialShapes(){
        Color color1=new Color(0x84,0xb4,0xc8,0xd0);
        Color color2=new Color(0x8e,0xc9,0xbb,0xd0);
        Color color3=new Color(0xf4,0xdc,0xd6,0xd0);
        Circle c=new Circle(color1,new Point(200,200),70);
        canvas.shapes.add(c.getCircumscribedSquare(color3));
        canvas.shapes.add(c);
        Rectangle r=new Rectangle(color1,new Point(520,350),130,90);
        canvas.shapes.add(r.getCircumscribedCircle(color3));
        canvas.shapes.add(r);
        Ellipse e=new Ellipse(color2,new Point(410,250),90,150);
        canvas.shapes.add(e);
        canvas.shapes.add(e.getInscribedCircle(color3));
        Square s=new Square(color1,new Point(600,80),90);
        canvas.shapes.add(s);
        canvas.shapes.add(s.getInscribedCircle(color3));
        canvas.rePaint();
    }

}

