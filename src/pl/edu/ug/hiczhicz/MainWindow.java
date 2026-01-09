package pl.edu.ug.hiczhicz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonExport;

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
        buttonSave.setText("Save");
        buttonExport.setText("Export");
        buttonLoad.setText("Load");
        updateLabels();
        drawInitialShapes();

        //---------------------------------------------ACTION BUTTONS----------------------------------------------
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

        buttonSave.addActionListener(actionEvent -> actionSave());
        buttonLoad.addActionListener(actionEvent -> actionLoad());
        buttonExport.addActionListener(actionEvent -> actionExport());
    }

    //---------------------------------------------ADD----------------------------------------------
    private void actionAdd() {
        if (color == null || point == null) {
            JOptionPane.showMessageDialog(this, "Select point and color!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] shapes = {"Rectangle", "Square", "Ellipse", "Circle"};
        //..tworzymy JComboBox, który obsłuży stringi - lista rozwijana
        JComboBox<String> shapeList = new JComboBox<>(shapes);

        //..definicja własnych przycisków
        Object[] options = {"Draw", "Cancel"};

        int result = JOptionPane.showOptionDialog(this, shapeList, "Please select shape",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]); //..domyślny przycisk

        if (result == 0) {
            String choice = (String) shapeList.getSelectedItem();
            //..obiekt z pustymi wymiarami
            Shape s = switch (choice) {
                case "Rectangle" -> new Rectangle(color, point, 0, 0);
                case "Square" -> new Square(color, point, 0);
                case "Ellipse" -> new Ellipse(color, point, 0, 0);
                case "Circle" -> new Circle(color, point, 0);
                default -> null;
            };

            if (s != null) {
                try {
                    //..korzystamy z metod w klasach
                    s.getExtraData(this);

                    //..dodajemy do listy
                    canvas.shapes.add(s);
                    canvas.rePaint();
                } catch (Exception ex) {
                    //..sytuacja gdy użytkownik kliknie cancel / poda błędne liczby
                    JOptionPane.showMessageDialog(this, "Cancelled or wrong data", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    //---------------------------------------------CLEAR----------------------------------------------
    private void actionClear(){
        canvas.shapes.clear();
        canvas.rePaint();
    }
    //---------------------------------------------SHOW SHAPES----------------------------------------------
    private void actionShowShapes() {
        if (canvas.shapes.isEmpty()){
            JOptionPane.showMessageDialog(
                    this,
                    "No shapes drawn",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            StringBuilder sb = new StringBuilder();
            for (Shape s : canvas.shapes) {
                sb.append(s.toString()); //..opis kształu
                sb.append("\n");         //..nowa linia
            }
            JOptionPane.showMessageDialog(
                    this,
                    sb.toString(),
                    "List of shapes",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    }
    //---------------------------------------------COLORS----------------------------------------------
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
    //---------------------------------------------CENTER----------------------------------------------
    private void actionPointClicked(MouseEvent e){
        this.point = new Point(e.getX(), e.getY());
        updateLabels();
    }

    //---------------------------------------------CLOSE----------------------------------------------
    private void actionClose() {
        this.dispose();
    }

    //---------------------------------------------CREDITS----------------------------------------------
    private void actionCredits(){
        JOptionPane.showMessageDialog(this, "Developed by Martyna Pieczka", "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

    //---------------------------------------------LABELS----------------------------------------------
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

    //---------------------------------------------SAVE----------------------------------------------
    private void actionSave() {
        //..nie zapisujemy pustych plików
        if (canvas.shapes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to save!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        //..filtrowanie, żeby widziało pliki tylko z .dat
        FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT files", "dat");
        fileChooser.setFileFilter(filter);

        //..tytuł okienka
        fileChooser.setDialogTitle("Save shapes");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                FileManager.saveShapes(canvas.shapes, filePath);
                JOptionPane.showMessageDialog(this, "Saved successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                //..wyrzuca błąd do konsoli, a nie użytkownikowi, dlatego się czepia - zmienić w przyszłości
                e.printStackTrace();
            }
        }
    }

    //---------------------------------------------LOAD----------------------------------------------
    private void actionLoad() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".dat files", "dat");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Load shapes");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                //..wczytanie listy z pliku
                canvas.shapes = FileManager.loadShapes(filePath);
                canvas.rePaint();
                JOptionPane.showMessageDialog(this, "Loaded successfully!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    //---------------------------------------------EXPORT----------------------------------------------
    private void actionExport() {
        if (canvas.shapes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to export!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export to SVG");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                //.. przekazujemy rozmiar canvas, żeby ustawić nagłówek SVG
                FileManager.exportToSVG(canvas.shapes, filePath, canvasPanel.getWidth(), canvasPanel.getHeight());
                JOptionPane.showMessageDialog(this, "Exported to SVG successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error exporting: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //---------------------------------------------INITIAL SHAPES----------------------------------------------
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

