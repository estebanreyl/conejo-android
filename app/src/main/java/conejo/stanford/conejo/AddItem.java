package conejo.stanford.conejo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


public class AddItem extends AppCompatActivity {
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mCamera.setDisplayOrientation(90);
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        AlertDialog.Builder helpDialog = new AlertDialog.Builder(AddItem.this);
        helpDialog.setCancelable(true);
        helpDialog.setTitle("Help");
        String message = "Take a picture of the item you want to add, we will take care of the rest";
        helpDialog.setMessage(message);
        final AlertDialog alert = helpDialog.create();
        alert.show();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    public void itemAdded(View view) {
        AlertDialog.Builder helpDialog = new AlertDialog.Builder(AddItem.this);
        helpDialog.setCancelable(true);
        helpDialog.setTitle("Picture Taken");
        String message = "Your item has been registered, it will soon appear on your wardrobe once our Artificial Intelligence system has finished analyzing it. Check for notifications in your wardrobe screen";
        helpDialog.setMessage(message);
        final AlertDialog alert = helpDialog.create();
        alert.show();
    }
}
