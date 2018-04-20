package com.pendulum;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

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

    //Pierwsze wahadlo przyczepione w X = 300 Y = 0
    //Pane: szerokosc 600 wysokosc 500

    public static void calculateNewValues()
    {
        velocityX1 += 0;
        velocityY1 += g*dt;
        positionY1 += velocityY1*dt;
        positionX1 += velocityX1*dt;
        //Trzeba naprawic bo w ogole nie dziala jak powinno, poki co wrzucam symulacje rzutu ukosnego
        /*
        double accelerationX1 = g * Math.sin(angle1) * Math.cos(angle1);
        double accelerationY1 = g * Math.sin(angle1) * Math.sin(angle1);
        velocityX1 -= accelerationX1 * dt;
        velocityY1 += accelerationY1 * dt;
        positionX1 = 300-Math.sqrt(length1*length1 - (positionY1)*(positionY1));
        positionY1 += velocityY1 * dt;
        angle1 = calculateAngle(positionX1, positionY1);
        */
        //TODO
    }

    //Metoda translacji działa poprawnie. newX - nowa wartosc x dla okregu,  newY - nowa wartosc y dla okregu
    public static void TranslateCircle(Circle circle, double newX, double newY)
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
        //wartosci zalezne od uzytkownika
        length1 = 200;
        mass1 = 1;
        positionX1 = 100;
        positionY1 = 0;
        angle1 = -1 * (Math.PI/2);
        //TODO

        //wartosci niezalezne od uzytkownika
        dt = 0.016;
        g = 9.81;
        velocityX1 = 30;
        velocityY1 = 0;
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

}
