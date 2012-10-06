package com.kelsos.mbrc.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kelsos.mbrc.R;

import java.util.ArrayList;

public class PlaylistArrayAdapter extends ArrayAdapter<MusicTrack> {
    private Context _context;
    private int _layoutResourceId;
    private ArrayList<MusicTrack> _nowPlayingList;
	private int playingTrackIndex;

    public PlaylistArrayAdapter(Context context, int resource, ArrayList<MusicTrack> objects) {
        super(context, resource, objects);
        this._layoutResourceId = resource;
        this._context = context;
        this._nowPlayingList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TrackHolder holder;

        if (row == null) {
            LayoutInflater layoutInflater = ((Activity) _context).getLayoutInflater();
            row = layoutInflater.inflate(_layoutResourceId, parent, false);

            holder = new TrackHolder();
            holder.title = (TextView) row.findViewById(R.id.trackTitle);
            holder.artist = (TextView) row.findViewById(R.id.trackArtist);
			holder.trackPlaying = (ImageView) row.findViewById(R.id.listview_item_image);

            row.setTag(holder);
        } else {
            holder = (TrackHolder) row.getTag();
        }

        MusicTrack track = _nowPlayingList.get(position);
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getArtist());
		if(position==playingTrackIndex)
		{
			holder.trackPlaying.setImageResource(R.drawable.ic_media_now_playing);
		}
		else
		{
			holder.trackPlaying.setImageResource(android.R.color.transparent);
		}

        return row;
    }

	public void setPlayingTrackIndex(int index)
	{
		this.playingTrackIndex = index;
	}

    static class TrackHolder {
        TextView title;
        TextView artist;
		ImageView trackPlaying;

    }

}