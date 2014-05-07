package mob.assignment.mediaplayerassignment;

import java.io.File;
import java.net.URL;

import mob.assignment.rss.domain.model.Channel;
import mob.assignment.rss.domain.model.Episode;
import mob.assignment.rss.util.xml.RssParser;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FileBrowserActivity extends ListActivity {
	private File[] data;
	private File root;
	private EditText input;
	private Channel ch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO change to run in AsuncTask
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = 
	             new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
		}
		
		populateListView();
	}

	private void populateListView() {
		switch (MainActivity.state) {
		case LOCAL:
			ch = null;
			root = new File("/"
					+ Environment.getExternalStorageDirectory().getPath()
					+ "/Music/");
			data = getFileData(root);
			setListAdapter(new FileAdapter(getBaseContext(), data));
			break;
		case PODCAST:
			data = null;
			try {
				ch = RssParser.parse(MainActivity.rss_url);
			} catch (Exception e) {
				//TODO Inform user of error!
				if (MainActivity.DEBUG) {
					e.printStackTrace();
					// get user input and show it to the toast
					Toast.makeText(getBaseContext(),
							MainActivity.rss_url,
							Toast.LENGTH_LONG).show();
				}
			}
			setListAdapter(new ChannelAdapter(getBaseContext(), ch));
			break;
		default:
			break;

		}
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
		switch (MainActivity.state) {
		case LOCAL:
			File currentFile = data[position];
			if (currentFile.isDirectory()) {
				data = getFileData(currentFile);
				setListAdapter(new FileAdapter(getBaseContext(), data));
			} else if (FileType.isMediaFile(currentFile)) {
				Intent i = new Intent();
				i.putExtra("filename", currentFile.getAbsolutePath());
				setResult(RESULT_OK, i);
				finish();
			} else {
				Toast.makeText(getBaseContext(),
						"The file type is not an audio file",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case PODCAST:
			Intent i = new Intent();
			i.putExtra("filename", ch.getEpisode(position).getLocation());
			setResult(RESULT_OK, i);
			finish();
			break;
		default:
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_browser, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_podcast:
			LayoutInflater layoutInflater = LayoutInflater.from(this);

			View promptView = layoutInflater.inflate(R.layout.prompts, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			input = (EditText) promptView.findViewById(R.id.userInput);
			
			// setup a dialog window
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									MainActivity.rss_url = input.getText().toString();
									MainActivity.state = State.PODCAST; //Changed state to PODCAST
									populateListView();

								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();

		case R.id.action_local_browse:
			MainActivity.state = State.LOCAL;
			populateListView();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}