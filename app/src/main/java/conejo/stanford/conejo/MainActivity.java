package conejo.stanford.conejo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.LinkagePager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    /*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_wardrobe);
                    return true;
            }
            return false;
        }
    };*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PagerContainer container = (PagerContainer) findViewById(R.id.pager_container);
        ViewPager pager = container.getViewPager();
        pager.setAdapter(new MyPagerAdapter());
        pager.setClipChildren(false);
        //
        pager.setOffscreenPageLimit(15);

        container.setPageItemClickListener(new PageItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"position:" + position,Toast.LENGTH_SHORT).show();
            }
        });

        boolean showTransformer = getIntent().getBooleanExtra("showTransformer",false);


        if(showTransformer){

            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.3f)
                    .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                    .spaceSize(0f)
                    .build();

        }else{
            pager.setPageMargin(30);
        }
    }


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_cover,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            imageView.setImageDrawable(getResources().getDrawable(DemoData.covers[position]));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return DemoData.covers.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }

}
