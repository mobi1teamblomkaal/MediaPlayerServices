package mob.assignment.mediaplayerassignment;

import java.io.File;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileBrowserActivity extends ListActivity {
	private File[] data;
	private File root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// root = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
		// root = new File("/storage/sdcard0/Music/"); // Christian's Nexus
		root = new File("/"
				+ Environment.getExternalStorageDirectory().getPath()
				+ "/Music/"); // Andi's LG-E400
		data = getFileData(root);
		setListAdapter(new FileAdapter(getBaseContext(), data));
	}

	private File[] getFileData(File file) {
		File parentFile = file.getParentFile();
		if (parentFile != null) {
			int length = file.listFiles().length + 1;
			File[] list = new File[length];
			list[0] = parentFile;
			File[] children = file.listFiles();
			for (int i = 1; i < length; i++) {
				list[i] = children[i - 1];
			}
			return list;
		}
		return file.listFiles();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File currentFile = data[position];
		if (currentFile.isDirectory()) {
			data = getFileData(currentFile);
			setListAdapter(new FileAdapter(getBaseContext(), data));
		} else if (FileType.isMediaFile(currentFile)) {
			Intent playMediaIntent = new Intent(getBaseContext(),
					MainActivity.class);
			playMediaIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			MainActivity.path = currentFile.getAbsolutePath();
			startActivity(playMediaIntent);
		} else {
			Toast.makeText(getBaseContext(),
					"The file type is not an audio file", Toast.LENGTH_SHORT)
					.show();
		}
	}
}