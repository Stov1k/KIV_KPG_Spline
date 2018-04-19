package cz.pavelzelenka.spline.splines;

import cz.pavelzelenka.spline.Point;

/**
 * Kubika
 * @author Pavel Zelenka
 * @version 2018-04-19
 */
public class Cubic {
    
	/** Body */
	private Point a, b, c, d;

	/**
	 * Instance kubiky
	 * @param a souradnice bodu
	 * @param b souradnice bodu
	 * @param c souradnice bodu
	 * @param d souradnice bodu
	 */
    public Cubic(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Vyhodnoceni kubiky
     * Y(t) = a + b*t + c*t^2 + d*t^3
     * @param t nalezici do [0,1]
     * @return bod
     */
    public Point evaluateCubic(double t) {
    	double x = a.getX() + t * (b.getX() + t * (c.getX() + t * d.getX()));
    	double y = a.getY() + t * (b.getY() + t * (c.getY() + t * d.getY()));
    	return new Point(x,y);
    }
}