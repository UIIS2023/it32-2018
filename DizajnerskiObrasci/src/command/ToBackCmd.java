package command;

import java.util.Collections;

import mvc.DrawingModel;

public class ToBackCmd implements Command {

	private int position;
	private DrawingModel model;

	public ToBackCmd(int position, DrawingModel model) {
		this.position = position;
		this.model = model;
	}

	@Override
	public void execute() {
		Collections.swap(model.getShapes(), position, position - 1);
	}

	@Override
	public void unexecute() {
		Collections.swap(model.getShapes(), position - 1, position);
	}

}
