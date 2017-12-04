package conejo.stanford.conejo;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Collections;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private boolean[] highlighted = new boolean[]{false,false,false};
    private MyPagerAdapter shirts;
    private MyPagerAdapter pants;
    private MyPagerAdapter shoes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PagerContainer containerShirts = (PagerContainer) findViewById(R.id.shirt_container);
        PagerContainer containerPants = (PagerContainer) findViewById(R.id.pants_container);
        PagerContainer containerShoes = (PagerContainer) findViewById(R.id.shoes_container);
        initCarousels(containerShirts,DemoData.shirts,0);
        initCarousels(containerPants,DemoData.pants,1);
        initCarousels(containerShoes,DemoData.shoes,2);
    }

    //Carousel init
    private void initCarousels(PagerContainer container, int[] list, int num){
        ViewPager pager = container.getViewPager();
        //Currently used as the method to get the adapters returns a bizzarre incomplete adapter
        if(num == 0){
            shirts = new MyPagerAdapter(list, num);
            pager.setAdapter(shirts);
        }else if(num == 1){
            pants = new MyPagerAdapter(list, num);
            pager.setAdapter(pants);
        }else{
            shoes = new MyPagerAdapter(list, num);
            pager.setAdapter(shoes);
        }

        pager.setClipChildren(false);
        pager.setOffscreenPageLimit(15);
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

    public void updateCarousels(){
        shirts.randomSelect();
        pants.randomSelect();
        shoes.randomSelect();
    }

    //Carousel Adapter
    //Should reimplement using arrayAdapter to improve speed
    public class MyPagerAdapter extends PagerAdapter {
        private int[] listBackup;
        private int[] list;
        private int arrayNum;

        MyPagerAdapter(int[] passed, int num){
            list = passed;
            listBackup = passed.clone();
            arrayNum = num;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_cover,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            imageView.setImageDrawable(getResources().getDrawable(list[position]));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            view.setTag(position);
            if(highlighted[arrayNum]){
                ImageView lock = (ImageView) view.findViewById(R.id.lock);
                lock.setVisibility(View.VISIBLE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=(Integer)v.getTag();

                    if(highlighted[arrayNum]){
                        resetElems();
                    }else{
                        fixElem(position);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = (Integer) v.getTag();
                    Intent intent = new Intent(getBaseContext(), similar_items.class);
                    String type = "";
                    if(arrayNum == 0) type = "Shirts";
                    else if(arrayNum==1) type = "Pants";
                    else type ="Shoes";
                    intent.putExtra("type",type);
                    intent.putExtra("id",listBackup[position]);
                    startActivity(intent);
                    return false;
                }
            });

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

        private void fixElem(int index){
            list = new int[1];
            list[0] = listBackup[index];
            highlighted[arrayNum] = true;
            this.notifyDataSetChanged();
            MainActivity.this.updateCarousels();
        }

        private void resetElems(){
            list = listBackup.clone();
            highlighted[arrayNum] = false;
            this.notifyDataSetChanged();
        }

        public void randomSelect(){
            if(highlighted[arrayNum])return;
            list = new int[(int)(Math.random()*listBackup.length + 1)];
            int[] temp = listBackup.clone();
            Collections.shuffle(Arrays.asList(temp));

            for(int i = 0; i < list.length; i++){
                list[i] = temp[i];
            }

            highlighted[arrayNum] = false;
            this.notifyDataSetChanged();
        }

        //Not the most desirable fix, essentially reloads the carousel, inneficient for large ones
        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }
    }








}
