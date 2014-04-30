package mob.assignment.rss.domain.model;

import java.net.URL;
import java.util.ArrayList;

public class Channel {
	private String name;
	private String description;
	private URL coverart;
	
	private ArrayList<Episode> episodeList;
	
	public Channel() {
		episodeList = new ArrayList<Episode>();
	}
	
	public Channel(String name, String description, URL coverart) {
		this();
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
	
	public void addEpisode(Episode e) {
		episodeList.add(e);
	}
	
	public Episode getEpisode(int index) throws IndexOutOfBoundsException {
		if (index > episodeList.size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		return episodeList.get(index);
	}
	
	public ArrayList<Episode> getEpisodeList() {
		return episodeList;
	}
}
