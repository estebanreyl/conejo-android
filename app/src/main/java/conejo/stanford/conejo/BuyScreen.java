package conejo.stanford.conejo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyScreen extends AppCompatActivity {
    String price;
    String description;
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
        price = intent.getStringExtra("price");
        String type = intent.getStringExtra("type");
        TextView description =  (TextView) findViewById(R.id.description);
        ImageView mainImg = (ImageView) findViewById(R.id.item_image);
        mainImg.setImageResource(id);
        this.description = generateDescription(type,price);
        description.setText(this.description);

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

    public void buyDialog(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BuyScreen.this);
        dialog.setCancelable(false);
        dialog.setTitle("Confirm your order");
        String message = "Are you sure you want to order ";
        if(description.contains("shirt")) message += " a ";
        else message+= "a pair of ";
        message+= (description.charAt(0)+"").toLowerCase() + description.substring(1, description.indexOf("collection") + "collection".length());
        message+= " for " + price + " ?";
        dialog.setMessage(message);

        dialog.setPositiveButton("Confirm Purchase", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(BuyScreen.this);
                dialog2.setCancelable(true);
                dialog2.setTitle("Order #1201432");
                String message = "Your order has been confirmed, go to your orders tab under profile for more information";
                dialog2.setMessage(message);
                final AlertDialog alert2 = dialog2.create();
                alert2.show();
            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}
