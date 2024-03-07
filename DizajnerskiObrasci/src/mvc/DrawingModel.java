package mvc;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel {
	private ArrayList<Shape> shapes = new ArrayList<>();

	public void addShape(Shape shape) {
		this.shapes.add(shape);
	}

	public void removeShape(Shape shape) {
		this.shapes.remove(shape);
	}

	public Shape getShape(int id) {
		return shapes.get(id);
	}

	public ArrayList<Shape> getShapes() {
		return this.shapes;
	}

}
