package mob.assignment.mediaplayerassignment;

import java.io.File;

public abstract class FileType {
	private static String[] mediaFileTypes = {
		".3gp",
		".mp4",
		".m4a",
		".aac",
		".flac",
		".mp3",
		".mid",
		".ota",
		".ogg",
		".mkv",
		".wav",
//		".jpg",
//		".gif",
//		".png",
//		".bmp",
	};
	
	public static boolean isMediaFile(File file) {
		String fileName = file.getName();
		try {
			String extension = fileName.substring(fileName.lastIndexOf('.'));
			for (String s : mediaFileTypes) {
				if (extension.equalsIgnoreCase(s))
					return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
