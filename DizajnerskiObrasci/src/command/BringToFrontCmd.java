package command;

import java.util.Collections;

import mvc.DrawingModel;

public class BringToFrontCmd implements Command {

	private int position;
	private DrawingModel model;

	public BringToFrontCmd(int position, DrawingModel model) {
		this.position = position;
		this.model = model;
	}

	@Override
	public void execute() {
		for (int i = position; i < model.getShapes().size() - 1; i++) {
			Collections.swap(model.getShapes(), i, i + 1);
		}
	}

	@Override
	public void unexecute() {
		for (int i = model.getShapes().size() - 1; i > position; i--) {
			Collections.swap(model.getShapes(), i, i - 1);
		}

	}

}
