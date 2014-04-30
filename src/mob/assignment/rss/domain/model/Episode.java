package mob.assignment.rss.domain.model;

import java.net.URL;

public class Episode extends Media {
	private String description;
	private URL location;
	
	public Episode(String description, URL location) {
		super();
		this.description = description;
		this.location = location;
	}

	public Episode() {
		super();
	}

	public Episode(String description, URL location, String title, int duration) {
		super(title, duration);
		this.description = description;
		this.location = location;
	}

	public Episode(String description, URL location, String title, int duration, int progress) {
		super(title, duration, progress);
		this.description = description;
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URL getLocation() {
		return location;
	}

	public void setLocation(URL location) {
		this.location = location;
	}
}
