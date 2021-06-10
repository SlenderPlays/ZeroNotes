package ro.zero.zeronotes.storage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataStorageManager {
	// ============================
	// Static variables and methods
	// ============================
	private static final String SAVEDATA_FILE_NAME = "appData.zn.json";
	private static DataStorageManager _instance = null;

	public static DataStorageManager getInstance() {
		if(_instance == null) {
			throw new NullPointerException("StorageManager is null. You should first instantiate it with StorageManager.init(...) inside the main Activity.");
		} else {
			return _instance;
		}
	}
	public static void init(Context context) {
		_instance = new DataStorageManager(context);
	}

	// ============================
	// Non-static vars and methods
	// ============================

	public SaveData saveData = null;
	public String saveDataFilePath;

	private DataStorageManager(Context context) {
		saveDataFilePath = context.getFilesDir().getPath() + "/" + SAVEDATA_FILE_NAME;
		File saveDataFile = new File(context.getFilesDir(),SAVEDATA_FILE_NAME);

		if(saveDataFile.exists()) {
			try {
				Scanner stream = new Scanner(saveDataFile);
				try {
					StringBuilder sb = new StringBuilder();
					while (stream.hasNextLine()) {
						sb.append(stream.nextLine());
					}
					saveData = new Gson().fromJson(sb.toString(), SaveData.class);
				}
				finally {
					stream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				createNewSaveData();
			}
		} else {
			createNewSaveData();
		}
	}

	private void createNewSaveData() {
		saveData = new SaveData();
		save();
	}

	public void save() {
		try {
			FileWriter stream = new FileWriter(saveDataFilePath);
			try {
				String str = new Gson().toJson(saveData);
				stream.write(str);
			}
			finally {
				stream.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
