package xyz.sleepygamers.agallery.FullScreenImage;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.sleepygamers.agallery.Edit.ImageEditActivity;
import xyz.sleepygamers.agallery.utils.Function;
import xyz.sleepygamers.agallery.R;


public class GalleryPreview extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ArrayList<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    Toolbar mToolbar;
    boolean mToolbarVisibility = true;
    LinearLayout bottomBar;
    ImageButton ib_edit, ib_crop, ib_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_preview);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        imageList = (ArrayList<HashMap<String, String>>) i.getExtras().getSerializable("list");
        adapter = new FullScreenImageAdapter(GalleryPreview.this,
                imageList);
        viewPager.setAdapter(adapter);
        setTitle();
        // displaying selected image first
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
        setBottomBar();
    }

    private void setBottomBar() {
        bottomBar = findViewById(R.id.bottomBar);
        ib_edit = findViewById(R.id.ib_edit);
        ib_crop = findViewById(R.id.ib_crop);
        ib_delete = findViewById(R.id.ib_delete);
        ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPic();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gallery_preview, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_edit:
                editPic();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editPic() {
        Intent i = new Intent(this, ImageEditActivity.class);
        i.putExtra("path", imageList.get(+viewPager.getCurrentItem()).get(Function.KEY_PATH));
        startActivity(i);

    }

    void setToolbarView() {
        if (mToolbarVisibility) {
            getSupportActionBar().hide();
            //   bottomBar.setVisibility(View.GONE);
            bottomBar.animate().translationY(bottomBar.getHeight()).setInterpolator(new DecelerateInterpolator(1));
            //    mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        } else {
            getSupportActionBar().show();
            //bottomBar.setVisibility(View.VISIBLE);
            bottomBar.animate().translationY(0).setInterpolator(new AccelerateInterpolator(1));

            //     mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        }
        mToolbarVisibility = !mToolbarVisibility;
    }

    void setTitle() {
        String path = imageList.get(+viewPager.getCurrentItem()).get(Function.KEY_PATH);
        try {
            path = path.substring(path.lastIndexOf("/") + 1);
            if (path.length() > 15) {
                path = path.substring(0, 15).concat("...");
            }
            getSupportActionBar().setTitle(path);
        } catch (Exception ex) {
            //        Toast.makeText(GalleryPreview.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}