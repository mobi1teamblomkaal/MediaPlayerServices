package mob.assignment.mediaplayerassignment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.xmlpull.v1.XmlPullParserException;

import mob.assignment.rss.domain.model.Channel;
import mob.assignment.rss.util.xml.RssParser;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
	final Context context = this;
	private EditText input;
	RssParser rss;
	Channel ch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		root = new File("/"
				+ Environment.getExternalStorageDirectory().getPath()
				+ "/Music/");
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
			Intent i = new Intent();
			i.putExtra("filename", currentFile.getAbsolutePath());
			setResult(RESULT_OK, i);
			finish();
		} else {
			Toast.makeText(getBaseContext(),
					"The file type is not an audio file", Toast.LENGTH_SHORT)
					.show();
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
			LayoutInflater layoutInflater = LayoutInflater.from(context);

			View promptView = layoutInflater.inflate(R.layout.prompts, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			input = (EditText) promptView.findViewById(R.id.userInput);
			//String sinput = input.toString();
			// setup a dialog window
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// get user input and show it to the toast
									Toast.makeText(context, input.getText(),
											Toast.LENGTH_LONG).show();

									/** the link to be passed to the XML parser */
									try {
										rss.parse(input.toString());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (XmlPullParserException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

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
			
			
			setListAdapter(new ChannelAdapter(getBaseContext(), ch));
			Intent i = new Intent();
			i.putExtra("episode", ch.getEpisodeList());
			setResult(RESULT_OK, i);
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}