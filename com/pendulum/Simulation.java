package com.pendulum;

public class Simulation{
    private final static double  CONST_PI = 3.141592653589793;
    public static Vec4 startState, state, positions;
    public static boolean pause = true, exit = false;

    //Stan początkowy
    public static double h = 0.002, g = 9.81, x0 = 300, y0 = 150, scale = 500;
    public static double m1, m2, l1, l2;

    public Simulation(Double[] parameters) {
        m1 = parameters[0];
        m2 = parameters[1];
        l1 = parameters[2];
        l2 = parameters[3];
        startState = new Vec4();
        state = new Vec4();
        positions = new Vec4();

        startState.x = parameters[4];
        startState.y = parameters[5];
    }


    public void f(Vec4 out, Vec4 in) {
        /*
        Objasnienia
        in.x - theta1
        in.y - theta2
        in.z - omega1
        in.w - omega2

        out.x - dtTheta1
        out.y - dtTheta2
        out.z - dtOmega1
        out.w - dtOmega2

        A tu są cztery równiania definiujące nasz ruch
         */

        //dodałem tą instrukcje jako lekkie tłumienie i zdaje się działać
        in.mul(0.998);

        out.x = in.z;
        out.y = in.w;
        out.z = (-g*(2*m1 + m2)*Math.sin(in.x) - m2*g*Math.sin(in.x - 2*in.y) - 2*m2*in.w*in.w*l2*Math.sin(in.x-in.y) - m2*in.z*in.z*l1*Math.sin(2*in.x-2*in.y)) /
                (l1*(2*m1 + m2 - m2*Math.cos(2*in.x - 2*in.y)));
        out.w = (2*Math.sin(in.x - in.y)*(in.z*in.z*l1*(m1 + m2) +g*(m1 + m2)*Math.cos(in.x) + in.w*in.w*l2*m2*Math.cos(in.x - in.y))) /
                (l2*(2*m1 + m2 - m2*Math.cos(2*in.x - 2*in.y)));
    }

    //Zmienne potrzebne w funkcji Simulate()
    Vec4 	s = new Vec4(), t = null,
            a = new Vec4(), b = new Vec4(), c = new Vec4(), d = new Vec4(),
            sb = new Vec4(), sc = new Vec4(), sd = new Vec4();

    //Funkcja Simulate bierze stan początkowy i liczy calke
    //Nie polecam sie w to zagłębiać
    public void simulate() {
        s.set(state);

        f(a, s);

        s.madd(sb, 1/2*h, a);
        f(b, sb);

        s.madd(sc, 1/2*h, b);
        f(c, sc);

        s.madd(sd, h, c);
        f(d, sd);

        a.madd(a, 2.0, b);
        a.madd(a, 2.0, c);
        a.madd(a, 1.0, d);
        a.madd(a, 1.0, a);
        s.madd(s, h/6.0, a);

        t = state;
        state = s;
        s = t;

        while(state.x > 2 * CONST_PI) state.x -= 2*CONST_PI;
        while(state.y > 2 * CONST_PI) state.y -= 2*CONST_PI;
        while(state.x < 0 * CONST_PI) state.x += 2*CONST_PI;
        while(state.y < 0 * CONST_PI) state.y += 2*CONST_PI;
    }

    //Bierze wyliczone kąty i oblicza pozycje kółek
    public void computePositions() {
        positions.x = l1*scale*Math.sin(state.x) + x0;
        positions.y = l1*scale*Math.cos(state.x) + y0;
        positions.z = l2*scale*Math.sin(state.y) + positions.x;
        positions.w = l2*scale*Math.cos(state.y) + positions.y;

    }

    public class Vec4 {
        public double x = 0, y = 0, z = 0, w = 0;
        public Vec4() { }
        public void set(Vec4 v) {
            x = v.x; y = v.y; z = v.z; w = v.w;
        }
        public void madd(Vec4 out, double a, Vec4 v) {
            out.x = x + a * v.x; out.y = y + a * v.y; out.z = z + a * v.z; out.w = w + a * v.w;
        }
        public void mul(double a) {
            x *= a; y *= a; z *= a; w *= a;
        }
    }

    public void resetSimulationValues(Double[] parameters)
    {
        m1 = parameters[0];
        m2 = parameters[1];
        l1 = parameters[2];
        l2 = parameters[3];
        startState = new Vec4();
        state = new Vec4();
        positions = new Vec4();

        startState.x = parameters[4];
        startState.y = parameters[5];
    }
}