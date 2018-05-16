package xyz.sleepygamers.agallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.sleepygamers.agallery.Edit.ImageEditActivity;

/**
 * Created by SHAJIB on 25/12/2015.
 */
public class GalleryPreview extends AppCompatActivity {

    ArrayList<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_preview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        imageList = (ArrayList<HashMap<String, String>>) i.getExtras().getSerializable("list");

        adapter = new FullScreenImageAdapter(GalleryPreview.this,
                imageList);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);

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


}