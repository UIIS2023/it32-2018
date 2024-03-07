package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class DrawingFrame extends JFrame {

	private DrawingView view = new DrawingView();
	private DrawingController controller;

	private final ButtonGroup btnGroup = new ButtonGroup();
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnHexagon;
	private JToggleButton tglbtnSelection;
	private JToggleButton tglbtnDelete;
	private JToggleButton tglbtnModify;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnFullBack;
	private JButton btnOneBack;
	private JButton btnOneForward;
	private JButton btnFullForward;
	private JButton btnInnerColor;
	private JButton btnOutlineColor;
	private JScrollPane scrollPane;
	private DefaultListModel<String> log;
	private JList list;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnOptions;
	private JMenuItem mntmHideLog;
	private JMenuItem mntmSaveLog;
	private JMenuItem mntmSaveDrawing;
	private JMenuItem mntmLoadLog;
	private JMenuItem mntmLoadDrawing;
	private JButton btnLoad;
	private JMenuItem mntmClearDrawing;
	private JMenuItem mntmExportAsPng;

	public DrawingFrame() {
		setTitle("Majkic Dragan IT32/2018");

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});

		getContentPane().add(view, BorderLayout.CENTER);
		view.setBackground(Color.white);

		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(toolBar, BorderLayout.WEST);

		ImageIcon point = new ImageIcon(getClass().getResource("/point.png"));
		tglbtnPoint = new JToggleButton(point);
		tglbtnPoint.setToolTipText("Draw point");
		setButtonBorder(tglbtnPoint, false);
		toolBar.add(tglbtnPoint);
		btnGroup.add(tglbtnPoint);

		ImageIcon line = new ImageIcon(getClass().getResource("/line.png"));
		tglbtnLine = new JToggleButton(line);
		tglbtnLine.setToolTipText("Draw line");
		setButtonBorder(tglbtnLine, false);
		toolBar.add(tglbtnLine);
		btnGroup.add(tglbtnLine);

		ImageIcon rectangle = new ImageIcon(getClass().getResource("/rectangle.png"));
		tglbtnRectangle = new JToggleButton(rectangle);
		tglbtnRectangle.setToolTipText("Draw rectangle");
		setButtonBorder(tglbtnRectangle, false);
		toolBar.add(tglbtnRectangle);
		btnGroup.add(tglbtnRectangle);

		ImageIcon circle = new ImageIcon(getClass().getResource("/circle.png"));
		tglbtnCircle = new JToggleButton(circle);
		tglbtnCircle.setToolTipText("Draw circle");
		setButtonBorder(tglbtnCircle, false);
		toolBar.add(tglbtnCircle);
		btnGroup.add(tglbtnCircle);

		ImageIcon donut = new ImageIcon(getClass().getResource("/donut.png"));
		tglbtnDonut = new JToggleButton(donut);
		tglbtnDonut.setToolTipText("Draw donut");
		setButtonBorder(tglbtnDonut, false);
		toolBar.add(tglbtnDonut);
		btnGroup.add(tglbtnDonut);

		ImageIcon hexagon = new ImageIcon(getClass().getResource("/hexagon.png"));
		tglbtnHexagon = new JToggleButton(hexagon);
		tglbtnHexagon.setToolTipText("Draw hexagon");
		setButtonBorder(tglbtnHexagon, false);
		toolBar.add(tglbtnHexagon);
		btnGroup.add(tglbtnHexagon);

		toolBar.add(Box.createVerticalGlue());

		btnInnerColor = new JButton("          ");
		btnInnerColor.setToolTipText("Set inner color");
		btnInnerColor.setPreferredSize(new Dimension(30, 30));
		btnInnerColor.setBorderPainted(false);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeColor("Set Inner Color ", btnInnerColor);
			}
		});
		toolBar.add(btnInnerColor);
		btnInnerColor.setBackground(Color.white);
		btnInnerColor.setFocusPainted(false);

		btnOutlineColor = new JButton("          ");
		btnOutlineColor.setToolTipText("Set outline color");
		btnOutlineColor.setPreferredSize(new Dimension(30, 30));
		btnOutlineColor.setBorderPainted(false);
		btnOutlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeColor("Set Outline Color ", btnOutlineColor);
			}
		});
		toolBar.add(btnOutlineColor);
		btnOutlineColor.setBackground(Color.black);
		btnOutlineColor.setFocusPainted(false);

		JToolBar toolBarCMD = new JToolBar();
		getContentPane().add(toolBarCMD, BorderLayout.NORTH);

		tglbtnSelection = new JToggleButton("Selection");
		toolBarCMD.add(tglbtnSelection);
		btnGroup.add(tglbtnSelection);

		tglbtnModify = new JToggleButton("Modify");
		tglbtnModify.setEnabled(false);
		tglbtnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modifyShape();
			}
		});
		toolBarCMD.add(tglbtnModify);
		btnGroup.add(tglbtnModify);

		tglbtnDelete = new JToggleButton("Delete");
		tglbtnDelete.setEnabled(false);
		tglbtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deleteShape();
			}
		});
		toolBarCMD.add(tglbtnDelete);
		btnGroup.add(tglbtnDelete);

		ImageIcon leftArrow = new ImageIcon(getClass().getResource("/leftArrow.png"));
		btnUndo = new JButton(leftArrow);
		btnUndo.setEnabled(false);
		btnUndo.setToolTipText("Undo");
		setButtonBorder(btnUndo, false);

		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		toolBarCMD.add(btnUndo);

		ImageIcon rightArrow = new ImageIcon(getClass().getResource("/rightArrow.png"));
		btnRedo = new JButton(rightArrow);
		btnRedo.setEnabled(false);
		btnRedo.setToolTipText("Redo");
		setButtonBorder(btnRedo, false);

		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		toolBarCMD.add(btnRedo);
		tglbtnSelection.setFocusPainted(false);
		setButtonBorder(tglbtnModify, false);
		setButtonBorder(tglbtnDelete, false);

		btnLoad = new JButton("Load");
		btnLoad.setToolTipText("Load log and draw, line by line from log");
		btnLoad.setEnabled(false);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.drawFromLog();
			}
		});
		toolBarCMD.add(btnLoad);

		toolBarCMD.add(Box.createHorizontalGlue());

		ImageIcon fullBack = new ImageIcon(getClass().getResource("/bringBack.png"));
		btnFullBack = new JButton(fullBack);
		btnFullBack.setEnabled(false);
		btnFullBack.setToolTipText("Move selected item to back");
		setButtonBorder(btnFullBack, false);
		btnFullBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveFullBack();
			}
		});
		toolBarCMD.add(btnFullBack);

		ImageIcon oneBack = new ImageIcon(getClass().getResource("/oneLeft.png"));
		btnOneBack = new JButton(oneBack);
		btnOneBack.setEnabled(false);
		btnOneBack.setToolTipText("Move selected item one step back");
		setButtonBorder(btnOneBack, false);
		btnOneBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveOneBack();
			}
		});
		toolBarCMD.add(btnOneBack);

		ImageIcon oneForward = new ImageIcon(getClass().getResource("/oneRight.png"));
		btnOneForward = new JButton(oneForward);
		btnOneForward.setEnabled(false);
		btnOneForward.setToolTipText("Move selected item one step forward");
		setButtonBorder(btnOneForward, false);
		btnOneForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveOneForward();
			}
		});
		toolBarCMD.add(btnOneForward);

		ImageIcon fullTop = new ImageIcon(getClass().getResource("/BringTop.png"));
		btnFullForward = new JButton(fullTop);
		btnFullForward.setEnabled(false);
		btnFullForward.setToolTipText("Move selected item to top");
		setButtonBorder(btnFullForward, false);
		btnFullForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveFullForward();
			}
		});
		toolBarCMD.add(btnFullForward);

		log = new DefaultListModel<String>();

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane, BorderLayout.SOUTH);

		list = new JList();
		list.setForeground(new Color(30, 144, 255));
		scrollPane.setViewportView(list);
		list.setModel(log);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmSaveLog = new JMenuItem("Save log");
		mntmSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		mnFile.add(mntmSaveLog);

		mntmSaveDrawing = new JMenuItem("Save drawing");
		mntmSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		mnFile.add(mntmSaveDrawing);

		mntmLoadLog = new JMenuItem("Load log");
		mntmLoadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		mnFile.add(mntmLoadLog);

		mntmLoadDrawing = new JMenuItem("Load drawing");
		mntmLoadDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		mnFile.add(mntmLoadDrawing);

		mntmExportAsPng = new JMenuItem("Export as png");
		mntmExportAsPng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.exportAsPng();
			}
		});
		mnFile.add(mntmExportAsPng);

		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		mntmHideLog = new JMenuItem("Hide log");
		mntmHideLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.hideLog();
			}
		});
		mnOptions.add(mntmHideLog);

		mntmClearDrawing = new JMenuItem("Clear drawing");
		mntmClearDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clearDrawing();
			}
		});
		mnOptions.add(mntmClearDrawing);

	}

	private void setButtonBorder(JButton button, boolean show) {
		button.setBorderPainted(show);
		button.setFocusPainted(show);
		button.setContentAreaFilled(show);
	}

	private void setButtonBorder(JToggleButton button, boolean show) {
		button.setFocusPainted(show);
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public JToggleButton getTglbtnSelection() {
		return tglbtnSelection;
	}

	public JToggleButton getTglbtnDelete() {
		return tglbtnDelete;
	}

	public JToggleButton getTglbtnModify() {
		return tglbtnModify;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JButton getBtnFullBack() {
		return btnFullBack;
	}

	public JButton getBtnOneBack() {
		return btnOneBack;
	}

	public JButton getBtnOneForward() {
		return btnOneForward;
	}

	public JButton getBtnFullForward() {
		return btnFullForward;
	}

	public DefaultListModel<String> getLog() {
		return log;
	}

	public JList getList() {
		return list;
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JMenuItem getMntmHideLog() {
		return mntmHideLog;
	}

}
