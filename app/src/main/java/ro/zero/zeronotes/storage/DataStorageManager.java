package ro.zero.zeronotes.storage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used to house all of the Data Storage implementations, including loading, retrieving and saving data.
 */
public class DataStorageManager {
	// ============================
	// Static variables and methods
	// ============================
	private static final String SAVEDATA_FILE_NAME = "appData.zn.json";
	private static DataStorageManager _instance = null;

	// Singleton Pattern
	public static DataStorageManager getInstance() {
		if(_instance == null) {
			throw new NullPointerException("StorageManager is null. You should first instantiate it with StorageManager.init(...) inside the main Activity.");
		} else {
			return _instance;
		}
	}

	/**
	 * Initialise the DataStorageManager with a context. This method should be called in the main activity, and before any attempt to access this data is had.
	 * @param context
	 */
	public static void init(Context context) {
		_instance = new DataStorageManager(context);
	}

	// ============================
	// Non-static vars and methods
	// ============================

	public SaveData saveData = null;
	// The path to the file where our data is saved. Used subsequently in the save method.
	private final String saveDataFilePath;

	private DataStorageManager(Context context) {
		saveDataFilePath = context.getFilesDir().getPath() + "/" + SAVEDATA_FILE_NAME;
		File saveDataFile = new File(context.getFilesDir(),SAVEDATA_FILE_NAME);

		// If the file exists, attempt to read it. Otherwise create an empty file.
		if(saveDataFile.exists()) {
			// Try to open the file. If for whatever reason we can't, print the error.
			try {
				Scanner stream = new Scanner(saveDataFile);
				// Attempt to read everything from the file and then, using GSON, interpret the JSON data and create a new SaveData class object.
				// If any errors occur, still attempt close the stream. Due to security concerns and best practices, we don't want to have open
				// file handles when we don't need to.
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
