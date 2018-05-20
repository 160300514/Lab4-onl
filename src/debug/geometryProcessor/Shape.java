package debug.geometryProcessor;

public abstract class Shape {
	
	//Getter Methods for properties shared by all shapes
	public abstract String getShape();
	public abstract String getName();
	public abstract String getColour();
	public abstract double getArea();

	//Setter Methods for properties shared by all shapes
	public abstract void setLength(double l);
	public abstract void setColour(String c);
	public abstract void setName(String n);
}
