package observer;

import java.util.Observable;
import java.util.Observer;

import mvc.DrawingFrame;

public class ButtonsObserver implements Observer {
	private boolean delete;
	private boolean modify;
	private boolean undo;
	private boolean redo;
	private boolean zAxis;
	private boolean loadLog;

	DrawingFrame frame;

	public ButtonsObserver(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void update(Observable o, Object arg) {
		ButtonsObservable buttonsObservable = (ButtonsObservable) o;
		this.delete = buttonsObservable.isDelete();
		this.modify = buttonsObservable.isModify();
		this.undo = buttonsObservable.isUndo();
		this.redo = buttonsObservable.isRedo();
		this.zAxis = buttonsObservable.iszAxis();
		this.loadLog = buttonsObservable.isLoadLog();

		enableButtons();
	}

	private void enableButtons() {
		frame.getTglbtnModify().setEnabled(this.modify);
		frame.getTglbtnDelete().setEnabled(this.delete);
		frame.getBtnUndo().setEnabled(this.undo);
		frame.getBtnRedo().setEnabled(this.redo);

		frame.getBtnFullBack().setEnabled(this.zAxis);
		frame.getBtnFullForward().setEnabled(this.zAxis);
		frame.getBtnOneBack().setEnabled(this.zAxis);
		frame.getBtnOneForward().setEnabled(this.zAxis);

		frame.getBtnLoad().setEnabled(this.loadLog);
	}

}
