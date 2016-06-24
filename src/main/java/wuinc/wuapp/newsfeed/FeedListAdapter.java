package wuinc.wuapp.newsfeed;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import wuinc.wuapp.R;
import wuinc.wuapp.base.*;
import wuinc.wuapp.*;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<ChallengeMainClass> feedItems;

    public FeedListAdapter(Activity activity, ArrayList<ChallengeMainClass> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item_duel, null);


        TextView vTitle = (TextView) convertView.findViewById(R.id.title);
        TextView vTimestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView vCategories = (TextView) convertView.findViewById(R.id.categories);

        TextView vName_1 = (TextView) convertView.findViewById(R.id.name_duel_1);
        TextView vCatchPhrase = (TextView) convertView.findViewById(R.id.catchphrase_duel);
        TextView vName_2 = (TextView) convertView.findViewById(R.id.name_duel_2);


        VideoView vfeedVideo_1 = (VideoView) convertView.findViewById(R.id.feedVideo_duel_1);
        VideoView vfeedVideo_2 = (VideoView) convertView.findViewById(R.id.feedVideo_duel_2);

        ImageView vProfifePic_1 = (ImageView) convertView.findViewById(R.id.profilePic_duel_1);
        ImageView vProfifePic_2 = (ImageView) convertView.findViewById(R.id.profilePic_duel_2);

        Challenge_1v1 item = (Challenge_1v1) feedItems.get(position);
        vTitle.setText(item.getTitle());

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getDate()), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        vTimestamp.setText(timeAgo);

        vCategories.setText(item.getDescription());

        String name_1 = item.getPublisher().getFirstName() + " " + item.getPublisher().getLastName();
        vName_1.setText(name_1);

        String catch_phrase = "challenges";
        vCatchPhrase.setText(catch_phrase);

        String name_2 = item.getChallenger().getFirstName() + " " + item.getChallenger().getLastName();
        vName_2.setText(name_2);

        BaseVideo load_video = new BaseVideo();
        try{
            vfeedVideo_1 = load_video.getVideoByPath(item.getProposal().getPathVideoClip());

            vfeedVideo_2 = load_video.getVideoByPath(item.getAnswer().getPathVideoClip());
        } catch (InterruptedException e) {
        e.printStackTrace();

        } catch (ExecutionException e) {
        e.printStackTrace();
        }



        // user profile pic
       //profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image
        if (item.getImge() != null) {
            feedImageView.setImageUrl(item.getImge(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}
