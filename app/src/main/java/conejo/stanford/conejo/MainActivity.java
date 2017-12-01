package conejo.stanford.conejo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
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

import java.io.Console;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.LinkageCoverTransformer;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PagerContainer containerShirts = (PagerContainer) findViewById(R.id.shirt_container);
        PagerContainer containerPants = (PagerContainer) findViewById(R.id.pants_container);
        PagerContainer containerShoes = (PagerContainer) findViewById(R.id.shoes_container);
        PagerContainer containerAccesories = (PagerContainer) findViewById(R.id.other_container);
        initCarousels(containerShirts,DemoData.shirts);
        initCarousels(containerPants,DemoData.pants);
        initCarousels(containerShoes,DemoData.shoes);
        initCarousels(containerAccesories,DemoData.accessories);

    }

    private void initCarousels(PagerContainer container, int[] list){
        //-----Shirt-----
        //PagerContainer containerShirts = (PagerContainer) findViewById(R.id.shirt_container);
        ViewPager pager = container.getViewPager();
        pager.setAdapter(new MyPagerAdapter(list));
        pager.setClipChildren(false);
        pager.setOffscreenPageLimit(15);
        container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("printed thingy:" + view.getId());
                return false;
            }
        });

        boolean showTransformer = getIntent().getBooleanExtra("showTransformer",true);
        if(showTransformer){

            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.15f)
                    .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                    .spaceSize(0f)
                    .build();

        }else{
            pager.setPageMargin(30);
        }


    }


    private class MyPagerAdapter extends PagerAdapter {

        public int[] list;

        public MyPagerAdapter(int[] passed){
            list = passed;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_cover,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            imageView.setImageDrawable(getResources().getDrawable(list[position]));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }

}
