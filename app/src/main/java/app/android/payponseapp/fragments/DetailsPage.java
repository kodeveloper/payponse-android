package app.android.payponseapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.android.payponseapp.MainActivity;
import app.android.payponseapp.R;
import app.android.payponseapp.checks.InputControl;
import app.android.payponseapp.pages.LoginPage;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsPage extends Fragment {
    private String phoneNumber=null;
    @Bind(R.id.txtName)EditText name;
    @Bind(R.id.txtSurname)EditText surname;
    @Bind(R.id.txtMail)EditText mail;
    @Bind(R.id.txtPass)EditText password;
    @Bind(R.id.passAgain)EditText repass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.details_page, container, false);
        ButterKnife.bind(this,v);
         phoneNumber= getArguments().getString("phone_number");
        return v;

    }

    @OnClick(R.id.btnComplete) void completly(){
    boolean result = new InputControl().detailsCheck(name.getText().toString(),
            surname.getText().toString(),
            mail.getText().toString(),
            password.getText().toString(),
            repass.getText().toString());
        if(result) {
            System.out.println("OK");
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();

        }
        else
            System.out.println("olmadı");
    }
}
