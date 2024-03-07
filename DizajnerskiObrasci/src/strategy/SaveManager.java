package strategy;

import java.io.File;

public class SaveManager implements Save {
	private Save saveFile;

	public SaveManager(Save saveFile) {
		this.saveFile = saveFile;
	}

	@Override
	public void save(File file, Object obj) {
		saveFile.save(file, obj);
	}

}
