package mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class DrawingView extends JPanel {

	private DrawingModel model = new DrawingModel();

	public DrawingView() {

	}

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext()) {
			iterator.next().draw(g);
		}
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

}
