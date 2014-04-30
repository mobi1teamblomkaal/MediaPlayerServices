package mob.assignment.rss.domain.model;

import java.net.URL;

public class Channel {
	private String name;
	private String description;
	private URL coverart;
	
	public Channel() {
		
	}
	
	public Channel(String name, String description, URL coverart) {
		this.name = name;
		this.description = description;
		this.coverart = coverart;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public URL getCoverart() {
		return coverart;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCoverart(URL coverart) {
		this.coverart = coverart;
	}
}
