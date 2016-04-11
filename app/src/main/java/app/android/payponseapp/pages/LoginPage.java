package app.android.payponseapp.pages;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import app.android.payponseapp.R;
import app.android.payponseapp.checks.InputControl;
import app.android.payponseapp.fragments.DetailsPage;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPage extends Activity {
    //EditText Identified.
    @Bind(R.id.txtPhone)EditText phoneText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);

    }
    //Checking the contents of the textbox.
    @OnClick(R.id.btnCheck) void checkPhone(){
       boolean result= InputControl.phoneCheck(phoneText.getText().toString());
        if(result){
            showDialog();
        }else{
            Toast.makeText(getApplicationContext(),"Telefon Numaranız Hatalıdır.",Toast.LENGTH_LONG).show();
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

        DetailsPage dp= new DetailsPage();
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
