package app.android.payponseapp.pages;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;

import app.android.payponseapp.R;
import app.android.payponseapp.checks.InputControl;
import app.android.payponseapp.fragments.DetailsPage;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPage extends Activity {
    //EditText Identified.
    DetailsPage dp= new DetailsPage();
    @Bind(R.id.txtPhone)EditText phoneText;
    @Bind(R.id.backgorundView)ImageView backgroundView;

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(this).load(R.drawable.background_login).
                fit().
                noFade().
                into(backgroundView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);

    }
    //Checking the contents of the textbox.
    @OnClick(R.id.btnCheck) void checkPhone(){

        HashMap<String,String> result = InputControl.phoneCheck(phoneText.getText().toString());
        if(result.get("isOK").equals("1")){
            showDialog();
        }else{
            Toast.makeText(getApplicationContext(),result.get("error_message"),Toast.LENGTH_LONG).show();
        }
    }
    private void showDialog(){
         // telefona gelen password için dialog çıkartlıyor.
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Lütfen mesaj olarak gönderilen şifre'yi giriniz.");
        final EditText input = new EditText(this);
        b.setView(input);
        b.setPositiveButton("TAMAM", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                // SHOULD NOW WORK
                boolean result =  InputControl.messagePasswordCheck(input.getText().toString());
                if(result){
                   showDetailsPage();
                }else
                    Toast.makeText(getApplicationContext(),"olmadı be kardeş",Toast.LENGTH_LONG).show();

            }
        });
        b.setNegativeButton("IPTAL", null);
        b.create().show();
    }
    private void showDetailsPage(){

        Bundle bundle = new Bundle();
        bundle.putString("phone_number",phoneText.getText().toString());
        FragmentManager manager = getFragmentManager();
        dp.setArguments(bundle);
        findViewById(R.id.phoneContainer).setVisibility(View.GONE);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.detailsContainer,dp,"detailsFragment");
        transaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}
