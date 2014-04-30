package mob.assignment.mediaplayerassignment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final boolean DEBUG = false;

	private Button btnPlay;
	private Button btnPause;
	private Button btnStop;
	private Button btnBrowse;
	private SeekBar sbProgress;
	private MediaPlayerServices mService;
	private ServiceConnection mConnection;
	private boolean mBound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnPlay = (Button) findViewById(R.id.playb);
		btnPause = (Button) findViewById(R.id.pauseb);
		btnStop = (Button) findViewById(R.id.stopb);
		btnBrowse = (Button) findViewById(R.id.browseb);
		sbProgress = (SeekBar) findViewById(R.id.sb);
		sbProgress.setMax(1000);

		mConnection = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName className,
					IBinder service) {
				MediaPlayerServices.LocalBinder binder = (MediaPlayerServices.LocalBinder) service;
				if (DEBUG)
					Toast.makeText(getBaseContext(), "Connecting Service",
							Toast.LENGTH_SHORT).show();
				mService = binder.getService();
				mBound = true;
			}

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				mBound = false;
			}
		};

		addEventListeners();
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
					Toast.makeText(getBaseContext(),
							"mService is null - btnPlay", Toast.LENGTH_SHORT)
							.show();
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
				Intent i = new Intent(getBaseContext(),
						FileBrowserActivity.class);
				startActivityForResult(i, 1);
			}
		});

		sbProgress
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int i,
							boolean b) {
						if (b) {
							mService.seekTo(i);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			//TODO Handle situation where result is not OK
			if (resultCode == RESULT_OK) {
				String path = data.getStringExtra("filename");
				mService.playSong(path);
			}
		}
	}
}
