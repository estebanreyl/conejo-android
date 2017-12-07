package conejo.stanford.conejo;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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


public class MainActivity extends AppCompatActivity   {
    private TextView mTextMessage;
    private boolean[] highlighted = new boolean[]{false,false,false};
    private MyPagerAdapter shirts;
    private MyPagerAdapter pants;
    private MyPagerAdapter shoes;
    private View mLayout;
    private final int PERMISSION_REQUEST_CAMERA = 0;
    private boolean editEnabled = false;

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
        mLayout = findViewById(R.id.main_layout);

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

    public void openCamera(View view) {
        showCameraPreview();
    }

    public void startCamera(){
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, profileActivity.class);
        startActivity(intent);
    }

    //----------------------------------------CAMERA PERMS REQS-------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(mLayout, "Camera permission was granted. Starting Add Items Screen",
                        Snackbar.LENGTH_SHORT)
                        .show();
                startCamera();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, "Camera permission request was denied.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }
    private void showCameraPreview() {
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Snackbar.make(mLayout,
                    "Camera permission is available. Starting Scanner.",
                    Snackbar.LENGTH_SHORT).show();
            startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
    }

    /**
     * Requests the {@link android.Manifest.permission#CAMERA} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(mLayout, "Camera access is required to be able to add new items to your wardrobe.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
            }).show();

        } else {
            Snackbar.make(mLayout,
                    "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    public void deleteCard(View view) {

    }

    public void editMode(View view) {
        editEnabled = true;
    }

    public void exitEditMode(View view) {
        Button edit = findViewById(R.id.)
    }

    //-------------------------------------------ADAPTER CLASS--------------------------------------
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
            /*if(editMode){
                ImageView xButton = (ImageView) view.findViewById(R.id.lock);
                xButton.setVisibility(View.VISIBLE);
            }*/
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
                    Intent intent = new Intent(getBaseContext(), SimilarItems.class);
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
