package cz.pavelzelenka.spline.splines;

import java.util.ArrayList;
import java.util.List;

import cz.pavelzelenka.spline.Point;

/**
 * Kubicka krivka
 * Inspirovano na
 * 	- http://mathworld.wolfram.com/CubicSpline.html
 * 	- https://github.com/deric/curve-fit-demo/blob/master/src/main/java/org/clueminer/curve/fit/splines/NatCubic.java
 * @author Pavel Zelenka
 * @version 2018-04-19
 */
public class NaturalCubicSpline implements Spline {
	
	public static final int STEPS = 25;
	
	private List<Point> input;
	private List<Point> output;
	private List<Point> temp;
	
	public NaturalCubicSpline() {
		input = new ArrayList<Point>();
		output = new ArrayList<Point>();
		temp = new ArrayList<Point>();
	}
	
    public Cubic[] calculateNaturalCubic() {
    	Point[] points = input.stream().toArray(Point[]::new);
    	int n = input.size()-1;
        double[] gamma = new double[n + 1]; 
        Point[] delta = new Point[n + 1];        
        Point[] D = new Point[n + 1];
        gamma[0] = 1D / 2D;
        for (int i = 1; i < n; i++) {
            gamma[i] = 1 / (4 - gamma[i - 1]);
        }
        gamma[n] = 1 / (2 - gamma[n - 1]);
        delta[0] = new Point(3 * (points[1].getX() - points[0].getX()) * gamma[0], 3 * (points[1].getY() - points[0].getY()) * gamma[0]);
        for(int i = 1; i < n; i++) {
            delta[i] = new Point((3 * (points[i + 1].getX() - points[i - 1].getX()) - delta[i - 1].getX()) * gamma[i], (3 * (points[i + 1].getY() - points[i - 1].getY()) - delta[i - 1].getY()) * gamma[i]);
        }
        delta[n] = new Point((3 * (points[n].getX() - points[n - 1].getX()) - delta[n - 1].getX()) * gamma[n], (3 * (points[n].getY() - points[n - 1].getY()) - delta[n - 1].getY()) * gamma[n]);
        D[n] = new Point(delta[n].getX(), delta[n].getY());
        for(int i = n - 1; i >= 0; i--) {
        	double xloc = delta[i].getX() - gamma[i] * D[i + 1].getX();
        	double yloc = delta[i].getY() - gamma[i] * D[i + 1].getY();
            D[i] = new Point(xloc, yloc);
        }
        Cubic[] C = new Cubic[n];
        for(int i = 0; i < n; i++) {
        	Point a = new Point(points[i].getX(), points[i].getY());
        	Point b = new Point(D[i].getX(), D[i].getY());
        	Point c = new Point(3 * (points[i + 1].getX() - points[i].getX()) - 2 * D[i].getX() - D[i + 1].getX(), 3 * (points[i + 1].getY() - points[i].getY()) - 2 * D[i].getY() - D[i + 1].getY());
        	Point d = new Point(2 * (points[i].getX() - points[i + 1].getX()) + D[i].getX() + D[i + 1].getX(), 2 * (points[i].getY() - points[i + 1].getY()) + D[i].getY() + D[i + 1].getY());
            C[i] = new Cubic(a, b, c, d);
        }
        return C;
    }
    
    private List<Point> getCurvePoints() {
    	clearTemp();
        if (input.size() >= 2) {
            Cubic[] cubic = calculateNaturalCubic();
            for (int i = 0; i < cubic.length; i++) {
                for (int step = 0; step <= STEPS; step++) {
                    double t = (double) step / (double) STEPS;
                    temp.add(cubic[i].evaluateCubic(t));
                }
            }
        }
        return temp;
    }
	
	private void clearTemp() {
		if(this.temp != null) {
			this.temp.clear();
		} else {
			this.temp = new ArrayList<Point>();
		}
	}
    
	private void clearOutput() {
		if(this.output != null) {
			this.output.clear();
		} else {
			this.output = new ArrayList<Point>();
		}
	}
	
	private void evaluateCurve() {
		clearOutput();
		if(input != null && input.size() > 0) {
			output.add(new Point(input.get(0).getX(), input.get(0).getY()));
			output.addAll(getCurvePoints());
		}
	}
	
	public List<Point> getOutputPoints(List<Point> input) {
		this.input = input;
		evaluateCurve();
		return output;
	}
}
