package tn.triforce.balti.Adapter;

/**
 * Created by noure on 27/05/2017.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tn.triforce.balti.DAO.YTVideoAPI.Video;
import tn.triforce.balti.R;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    private static LinearLayout.LayoutParams normalLayoutParams;
    private static LinearLayout.LayoutParams lastLayoutParams;
    Context context;
    List<Video> videos;

    public VideoListAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//*
        normalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lastLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
//*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_list, parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder videoViewHolder, int position) {
        //*
        if (position == videos.size() - 1) {
            videoViewHolder.itemView.setLayoutParams(lastLayoutParams);
        } else {
            videoViewHolder.itemView.setLayoutParams(normalLayoutParams);
        }
//*/
        if (position == videos.size() - 1) {
            videoViewHolder.itemView.setLayoutParams(lastLayoutParams);
        } else {
            videoViewHolder.itemView.setLayoutParams(normalLayoutParams);
        }

        VideoListAdapter.VideoViewHolder mHolder = (VideoListAdapter.VideoViewHolder) videoViewHolder;

        videoViewHolder.title.setText(videos.get(position).getTitle());

        Picasso.with(context).load(videos.get(position).getImage()).into(mHolder.image);

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView channel;
        ImageView image;

        public VideoViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_video);
            image = (ImageView) itemView.findViewById(R.id.image_video);
        }
    }
}

