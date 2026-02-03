# Shapes

A simple vector graphics editor built with **Java Swing**. This application allows users to draw, manipulate, and save various geometric shapes with custom parameters.

## Features

* **Interactive GUI:** User-friendly interface created with Java Swing.
* **Shape Drawing:** Draw geometric shapes (e.g., circles, rectangles) on a canvas.
* **Customization:**
  * Change **Color** of the shapes.
  * Set specific **Position** (coordinates).
  * Adjust **Size** dimensions.
* **Export to SVG:** Save your drawings directly to Scalable Vector Graphics (`.svg`) format for high-quality scaling.

## Technologies Used

* **Language:** Java
* **GUI Framework:** Swing (JFrame, JPanel, Graphics2D)
* **File I/O:** XML/Batik (or internal implementation) for SVG export.

## Getting Started

### Prerequisites
* Java Development Kit (JDK) 8 or higher.

### Installation and Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/HiczHicz/Shapes.git](https://github.com/HiczHicz/Shapes.git)

2. Navigate to the project directory:
    ```bash
    cd Shapes
    
3. Compile the source code:
    ```bash
    javac -d bin src/*.java
    
4. Run the application
    ```bash
    java -cp bin Main
    
## Usage
1. Launch the application.
2. Select a shape tool from the menu/toolbar.
3. Input or select the desired color and size.
4. Click on the canvas to draw the shape at the desired position.
5. To export your work, select the Export option from the file menu.
6. To save your work, select the Load option from the file menu. You can later load it using the Load option.



