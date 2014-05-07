package mob.assignment.rss.domain.model;

import java.net.URL;

public class Episode extends Media {
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String DURATION = "itunes:duration";
	public static final String LOCATION = "enclosure";
	
	private String description;
	private String location;
	
	public Episode(String description, String location) {
		super();
		this.description = description;
		this.location = location;
	}

	public Episode() {
		super();
	}

	public Episode(String description, String location, String title, int duration) {
		super(title, duration);
		this.description = description;
		this.location = location;
	}

	public Episode(String description, String location, String title, int duration, int progress) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
