package com.pendulum;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Utility
{
    private static double velocityX1, velocityX2, velocityY1, velocityY2;
    public static double positionX1, positionX2, positionY1, positionY2;
    public static double angle1, angle2;
    public static double length1, length2;
    public static double mass1, mass2;
    public static ArrayList<Point> pathPoints;
    private static double dt, g;
    final static double RADIUS = 10;

    private static Circle firstPendulum, secondPendulum;
    private static Line firstLine, secondLine;

    public static Simulation sim;

    //Pierwsze wahadlo przyczepione w X = 300 Y = 0
    //Pane: szerokosc 600 wysokosc 500

    public static void calculateNewValues()
    {
        /*
        //To wszystko trzeba bedzie zmienic na symulacje ruchu wahadla
        velocityX1 += 0;
        velocityY1 += g*dt;
        positionY1 += velocityY1*dt;
        positionX1 += velocityX1*dt;

        velocityX2 += 0;
        velocityY2 += g*dt;
        positionY2 += velocityY2*dt;
        positionX2 += velocityX2*dt;
        //TODO
        */
        sim.simulate();
        sim.computePositions();

        positionX1 = Simulation.positions.x;
        positionY1 = Simulation.positions.y;
        positionX2 = Simulation.positions.z;
        positionY2 = Simulation.positions.w;



    }

    //Translatuje pierwsze wahadlo wraz z nitka przyczepiona do niego
    public static void TranslateFirstPendulum ()
    {
        TranslateCircle(firstPendulum, positionX1, positionY1);
        TranslateLine(firstLine, 300, 0, positionX1, positionY1);
    }

    //Przenosi drugie wahadlo wraz z nitka
    public static void TranslateSecondPendulum()
    {
        TranslateCircle(secondPendulum, positionX2, positionY2);
        TranslateLine(secondLine, positionX1, positionY1, positionX2, positionY2);
    }

    //Przenosi linie na wspolrzedne (X1,Y1) (X2,Y2)
    private static void TranslateLine (Line line, double X1, double Y1, double X2, double Y2)
    {
        line.setStartX(X1);
        line.setStartY(Y1);
        line.setEndX(X2);
        line.setEndY(Y2);
    }

    //Metoda translacji działa poprawnie. newX - nowa wartosc x dla okregu,  newY - nowa wartosc y dla okregu
    private static void TranslateCircle (Circle circle, double newX, double newY)
    {
        double initialX = circle.getCenterX();
        double initialY = circle.getCenterY();
        circle.setTranslateX(newX - initialX);
        circle.setTranslateY(newY - initialY);
    }

    //Bedzie ustawiac wartosci poczatkowe zmiennych zgodnie z wartosciami w TextFieldach
    //Poki co wartosci sa domyslne do testowania
    public static void setInitialValues()
    {
        //wartosci zalezne od uzytkownika (do zmiany w przyszlosci)
        length1 = 200;
        mass1 = 1;
        positionX1 = 100;
        positionY1 = 0;
        angle1 = -1 * (Math.PI/2);
        positionX2 = 200;
        positionY2 = 200;
        //TODO

        //wartosci niezalezne od uzytkownika
        dt = 0.016;
        g = 9.81;
        velocityX1 = 30;
        velocityY1 = 0;
        velocityX2 = 15;
        velocityY2 = -20;
    }

    //Oblicza kat w zaleznosci od polozenia
    private static double calculateAngle (double x, double y)
    {
        //Polozenie punktu zaczepienia wahadla pierwszego
        double axisX = 300;
        double axisY = 0;

        double dy = y - axisY;
        double dx = x - axisX;
        return Math.atan((dy/dx));
        //TODO
    }

    public static void initializeFigures(Pane drawingPane)
    {
        firstPendulum = new Circle (positionX1, positionY1, RADIUS);
        drawingPane.getChildren().add(firstPendulum);
        secondPendulum = new Circle (positionX2, positionY2, RADIUS);
        drawingPane.getChildren().add(secondPendulum);
        firstLine = new Line (300, 0, positionX1, positionY1);
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

}