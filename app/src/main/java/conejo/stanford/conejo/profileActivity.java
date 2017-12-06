package conejo.stanford.conejo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void openHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openEditPayment(View view) {
        Intent intent = new Intent(this, PaymentInfo.class);
        startActivity(intent);
    }

    public void openOrders(View view) {
        notImplemented();
    }

    public void openShipping(View view) {
        notImplemented();
    }

    public void resetPassword(View view) {
        notImplemented();
    }

    public void logoutShipping(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void notImplemented(){
        Toast.makeText(getApplicationContext(), "Not Implemented", Toast.LENGTH_SHORT).show();
    }
}
