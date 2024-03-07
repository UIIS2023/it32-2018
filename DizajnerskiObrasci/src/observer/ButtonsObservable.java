package observer;

import java.util.Observable;

public class ButtonsObservable extends Observable {

	private boolean delete;
	private boolean modify;
	private boolean undo;
	private boolean redo;
	private boolean zAxis;
	private boolean loadLog;

	public ButtonsObservable() {
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
		setChanged();
		notifyObservers();
	}

	public void setModify(boolean modify) {
		this.modify = modify;
		setChanged();
		notifyObservers();
	}

	public void setUndo(boolean undo) {
		this.undo = undo;
		setChanged();
		notifyObservers();
	}

	public void setzAxis(boolean zAxis) {
		this.zAxis = zAxis;
		setChanged();
		notifyObservers();
	}

	public void setLoadLog(boolean loadLog) {
		this.loadLog = loadLog;
		setChanged();
		notifyObservers();
	}

	public void setRedo(boolean redo) {
		this.redo = redo;
		setChanged();
		notifyObservers();
	}

	public boolean isDelete() {
		return delete;
	}

	public boolean isModify() {
		return modify;
	}

	public boolean isUndo() {
		return undo;
	}

	public boolean isRedo() {
		return redo;
	}

	public boolean iszAxis() {
		return zAxis;
	}

	public boolean isLoadLog() {
		return loadLog;
	}

}
