package app.android.payponseapp.checks;

import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Abdullah on 10.4.2016.
 */

// dataları json olarak yollıyıcam
    //Success ise success 1  0 ise error massagei ekrana bas
    
public  class InputControl {
   static boolean isOk=false;
    public static boolean phoneCheck(String phoneNumber){

        if(phoneNumber.matches("^[0-9]{10,10}$")) {
            if(phoneNumber.substring(0,1).equals("5"))
                isOk=true;
        }
      return   isOk;
    }
    public static boolean messagePasswordCheck(String password){

        //MESAJA GELEN PASSWORD OKEY OLUP OLMADIGI KONTROL EDİLİCEK.
        if (password.equals("12345"))
            isOk=true;

        return isOk;
    }
    public boolean detailsCheck(String name ,String surname ,String mail,String password ,String repass){

        final Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");


        if(name.isEmpty() || surname.isEmpty() || mail.isEmpty() || password.isEmpty() || repass.isEmpty())
        isOk=false;
        else{
            if (password.equals(repass) && EMAIL_ADDRESS_PATTERN.matcher(mail).matches() && password.length()>=8){
                //dataları burdan göndericem
                isOk=true;
            }

        }
        return isOk;
    }
}
