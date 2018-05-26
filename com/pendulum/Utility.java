package com.pendulum;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.scene.control.TextField;
import java.util.ArrayList;

public class Utility
{
    public static double positionX1, positionX2, positionY1, positionY2;
    public static double angle1, angle2;
    public static double length1, length2;
    public static double mass1, mass2;
    public static ArrayList<Point> pathPoints;
    private final static double RADIUS = 10;

    private static Circle firstPendulum, secondPendulum;
    private static Line firstLine, secondLine;

    public static Simulation sim;

    //Pierwsze wahadlo przyczepione w X = 300 Y = 150
    //Pane: szerokosc 600 wysokosc 500

    public static void calculateNewValues()
    {
        sim.simulate();
        sim.computePositions();

        positionX1 = Simulation.positions.x;
        positionY1 = Simulation.positions.y;
        positionX2 = Simulation.positions.z;
        positionY2 = Simulation.positions.w;
    }

    //Zmienia wspolrzedne wahadel w symulacji
    public static void addNewValuesToSimulation()
    {
        Simulation.positions.x = positionX1;
        Simulation.positions.y = positionY1;
        Simulation.positions.z = positionX2;
        Simulation.positions.w = positionY2;
    }

    //Translatuje pierwsze wahadlo wraz z nitka przyczepiona do niego
    public static void translateFirstPendulum ()
    {
        Platform.runLater( () ->
        {
            translateCircle(firstPendulum, positionX1, positionY1);
            translateLine(firstLine, 300, 150, positionX1, positionY1);
        });
    }

    //Przenosi drugie wahadlo wraz z nitka
    public static void translateSecondPendulum()
    {
        Platform.runLater(() ->
        {
            translateCircle(secondPendulum, positionX2, positionY2);
            translateLine(secondLine, positionX1, positionY1, positionX2, positionY2);
        });
    }

    //Przenosi linie na wspolrzedne (X1,Y1) (X2,Y2)
    private static void translateLine (Line line, double X1, double Y1, double X2, double Y2)
    {
        Platform.runLater(() ->
        {
            line.setStartX(X1);
            line.setStartY(Y1);
            line.setEndX(X2);
            line.setEndY(Y2);
        });
    }

    //Metoda translacji działa poprawnie. newX - nowa wartosc x dla okregu,  newY - nowa wartosc y dla okregu
    private static void translateCircle (Circle circle, double newX, double newY)
    {
        double initialX = circle.getCenterX();
        double initialY = circle.getCenterY();
        Platform.runLater(() ->
        {
            circle.setTranslateX(newX - initialX);
            circle.setTranslateY(newY - initialY);
        });
    }

    //Bedzie ustawiac wartosci poczatkowe zmiennych zgodnie z wartosciami w TextFieldach
    //Poki co wartosci sa domyslne do testowania
    public static void setInitialValues(TextField[] textFields)
    {
        //wartosci zalezne od uzytkownika (do zmiany w przyszlosci)
        mass1 = Double.parseDouble(textFields[0].getText());
        mass2 = Double.parseDouble(textFields[1].getText());
        angle1 = Double.parseDouble(textFields[2].getText());
        angle2 = Double.parseDouble(textFields[3].getText());
        length1 = Double.parseDouble(textFields[4].getText());
        length2 = Double.parseDouble(textFields[5].getText());
        calculatePositionsFromAngles();
    }

    //Liczy (x, y) dla kazdego wahadla na podstawie katow angle1, angle2
    private static void calculatePositionsFromAngles ()
    {
        positionX1 = length1 * Simulation.scale * Math.sin(angle1) + 300; //l1*scale*Math.sin(state.x) + x0;
        positionY1 = length1 * Simulation.scale * Math.cos(angle1) + 150;
        positionX2 = length2 * Simulation.scale * Math.sin(angle2) + positionX1;
        positionY2 = length2 * Simulation.scale * Math.cos(angle2) + positionY1;
    }

    public static void initializeFigures(Pane drawingPane)
    {
        firstPendulum = new Circle (positionX1, positionY1, RADIUS);
        drawingPane.getChildren().add(firstPendulum);
        secondPendulum = new Circle (positionX2, positionY2, RADIUS);
        drawingPane.getChildren().add(secondPendulum);
        firstLine = new Line (300, 150, positionX1, positionY1);
        drawingPane.getChildren().add(firstLine);
        secondLine = new Line (positionX1, positionY1, positionX2, positionY2);
        drawingPane.getChildren().add(secondLine);
        pathPoints = new ArrayList<>();
    }

    //Rysuje droge wahadla w podanym kolorze, pendulumNumber = numer wahadla (1, 2)
    public static void drawPendulumPath (Pane drawingPane, Color color, int pendulumNumber)
    {
        double x = 0, y = 0;
        switch (pendulumNumber)
        {
            case 1:
                x = positionX1;
                y = positionY1;
                break;
            case 2:
                x = positionX2;
                y = positionY2;
                break;
        }
        Point point = new Point (x, y, color);
        pathPoints.add(point);
        Circle c = point.drawPoint();
        //Chyba jedyny sposob zeby updatowac Pane z tego watku
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawingPane.getChildren().add(c);
            }
        });
    }

    //Ustawia w TextFieldach wartosci domyslne
    public static void setDefaultValues (TextField[] textFields)
    {
        textFields[0].setText("3.4");
        textFields[1].setText("0.25");
        textFields[2].setText("2.5");
        textFields[3].setText("0.8");
        textFields[4].setText("0.25");
        textFields[5].setText("0.2");

    }

}