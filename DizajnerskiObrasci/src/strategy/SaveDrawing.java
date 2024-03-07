package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import geometry.Shape;

public class SaveDrawing implements Save {

	@Override
	public void save(File file, Object obj) {

		@SuppressWarnings("unchecked")
		ArrayList<Shape> shapes = (ArrayList<Shape>) obj;

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean keep = true;

		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(shapes);
			JOptionPane.showMessageDialog(null, "Drawing saved!");
		} catch (Exception e) {
			keep = false;
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
				if (keep == false)
					file.delete();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "There is a problem with saving drawing!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
