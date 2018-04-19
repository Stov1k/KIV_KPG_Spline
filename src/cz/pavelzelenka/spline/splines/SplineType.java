package cz.pavelzelenka.spline.splines;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Seznam krivek
 * @author Pavel Zelenka
 * @version 2018-04-15
 */
public enum SplineType {
	BSPLINE("B-Spline", new BSpline()),
	NATURAL_CUBIC_SPLINE("Natural Cubic", new NaturalCubicSpline());

	/** nazev */
    private final String name;

	/** krivka */
    private final Spline spline;
    
    /**
     * Konstruktor
     * @param name nazev
     * @param spline instance krivky
     */
    private SplineType(String name, Spline spline) {
        this.name = name;
        this.spline = spline;
    }

    /**
     * Vrati nazev
     * @return nazev
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Vrati instanci krivky
     * @return instance krivky
     */
	public Spline getSpline() {
		return spline;
	}

	/**
	 * Vrati odpovidajici typ krivky ze seznamu
	 * @param spline krivka
	 * @return typ krivky ze seznamu
	 */
	public static SplineType getSplineType(Spline spline) {
		for(SplineType splineType : SplineType.values()) {
			if(splineType.getSpline().equals(spline)) {
				return splineType;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Vrati seznam krivek
	 * @return seznam krivek
	 */
	public static ObservableList<SplineType> getDefaultList() {
		ObservableList<SplineType> result = FXCollections.observableArrayList(SplineType.values());
		return result;
	}
}
