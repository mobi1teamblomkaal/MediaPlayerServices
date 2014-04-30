package mob.assignment.rss.domain.model;

import java.io.File;

public class Music extends Media {
	private File location;

	public Music() {
		super();
	}

	public Music(File location, String title, int duration) {
		super(title, duration);
		this.location = location;
	}

	public Music(File location, String title, int duration, int progress) {
		super(title, duration, progress);
		this.location = location;
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
	}
}
