package mob.assignment.mediaplayerassignment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static String path;
	private static int seekBarMax = 0;
	private static boolean newFileLoaded = false;
	public static final boolean DEBUG = true;
	public static boolean isStarted = false;
	private Button btnPlay;
	private Button btnPause;
	private Button btnStop;
	private Button btnBrowse;
	private static SeekBar sbProgress;
	// private static MediaPlayer mp;
	// private static Runnable r;
	// private static Thread t;
	MediaPlayerServices mService;
	boolean mBound;

	private ServiceConnection mConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnPlay = (Button) findViewById(R.id.playb);
		btnPause = (Button) findViewById(R.id.pauseb);
		btnStop = (Button) findViewById(R.id.stopb);
		btnBrowse = (Button) findViewById(R.id.browseb);
		sbProgress = (SeekBar) findViewById(R.id.sb);

		mConnection = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName className, IBinder service) {
				MediaPlayerServices.LocalBinder binder = (MediaPlayerServices.LocalBinder) service;
				if (DEBUG)
					Toast.makeText(getBaseContext(), "Connecting Service", Toast.LENGTH_SHORT).show();
				mService = binder.getService();
				mBound = true;
			}

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				mBound = false;
			}
		};
		
		addEventListeners();
		/*
		 * // Runnable responsible for updating the UI with playing progress. r
		 * = new Runnable() {
		 * 
		 * @Override public void run() { while (true) { try {
		 * sbProgress.setProgress((mp == null) ? 0 : mp .getCurrentPosition());
		 * Thread.sleep(250); } catch (InterruptedException e) {
		 * Log.e("MedaiPlayerThread", "ProgressFAIL", e); } } } }; t = new
		 * Thread(r); t.start();
		 */
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this, MediaPlayerServices.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	private void addEventListeners() {
		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mService != null)
					mService.play();
				else if (DEBUG)
					Toast.makeText(getBaseContext(), "mService is null - btnPlay", Toast.LENGTH_SHORT).show();
			}
		});
		btnPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mService.pause();
			}
		});

		btnStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mService.stop();
			}
		});

		btnBrowse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				newFileLoaded = true;
				Intent i = new Intent(getBaseContext(),
						FileBrowserActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});

		sbProgress
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int i,
							boolean b) {
						if (b) {
//							mp.seekTo(i);
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// Should do nothing
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// Should do nothing
					}
				});
	}

	 protected void onActivityResult(int requestCode, int resultCode, Intent data){
	        if (requestCode == 1){
	            if (resultCode == RESULT_OK) {
	                path = data.getStringExtra("filename");
	               mService.playSong(path);
	            }
	        }
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
