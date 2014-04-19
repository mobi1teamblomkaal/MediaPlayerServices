package mob.assignment.mediaplayerassignment;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Binder;

public class MediaPlayerServices extends Service {
	
	 private final IBinder mBinder = new LocalBinder();
	    private MediaPlayer mp;

	    public MediaPlayerServices() {
	    }

	    public class LocalBinder extends Binder {
	    	MediaPlayerServices getService() {
	           
	            return MediaPlayerServices.this;
	        }
	    }
	    
	    
	@Override
	public IBinder onBind(Intent intent) {
		 mp = mp.create(this, Uri.parse("http://gototen.dk/wp-content/uploads/2013/12/dont-mess-with-my-man.mp3"));
	        return mBinder;
	}
	 public void play() {
	        if(mp == null) {
	            return;
	        }
	        if(!mp.isPlaying()) {
	//        	 mp = mp.create(this, Uri.parse("http://gototen.dk/wp-content/uploads/2013/12/dont-mess-with-my-man.mp3"));
	            mp.start();
	            
	        }
	    }

	    public void pause() {
	    	if (mp == null) {
				return;
			}
			if (!mp.isPlaying()) {
				mp.start();

			} else {
				mp.pause();
			}
	    }

	    public void stop() {
	    	if (mp == null) {
				return;
			}
			if (mp.isPlaying()) {
				mp.pause();
			}
			mp.seekTo(0);
		
	    }
	    
	    public void reset(){
	    mp.reset();	
	    	
	    }

	    public void playSong(String songPath) {
	        mp.reset();
	        mp = MediaPlayer.create(this, Uri.parse(songPath));
	        mp.start();
	    }


	}


