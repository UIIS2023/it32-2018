package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape {

	private Hexagon hexagon = new Hexagon(-1, -1, -1);

	public HexagonAdapter(int x, int y, int r, Color color, Color innerColor) {
		this.hexagon = new Hexagon(x, y, r);
		this.hexagon.setBorderColor(color);
		this.hexagon.setAreaColor(innerColor);
	}

	public HexagonAdapter() {

	}

	@Override
	public void draw(Graphics g) {

		if (isSelected()) {
			hexagon.setSelected(true);
		} else {
			hexagon.setSelected(false);
		}

		hexagon.paint(g);
	}

	@Override
	public void moveBy(int byX, int byY) {
		hexagon.setX(hexagon.getX() + byX);
		hexagon.setY(hexagon.getY() + byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Hexagon) {
			return (int) (hexagon.getR() - ((Hexagon) o).getR());
		}
		return 0;
	}

	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter ha = (HexagonAdapter) obj;
			if (this.hexagon.getX() == ha.getHexagon().getX() && this.hexagon.getY() == ha.getHexagon().getY()
					&& this.hexagon.getR() == ha.getHexagon().getR()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public String toString() {
		return "Hexagon [(" + hexagon.getX() + ", " + hexagon.getY() + ") " + ", Radius (" + hexagon.getR()
				+ "), OutlineColor (" + hexagon.getBorderColor().getRGB() + " )" + ", InnerColor ("
				+ hexagon.getAreaColor().getRGB() + " )]";
	}

	public HexagonAdapter clone() {
		HexagonAdapter original = new HexagonAdapter();
		original.hexagon.setX(this.hexagon.getX());
		original.hexagon.setY(this.hexagon.getY());
		original.hexagon.setR(this.hexagon.getR());
		original.hexagon.setBorderColor(this.hexagon.getBorderColor());
		original.hexagon.setAreaColor(this.hexagon.getAreaColor());
		original.hexagon.setSelected(this.hexagon.isSelected());

		return original;
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

}
