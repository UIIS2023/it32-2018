package strategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class SaveLog implements Save {

	@Override
	public void save(File file, Object obj) {
		String log = (String) obj;
		FileWriter fw = null;

		try {
			fw = new FileWriter(file);
			fw.write(log);
			JOptionPane.showMessageDialog(null, "Log saved!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Wrong file!", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "There is a problem with saving log!", "Error",
						JOptionPane.ERROR_MESSAGE);

			}

		}

	}

}
