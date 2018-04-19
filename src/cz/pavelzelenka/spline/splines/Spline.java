package cz.pavelzelenka.spline.splines;

import java.util.List;

import cz.pavelzelenka.spline.Point;

/**
 * Krivka
 * @author Pavel Zelenka
 * @version 2018-04-16
 */
public interface Spline {
	
	public List<Point> getOutputPoints(List<Point> input);
	
}
