package mvc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.ButtonsObservable;
import observer.ButtonsObserver;
import strategy.Save;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SaveManager;

public class DrawingController {

	// mvc
	private DrawingModel model;
	private DrawingFrame frame;

	// select
	private ArrayList<Shape> selectedShapes = new ArrayList<>();
	private Point startPoint;

	// command
	private ArrayList<Command> cmdList = new ArrayList<Command>();
	private int cmdPosition;

	// observer
	private ButtonsObservable buttonsObservable;
	private ButtonsObserver buttonsObserver;

	// log
	private int logPosition;
	private int logSize;

	public DrawingController() {

	}

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;

		buttonsObservable = new ButtonsObservable();
		buttonsObserver = new ButtonsObserver(frame);
		buttonsObservable.addObserver(buttonsObserver);
	}

	// Select and draw

	public void mouseClicked(MouseEvent e) {
		Command cmd = null;

		if (frame.getTglbtnSelection().isSelected()) {
			Shape selectedShape = null;
			for (Iterator<Shape> iterator = this.model.getShapes().iterator(); iterator.hasNext();) {
				Shape shape = iterator.next();
				if (shape.contains(e.getX(), e.getY())) {
					selectedShape = shape;
				}
			}
			if (selectedShape != null) {
				if (this.selectedShapes.contains(selectedShape)) {
					this.selectedShapes.remove(selectedShape);
					selectedShape.setSelected(false);
					setButtonsDisable();
					frame.getLog().addElement("UnselectShape " + selectedShape);
				} else {
					this.selectedShapes.add(selectedShape);
					selectedShape.setSelected(true);
					frame.getLog().addElement("SelectShape " + selectedShape);
				}

				checkSelectedItems();

			} else {
				this.setSelectedForShapes(false);
			}
		} else {
			this.setSelectedForShapes(false);
			if (frame.getTglbtnPoint().isSelected()) {
				Point point = new Point(e.getX(), e.getY(), false, frame.getBtnOutlineColor().getBackground());
				cmd = new AddShapeCmd(point, model);
				frame.getLog().addElement("Add " + point);
			} else if (frame.getTglbtnLine().isSelected()) {
				if (startPoint == null) {
					startPoint = new Point(e.getX(), e.getY());
				} else {
					Line line = new Line(startPoint, new Point(e.getX(), e.getY()), false,
							frame.getBtnOutlineColor().getBackground());
					cmd = new AddShapeCmd(line, model);
					frame.getLog().addElement("Add " + line);
					startPoint = null;
				}
			} else if (frame.getTglbtnRectangle().isSelected()) {
				DlgRectangle dlgRectangle = new DlgRectangle();

				dlgRectangle.getTxtULPX().setText(Integer.toString(e.getX()));
				dlgRectangle.getTxtULPY().setText(Integer.toString(e.getY()));

				dlgRectangle.getTxtULPX().setEditable(false);
				dlgRectangle.getTxtULPY().setEditable(false);

				dlgRectangle.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlgRectangle.getBtnOutlineColor().setBackground(frame.getBtnOutlineColor().getBackground());

				dlgRectangle.setVisible(true);
				if (dlgRectangle.isOk) {
					Rectangle rectangle = dlgRectangle.getRectangle();
					cmd = new AddShapeCmd(rectangle, model);
					frame.getLog().addElement("Add " + rectangle);
				}

			} else if (frame.getTglbtnCircle().isSelected()) {
				DlgCircle dlgCircle = new DlgCircle();

				dlgCircle.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlgCircle.getTxtCenterY().setText(Integer.toString(e.getY()));

				dlgCircle.getTxtCenterX().setEditable(false);
				dlgCircle.getTxtCenterY().setEditable(false);

				dlgCircle.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlgCircle.getBtnOutlineColor().setBackground(frame.getBtnOutlineColor().getBackground());

				dlgCircle.setVisible(true);
				if (dlgCircle.isOk) {
					Circle circle = dlgCircle.getCircle();
					cmd = new AddShapeCmd(circle, model);
					frame.getLog().addElement("Add " + circle);
				}
			} else if (frame.getTglbtnDonut().isSelected()) {
				DlgDonut dlgDonut = new DlgDonut();

				dlgDonut.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlgDonut.getTxtCenterY().setText(Integer.toString(e.getY()));

				dlgDonut.getTxtCenterX().setEditable(false);
				dlgDonut.getTxtCenterY().setEditable(false);

				dlgDonut.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlgDonut.getBtnOutlineColor().setBackground(frame.getBtnOutlineColor().getBackground());

				dlgDonut.setVisible(true);
				if (dlgDonut.isOk) {
					Donut donut = dlgDonut.getDonut();
					cmd = new AddShapeCmd(donut, model);
					frame.getLog().addElement("Add " + donut);
				}
			} else if (frame.getTglbtnHexagon().isSelected()) {
				DlgHexagon dlgHexagon = new DlgHexagon();

				dlgHexagon.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlgHexagon.getTxtCenterY().setText(Integer.toString(e.getY()));

				dlgHexagon.getTxtCenterX().setEditable(false);
				dlgHexagon.getTxtCenterY().setEditable(false);

				dlgHexagon.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
				dlgHexagon.getBtnOutlineColor().setBackground(frame.getBtnOutlineColor().getBackground());

				dlgHexagon.setVisible(true);
				if (dlgHexagon.isOk) {
					HexagonAdapter hexagon = dlgHexagon.getHexagon();
					cmd = new AddShapeCmd(hexagon, model);
					frame.getLog().addElement("Add " + hexagon);
				}
			}

			if (cmd != null) {
				cmd.execute();

				checkPositionAndDelete();

				cmdPosition++;
				cmdList.add(cmd);
			}
		}

		frame.repaint();
	}

	// delete

	public void deleteShape() {
		if (JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete selected item?", "Delete?",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			Command cmd = new RemoveShapeCmd(selectedShapes, model);
			cmd.execute();

			checkPositionAndDelete();

			cmdList.add(cmd);
			cmdPosition++;

			frame.getLog().addElement("Delete [" + selectedShapes + "]");

			this.setSelectedForShapes(false);
		}

		frame.getTglbtnSelection().setSelected(true);
		frame.repaint();
	}

	// modify

	public void modifyShape() {
		Shape selected = selectedShapes.get(0);
		Command cmdModify = null;
		if (selected != null) {
			selectedShapes.get(0).setSelected(false);
			selectedShapes.clear();
			if (selected instanceof Point) {
				Point point = (Point) selected;
				DlgPoint dlgPoint = new DlgPoint();
				dlgPoint.getTxtX().setText((Integer.toString(point.getX())));
				dlgPoint.getTxtY().setText((Integer.toString(point.getY())));
				dlgPoint.getBtnColor().setBackground(point.getColor());

				dlgPoint.setVisible(true);

				if (dlgPoint.isOk) {
					cmdModify = new UpdatePointCmd(point, dlgPoint.getPoint());
					selectedShapes.add(point);
					frame.getLog().addElement("Modify " + dlgPoint.getPoint());
				}
			} else if (selected instanceof Line) {
				Line line = (Line) selected;
				DlgLine dlgLine = new DlgLine();
				dlgLine.getTxtStartPointX().setText((Integer.toString(line.getStartPoint().getX())));
				dlgLine.getTxtStartPointY().setText((Integer.toString(line.getStartPoint().getY())));
				dlgLine.getTxtEndPointX().setText((Integer.toString(line.getEndPoint().getX())));
				dlgLine.getTxtEndPointY().setText((Integer.toString(line.getEndPoint().getY())));
				dlgLine.getBtnOutlineColor().setBackground(line.getColor());

				dlgLine.setVisible(true);

				if (dlgLine.isOk) {
					cmdModify = new UpdateLineCmd(line, dlgLine.getLine());
					selectedShapes.add(line);
					frame.getLog().addElement("Modify " + dlgLine.getLine());
				}
			} else if (selected instanceof Donut) {
				Donut donut = (Donut) selected;
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.getTxtCenterX().setText((Integer.toString(donut.getCenter().getX())));
				dlgDonut.getTxtCenterY().setText((Integer.toString(donut.getCenter().getY())));
				dlgDonut.getTxtInnerRadius().setText((Integer.toString(donut.getInnerRadius())));
				dlgDonut.getTxtOuterRadius().setText((Integer.toString(donut.getRadius())));
				dlgDonut.getBtnOutlineColor().setBackground(donut.getColor());
				dlgDonut.getBtnInnerColor().setBackground(donut.getInnerColor());

				dlgDonut.setVisible(true);

				if (dlgDonut.isOk) {
					cmdModify = new UpdateDonutCmd(donut, dlgDonut.getDonut());
					selectedShapes.add(donut);
					frame.getLog().addElement("Modify " + dlgDonut.getDonut());
				}
			} else if (selected instanceof Circle) {
				Circle circle = (Circle) selected;
				DlgCircle dlgCircle = new DlgCircle();
				dlgCircle.getTxtCenterX().setText((Integer.toString(circle.getCenter().getX())));
				dlgCircle.getTxtCenterY().setText((Integer.toString(circle.getCenter().getY())));
				dlgCircle.getTxtRadius().setText((Integer.toString(circle.getRadius())));
				dlgCircle.getBtnOutlineColor().setBackground(circle.getColor());
				dlgCircle.getBtnInnerColor().setBackground(circle.getInnerColor());

				dlgCircle.setVisible(true);

				if (dlgCircle.isOk) {
					cmdModify = new UpdateCircleCmd(circle, dlgCircle.getCircle());
					selectedShapes.add(circle);
					frame.getLog().addElement("Modify " + dlgCircle.getCircle());
				}
			} else if (selected instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) selected;
				DlgRectangle dlgRectangle = new DlgRectangle();
				dlgRectangle.getTxtULPX().setText((Integer.toString(rectangle.getUpperLeftPoint().getX())));
				dlgRectangle.getTxtULPY().setText((Integer.toString(rectangle.getUpperLeftPoint().getY())));
				dlgRectangle.getTxtHeight().setText((Integer.toString(rectangle.getHeight())));
				dlgRectangle.getTxtWidth().setText((Integer.toString(rectangle.getWidth())));
				dlgRectangle.getBtnOutlineColor().setBackground(rectangle.getColor());
				dlgRectangle.getBtnInnerColor().setBackground(rectangle.getInnerColor());

				dlgRectangle.setVisible(true);

				if (dlgRectangle.isOk) {
					cmdModify = new UpdateRectangleCmd(rectangle, dlgRectangle.getRectangle());
					selectedShapes.add(rectangle);
					frame.getLog().addElement("Modify " + dlgRectangle.getRectangle());
				}
			} else if (selected instanceof HexagonAdapter) {
				HexagonAdapter hexagonAdapter = (HexagonAdapter) selected;
				DlgHexagon dlgHexagon = new DlgHexagon();

				dlgHexagon.getTxtCenterX().setText(Integer.toString(hexagonAdapter.getHexagon().getX()));
				dlgHexagon.getTxtCenterY().setText(Integer.toString(hexagonAdapter.getHexagon().getY()));
				dlgHexagon.getTxtRadius().setText(Integer.toString(hexagonAdapter.getHexagon().getR()));
				dlgHexagon.getBtnOutlineColor().setBackground(hexagonAdapter.getHexagon().getBorderColor());
				dlgHexagon.getBtnInnerColor().setBackground(hexagonAdapter.getHexagon().getAreaColor());

				dlgHexagon.setVisible(true);

				if (dlgHexagon.isOk) {
					cmdModify = new UpdateHexagonCmd(hexagonAdapter, dlgHexagon.getHexagon());
					selectedShapes.add(hexagonAdapter);
					frame.getLog().addElement("Modify " + dlgHexagon.getHexagon());
				}
			}
			if (cmdModify != null) {
				cmdModify.execute();

				checkPositionAndDelete();

				cmdList.add(cmdModify);
				cmdPosition++;

				buttonsObservable.setModify(true);
			} else {
				buttonsObservable.setModify(false);
				buttonsObservable.setDelete(false);
				buttonsObservable.setzAxis(false);
			}

			if (selectedShapes.size() == 1) {
				selectedShapes.get(0).setSelected(true);
			}

			frame.repaint();
		}

		frame.getTglbtnSelection().setSelected(true);
	}

	// undo redo

	public void undo() {
		Command cmd = cmdList.get(cmdPosition - 1);
		cmd.unexecute();
		cmdPosition--;
		frame.getLog().addElement("Undo");

		setSelectedForShapes(false);

		buttonsObservable.setRedo(true);
		if (cmdPosition == 0) {
			buttonsObservable.setUndo(false);
		}
		frame.repaint();

	}

	public void redo() {
		Command cmd = cmdList.get(cmdPosition);
		cmd.execute();
		frame.getLog().addElement("Redo");

		cmdPosition++;

		buttonsObservable.setUndo(true);
		if (cmdPosition == cmdList.size()) {
			buttonsObservable.setRedo(false);
		}

		frame.repaint();

	}

	// brings

	public void moveFullBack() {
		int index = model.getShapes().indexOf(selectedShapes.get(0));
		BringToBackCmd bringToBackCmd = new BringToBackCmd(index, model);
		bringToBackCmd.execute();

		frame.getLog().addElement("BringFullBack " + selectedShapes.get(0));

		checkPositionAndDelete();
		cmdList.add(bringToBackCmd);
		cmdPosition++;

		frame.repaint();
	}

	public void moveOneBack() {
		int index = model.getShapes().indexOf(selectedShapes.get(0));
		if (index == 0) {
			return;
		}
		ToBackCmd toBackCmd = new ToBackCmd(index, model);
		toBackCmd.execute();

		frame.getLog().addElement("BringOneBack " + selectedShapes.get(0));

		checkPositionAndDelete();
		cmdList.add(toBackCmd);
		cmdPosition++;

		frame.repaint();
	}

	public void moveFullForward() {
		int index = model.getShapes().indexOf(selectedShapes.get(0));

		BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(index, model);
		bringToFrontCmd.execute();

		frame.getLog().addElement("BringFullForward " + selectedShapes.get(0));

		checkPositionAndDelete();
		cmdList.add(bringToFrontCmd);
		cmdPosition++;

		frame.repaint();
	}

	public void moveOneForward() {
		int index = model.getShapes().indexOf(selectedShapes.get(0));
		if (index == model.getShapes().size() - 1) {
			return;
		}
		ToFrontCmd toFrontCmd = new ToFrontCmd(index, model);
		toFrontCmd.execute();

		frame.getLog().addElement("BringOneForward " + selectedShapes.get(0));

		checkPositionAndDelete();
		cmdList.add(toFrontCmd);
		cmdPosition++;

		frame.repaint();
	}

	// options

	public void changeColor(String name, JButton btn) {
		Color color = JColorChooser.showDialog(null, "Choose Color", btn.getBackground());
		if (color != null) {
			btn.setBackground(color);
			frame.getLog().addElement(name + color.getRGB());
		}
	}

	public void clearDrawing() {
		frame.getLog().clear();
		model.getShapes().clear();
		setSelectedForShapes(false);
		setButtonsDisable();
		buttonsObservable.setLoadLog(false);
		buttonsObservable.setUndo(false);
		buttonsObservable.setRedo(false);
		cmdList.clear();
		cmdPosition = 0;

		frame.repaint();
	}

	public void hideLog() {
		if (frame.getMntmHideLog().getText().contentEquals("Hide log")) {
			frame.getScrollPane().setVisible(false);
			frame.pack();
			frame.setSize(1000, 750);
			frame.getMntmHideLog().setText("Show log");
		} else {
			frame.pack();
			frame.getMntmHideLog().setText("Hide log");
			frame.getScrollPane().setVisible(true);
			frame.setSize(1000, 750);
		}
	}

	// save log and drawing

	public void saveLog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save a log");
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(extensionFilter);
		int retval = fileChooser.showSaveDialog(frame.getParent());
		if (retval == JFileChooser.APPROVE_OPTION) {
			File file;
			if (fileChooser.getSelectedFile().toString().contains(".txt")) {
				file = new File(fileChooser.getSelectedFile().toString());
			} else {
				file = new File(fileChooser.getSelectedFile() + ".txt");
			}
			String log = "";
			for (int i = 0; i < frame.getLog().getSize(); i++) {
				log += frame.getLog().getElementAt(i) + "\n";
			}
			Save saveLog = new SaveLog();
			Save saveManager = new SaveManager(saveLog);

			saveManager.save(file, log);

		}
	}

	public void saveDrawing() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save a drawing");
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".ser", "ser");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(extensionFilter);
		int retval = fileChooser.showSaveDialog(frame.getParent());
		if (retval == JFileChooser.APPROVE_OPTION) {
			File file;
			if (fileChooser.getSelectedFile().toString().contains(".ser")) {
				file = new File(fileChooser.getSelectedFile().toString());
			} else {
				file = new File(fileChooser.getSelectedFile() + ".ser");
			}

			Save saveDrawing = new SaveDrawing();
			Save saveManager = new SaveManager(saveDrawing);

			saveManager.save(file, this.model.getShapes());

		}
	}

	// load log and drawing

	@SuppressWarnings("unchecked")
	public void loadDrawing() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Load a drawing");
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".ser", "ser");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(extensionFilter);
		int retval = fileChooser.showOpenDialog(frame.getParent());
		if (retval == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();
			try {
				FileInputStream fos = null;
				ObjectInputStream ois = null;
				boolean keep = true;

				try {
					fos = new FileInputStream(file);
					ois = new ObjectInputStream(fos);
					clearDrawing();

					model.getShapes().addAll((ArrayList<Shape>) ois.readObject());
					frame.repaint();
				} catch (Exception e) {
					keep = false;
				} finally {
					try {
						if (ois != null)
							ois.close();
						if (fos != null)
							fos.close();
						if (keep == false)
							file.delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "Drawing loaded!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Wrong file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void loadLog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Load a log");
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(extensionFilter);
		int retval = fileChooser.showOpenDialog(frame.getParent());
		if (retval == JFileChooser.APPROVE_OPTION) {
			clearDrawing();
			File file = fileChooser.getSelectedFile();
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					frame.getLog().addElement(line);
				}

				logSize = frame.getLog().getSize();
				logPosition = 0;
				buttonsObservable.setLoadLog(true);
				frame.repaint();
				JOptionPane.showMessageDialog(null, "Log loaded!");
				bufferedReader.close();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Wrong file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void exportAsPng() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export drawing as png");
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(".png", "png");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(extensionFilter);
		int retval = fileChooser.showSaveDialog(frame.getParent());
		if (retval == JFileChooser.APPROVE_OPTION) {
			frame.getLog().clear();
			File file;
			if (fileChooser.getSelectedFile().toString().contains(".png")) {
				file = new File(fileChooser.getSelectedFile().toString());
			} else {
				file = new File(fileChooser.getSelectedFile() + ".png");
			}

			Container c = frame.getView();
			BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
			c.paint(im.getGraphics());
			try {
				ImageIO.write(im, "png", file);
				JOptionPane.showMessageDialog(null, "Image saved!");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Wrong file!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// draw from log

	public void drawFromLog() {
		if (logPosition == logSize) {
			buttonsObservable.setLoadLog(false);
			return;
		}
		String line = frame.getLog().getElementAt(logPosition);
		frame.getList().setSelectedIndex(logPosition);
		frame.getList().ensureIndexIsVisible(logPosition + 1);
		String[] elements = line.trim().split(("[\\(\\)\\[\\]\\ \\,\\@]+"));

		Command cmd = null;
		try {
			if (line.contains("Add")) {
				Shape addShape = returnShape(line, elements);
				cmd = new AddShapeCmd(addShape, model);

			} else if (line.contains("Modify")) {
				Shape oldState = selectedShapes.get(0);
				Shape newState = returnShape(line, elements);

				if (oldState instanceof Point) {
					cmd = new UpdatePointCmd((Point) oldState, (Point) newState);
				} else if (oldState instanceof Line) {
					cmd = new UpdateLineCmd((Line) oldState, (Line) newState);
				} else if (oldState instanceof Donut) {
					cmd = new UpdateDonutCmd((Donut) oldState, (Donut) newState);
				} else if (oldState instanceof Circle) {
					cmd = new UpdateCircleCmd((Circle) oldState, (Circle) newState);
				} else if (oldState instanceof Rectangle) {
					cmd = new UpdateRectangleCmd((Rectangle) oldState, (Rectangle) newState);
				} else if (oldState instanceof HexagonAdapter) {
					cmd = new UpdateHexagonCmd((HexagonAdapter) oldState, (HexagonAdapter) newState);
				}

			} else if (line.contains("Delete")) {
				cmd = new RemoveShapeCmd(selectedShapes, model);
				selectedShapes.forEach(s -> s.setSelected(false));
				selectedShapes.clear();
			} else if (line.contains("Bring")) {
				Shape selectedShape = returnShape(line, elements);
				int index = model.getShapes().indexOf(selectedShape);
				if (line.contains("BringOneForward")) {
					cmd = new ToFrontCmd(index, model);
				} else if (line.contains("BringFullForward")) {
					cmd = new BringToFrontCmd(index, model);
				} else if (line.contains("BringFullBack")) {
					cmd = new BringToBackCmd(index, model);
				} else if (line.contains("BringOneBack")) {
					cmd = new ToBackCmd(index, model);
				}
			} else if (line.contains("UnselectShape")) {
				Shape shape = returnSelectedShape(line, elements);

				this.selectedShapes.remove(shape);
				shape.setSelected(false);

				logPosition++;
				frame.repaint();
				checkSelectedItems();
				return;
			} else if (line.contains("SelectShape")) {
				Shape shape = returnSelectedShape(line, elements);

				this.selectedShapes.add(shape);
				shape.setSelected(true);
				checkSelectedItems();
			} else if (line.contains("Unselect All")) {
				selectedShapes.forEach(s -> s.setSelected(false));
				selectedShapes.clear();

				setButtonsDisable();
			} else if (line.contains("Undo")) {
				Command undoCmd = cmdList.get(cmdPosition - 1);
				undoCmd.unexecute();
				cmdPosition--;

				setSelectedForShapes(false);
			} else if (line.contains("Redo")) {
				Command redoCmd = cmdList.get(cmdPosition);
				redoCmd.execute();
				cmdPosition++;

				setSelectedForShapes(false);
			} else if (line.contains("Set Inner Color")) {
				frame.getBtnInnerColor().setBackground(new Color(Integer.parseInt(elements[3])));
			} else if (line.contains("Set Outline Color")) {
				frame.getBtnOutlineColor().setBackground(new Color(Integer.parseInt(elements[3])));
			}

			if (cmd != null) {
				cmd.execute();

				checkPositionAndDelete();

				cmdPosition++;
				cmdList.add(cmd);
			}
			logPosition++;
			frame.repaint();

			if (logPosition == logSize) {
				buttonsObservable.setLoadLog(false);
				return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"There is a problem with loading log! Check log, clear drawing in options and start again!",
					"Error", JOptionPane.ERROR_MESSAGE);

		}

	}

	// Private methods

	private Shape returnShape(String line, String[] elements) {
		Shape shape = null;
		if (line.contains("Point")) {
			shape = new Point(Integer.parseInt(elements[2]), Integer.parseInt(elements[3]), false,
					new Color(Integer.parseInt(elements[5])));
		} else if (line.contains("Line")) {
			shape = new Line(new Point(Integer.parseInt(elements[2]), Integer.parseInt(elements[3])),
					new Point(Integer.parseInt(elements[4]), Integer.parseInt(elements[5])), false,
					new Color(Integer.parseInt(elements[7])));
		} else if (line.contains("Rectangle")) {
			shape = new Rectangle(new Point(Integer.parseInt(elements[2]), Integer.parseInt(elements[3])),
					Integer.parseInt(elements[7]), Integer.parseInt(elements[5]), false,
					new Color(Integer.parseInt(elements[9])), new Color(Integer.parseInt(elements[11])));
		} else if (line.contains("Circle")) {
			shape = new Circle(new Point(Integer.parseInt(elements[2]), Integer.parseInt(elements[3])),
					Integer.parseInt(elements[5]), false, new Color(Integer.parseInt(elements[7])),
					new Color(Integer.parseInt(elements[9])));
		} else if (line.contains("Donut")) {
			shape = new Donut(new Point(Integer.parseInt(elements[2]), Integer.parseInt(elements[3])),
					Integer.parseInt(elements[5]), Integer.parseInt(elements[7]), false,
					new Color(Integer.parseInt(elements[9])), new Color(Integer.parseInt(elements[11])));
		} else if (line.contains("Hexagon")) {
			shape = new HexagonAdapter(Integer.parseInt(elements[2]), Integer.parseInt(elements[3]),
					Integer.parseInt(elements[5]), new Color(Integer.parseInt(elements[7])),
					new Color(Integer.parseInt(elements[9])));
		}

		return shape;
	}

	private Shape returnSelectedShape(String line, String[] elements) {
		Shape selectedShape = returnShape(line, elements);
		int index = model.getShapes().indexOf(selectedShape);
		Shape shape = model.getShape(index);

		return shape;
	}

	private void setSelectedForShapes(boolean value) {
		if (selectedShapes.size() == 0) {
			return;
		}
		for (Iterator<Shape> iterator = selectedShapes.iterator(); iterator.hasNext();) {
			iterator.next().setSelected(value);

		}
		frame.getLog().addElement("Unselect All");
		if (!value) {
			selectedShapes.clear();
		}

		setButtonsDisable();
	}

	private void setButtonsDisable() {
		buttonsObservable.setDelete(false);
		buttonsObservable.setModify(false);
		buttonsObservable.setzAxis(false);
	}

	private void checkSelectedItems() {
		if (this.selectedShapes.size() == 1) {
			buttonsObservable.setDelete(true);
			buttonsObservable.setModify(true);
			if (model.getShapes().size() > 1)
				buttonsObservable.setzAxis(true);

		} else if (this.selectedShapes.size() > 1) {
			buttonsObservable.setDelete(true);
			buttonsObservable.setModify(false);
			buttonsObservable.setzAxis(false);
		} else {
			this.setSelectedForShapes(false);

		}
	}

	private void checkPositionAndDelete() {
		if (cmdPosition < cmdList.size()) {
			for (int i = cmdList.size() - 1; i >= cmdPosition; i--) {
				cmdList.remove(i);
			}
		}
		buttonsObservable.setUndo(true);
		buttonsObservable.setRedo(false);
	}

}
