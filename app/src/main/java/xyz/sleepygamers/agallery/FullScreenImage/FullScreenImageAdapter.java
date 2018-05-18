package xyz.sleepygamers.agallery.FullScreenImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import xyz.sleepygamers.agallery.FullScreenImage.GalleryPreview;
import xyz.sleepygamers.agallery.R;
import xyz.sleepygamers.agallery.utils.Function;
import xyz.sleepygamers.agallery.utils.TouchImageView;

public class FullScreenImageAdapter extends PagerAdapter {

    ArrayList<HashMap<String, String>> _imagePaths;
    private Activity _activity;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<HashMap<String, String>> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        Glide.with(_activity)
                .load(new File(_imagePaths.get(+position).get(Function.KEY_PATH))) // Uri of the picture
                .into(imgDisplay);

        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //       Toast.makeText(_activity, "Clicked", Toast.LENGTH_SHORT).show();
                ((GalleryPreview) _activity).setToolbarView();
            }
        });


        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}