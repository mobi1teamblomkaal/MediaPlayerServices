package mob.assignment.rss.domain.model;

public abstract class Media {
	public static final int NOT_PLAYING = -1;
	
	protected String title;
	protected int duration;
	protected int progress;
	
	public Media() {
		
	}
	
	public Media(String title, int duration) {
		this.title = title;
		this.duration = duration;
	}

	public Media(String title, int duration, int progress) {
		this.title = title;
		this.duration = duration;
		this.progress = progress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
