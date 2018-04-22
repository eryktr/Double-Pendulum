package com.pendulum;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//Okazało się, że nie ma żadnej klasy spełniającej nasze wymaganie - zrobiłem coś takiego - powinna się nadać.
public class Point
{
    private double x,y;
    private Color color;

    public Point(double x, double y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public Circle drawPoint()
    {
        return new Circle(x, y, 1, color);
    }
}
