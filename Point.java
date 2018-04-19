package com.pendulum;
//Okazało się, że nie ma żadnej klasy spełniającej nasze wymaganie - zrobiłem coś takiego - powinna się nadać.
public class Point
{
    private double x,y;
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
}
