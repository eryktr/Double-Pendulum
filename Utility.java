package com.pendulum;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Utility
{
    public double velocityX1, velocityX2, velocityY1, velocityY2;
    public double angle1, angle2;
    public double length1, length2;
    public ArrayList<Point> pathPoints;

    public static void calculateNewValues()
    {
        //TODO
    }

    //Metoda translacji działa poprawnie.
    public static void TranslateCircle(Circle circle, double newX, double newY)
    {
        double initialX = circle.getCenterX();
        double initialY = circle.getCenterY();
        circle.setTranslateX(circle.getTranslateX() + (newX - initialX));
        circle.setTranslateY(circle.getTranslateY() + (newY - initialY));
    }

}
