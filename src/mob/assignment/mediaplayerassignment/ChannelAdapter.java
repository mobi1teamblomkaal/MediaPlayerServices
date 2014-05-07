package mob.assignment.mediaplayerassignment;

import java.util.ArrayList;

import mob.assignment.rss.domain.model.Channel;
import mob.assignment.rss.domain.model.Episode;
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
public class ChannelAdapter extends ArrayAdapter<Episode> {
	private Channel chData;
	private Context context;

	public ChannelAdapter(Context context, Channel objects) {
		super(context, R.layout.file_list_item, objects.getEpisodeList());
		this.chData = objects;
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

		//TODO set image
		Episode episode = chData.getEpisode(position);
		name.setText(episode.getTitle());
		description.setText(episode.getDescription());
		
		
		
		/*
		 * image resources folder: ic_menu_archive media file:
		 * ic_media_video_poster other:
		 */
		
		return view;
	}
}
