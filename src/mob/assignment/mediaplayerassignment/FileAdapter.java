package mob.assignment.mediaplayerassignment;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sandbeck on 3/26/14.
 */
public class FileAdapter extends ArrayAdapter<File> {
	private File[] data;
	private Context context;

	public FileAdapter(Context context, File[] objects) {
		super(context, R.layout.file_list_item, objects);
		this.data = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflator.inflate(R.layout.file_list_item, null);
		ImageView image = (ImageView) view.findViewById(R.id.iconImageView);
		TextView name = (TextView) view.findViewById(R.id.nameTextView);
		TextView description = (TextView) view
				.findViewById(R.id.descriptionTextView);

		/*
		 * image resources folder: ic_menu_archive media file:
		 * ic_media_video_poster other:
		 */
		if (data[position].isDirectory()) {
			image.setImageDrawable(view.getResources().getDrawable(
					R.drawable.ic_menu_archive));
			description.setText("directory");
		} else if (FileType.isMediaFile(data[position])) {
			image.setImageDrawable(view.getResources().getDrawable(
					R.drawable.ic_media_video_poster));
			description.setText("media file");
		} else {
			image.setImageDrawable(view.getResources().getDrawable(
					R.drawable.ic_menu_paste_holo_dark));
			description.setText("file");
		}

		if (position == 0 && data[position].getParentFile() != null) {
			name.setText("..");
		} else {
			name.setText(data[position].getName());
		}
		return view;
	}
}
