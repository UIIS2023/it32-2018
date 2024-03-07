package command;

import java.util.Collections;

import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private int position;
	private DrawingModel model;

	public BringToBackCmd(int position, DrawingModel model) {
		this.position = position;
		this.model = model;
	}

	@Override
	public void execute() {
		for (int i = position; i > 0; i--) {
			Collections.swap(model.getShapes(), i, i - 1);
		}
	}

	@Override
	public void unexecute() {
		for (int i = 0; i < position; i++) {
			Collections.swap(model.getShapes(), i, i + 1);
		}

	}

}
