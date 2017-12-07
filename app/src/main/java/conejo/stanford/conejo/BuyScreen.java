package conejo.stanford.conejo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_screen);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.8));
        //Get data from previous activity to lad

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",R.mipmap.pants_1);
        String price = intent.getStringExtra("price");
        String type = intent.getStringExtra("type");
        TextView description =  (TextView) findViewById(R.id.description);
        ImageView mainImg = (ImageView) findViewById(R.id.item_image);
        mainImg.setImageResource(id);
        description.setText(generateDescription(type,price));

        //loadUI(type, id);
    }


    private String generateDescription(String type, String price){
        String description = type;
        if(type.equals("Shoes")){
            description += " from " +
                    DemoData.randItem(DemoData.shoeBrands) + "'s " +
                    DemoData.randItem(DemoData.holidaySeason) + " " +
                    DemoData.randYear() + " collection"+
                    "\n\n"+
                    "Conejo's Recommended size: " + DemoData.randItem(DemoData.shoeSizes)+
                    "\n"+price;
        }else{
            description += " from " +
                    DemoData.randItem(DemoData.shirtPantBrands) + "'s " +
                    DemoData.randItem(DemoData.holidaySeason) + " " +
                    DemoData.randYear() + " collection"+
                    "\n\n"+
                    "Conejo's Recommended size: " + DemoData.randItem(DemoData.shirtPantSizes)+
                    "\n"+price;
        }

        return description;
    }

    public void close(View view) {
        finish();
    }
}
