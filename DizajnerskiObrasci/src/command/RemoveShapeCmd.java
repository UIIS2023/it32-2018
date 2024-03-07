package command;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command {
	private ArrayList<Shape> selectedShapes;
	private ArrayList<Shape> deletedShapes;
	private DrawingModel model;

	public RemoveShapeCmd(ArrayList<Shape> selectedShapes, DrawingModel model) {
		this.selectedShapes = selectedShapes;
		this.model = model;
		this.deletedShapes = new ArrayList<Shape>();
		this.deletedShapes.addAll(selectedShapes);
	}

	@Override
	public void execute() {
		model.getShapes().removeAll(deletedShapes);
	}

	@Override
	public void unexecute() {
		model.getShapes().addAll(deletedShapes);
	}

}
