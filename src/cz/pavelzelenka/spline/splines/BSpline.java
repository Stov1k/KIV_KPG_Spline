package cz.pavelzelenka.spline.splines;

import java.util.ArrayList;
import java.util.List;

import cz.pavelzelenka.spline.Point;

/**
 * BSpline
 * @author Pavel Zelenka
 * @version 2018-04-19
 */
public class BSpline implements Spline {
	
	public static final int STEPS = 50;
	
	private List<Point> input;
	private List<Point> output;
	private List<Point> temp;
	
	public BSpline() {
		input = new ArrayList<Point>();
		output = new ArrayList<Point>();
		temp = new ArrayList<Point>();
	}
	
	public List<Point> curvePoints() {
		
		List<Point> inputCopy = new ArrayList<>();
		
		if(input.size() > 0) {
			for(int i=0; i<input.size(); i++) {
				inputCopy.add(input.get(i));
				if(i == 0) {
					inputCopy.add(new Point(input.get(i).getX(), input.get(i).getY()));
					inputCopy.add(new Point(input.get(i).getX(), input.get(i).getY()));
				}
				if(i == input.size()-1) {
					inputCopy.add(new Point(input.get(i).getX(), input.get(i).getY()));
					inputCopy.add(new Point(input.get(i).getX(), input.get(i).getY()));
				}
			}
		}
		
		Point[] points = inputCopy.stream().toArray(Point[]::new);
		int n = inputCopy.size();

		Point point = null;
		Point prev = null;
		boolean first = true;
		
		for(int i=1; i < n-2; i++) {
			
			Point a = new Point(points[i-1].getX(), points[i-1].getY());
			Point b = new Point(points[i].getX(), points[i].getY());
			Point c = new Point(points[i+1].getX(), points[i+1].getY());
			Point d = new Point(points[i+2].getX(), points[i+2].getY());
			
			Point s3 = new Point((-a.getX() + 3 * (b.getX() - c.getX()) + d.getX()) / 6, (-a.getY() + 3 * (b.getY() - c.getY()) + d.getY()) / 6 );
			Point s2 = new Point((a.getX() - 2 * b.getX() + c.getX()) / 2, (a.getY() - 2 * b.getY() + c.getY()) / 2);
			Point s1 = new Point((c.getX() - a.getX()) / 2, (c.getY() - a.getY()) / 2);	
			Point s0 = new Point((a.getX() + 4 * b.getX() + c.getX()) / 6, (a.getY() + 4 * b.getY() + c.getY()) / 6);
			
			for(int step=0; step <= STEPS; step++) {
				prev = point;
				Cubic cubic = new Cubic(s0, s1, s2, s3);
				double t = (double) step / (double) STEPS;
				point = cubic.evaluateCubic(t);
	            if (first) {
	            	first = false; 
	            } else { 
	            	temp.add(new Point(prev.getX(), prev.getY()));
	            	temp.add(new Point(point.getX(), point.getY()));
	            }
	         }
		}
		
		return temp;
	}
	
	public List<Point> getPoints(List<Point> ip) {
		if(input.size() > 0) {
			output.addAll(curvePoints());
		}
		return output;
	}
	
	private List<Point> getCurvePoints() {
    	clearTemp();
		curvePoints();
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
			output.addAll(getCurvePoints());
		}
	}
	
	public List<Point> getOutputPoints(List<Point> input) {
		this.input = input;
		evaluateCurve();
		return output;
	}
	
}
