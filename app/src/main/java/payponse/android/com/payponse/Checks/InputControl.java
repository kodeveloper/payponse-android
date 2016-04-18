package payponse.android.com.payponse.Checks;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by abdullah on 17.04.2016.
 */
public  class InputControl {
    static HashMap<String,String> hashResponse = new HashMap<String,String>();
    static boolean isTrue=false;

    public static HashMap<String,String> phoneCheck(String phoneNumber){
        hashResponse.clear();
        if(phoneNumber.matches("^[0-9]{10,10}$")) {
            if(phoneNumber.substring(0,1).equals("5"))
                hashResponse.put("isOK","1");

            else {
                hashResponse.put("isOK", "0");
                hashResponse.put("error_message","Numaranın 5 ile başlaması gerekiyor.");
            }
        }else{
            hashResponse.put("isOK","0");
            hashResponse.put("error_message","Numara formata uygun degildir.");
        }
        return hashResponse;
    }
    public static boolean messagePasswordCheck(String password){

        //MESAJA GELEN PASSWORD OKEY OLUP OLMADIGI KONTROL EDİLİCEK.
        if (password.equals("12345"))
            isTrue=true;

        return isTrue;
    }
    public static HashMap<String,String> detailsCheck(String name ,String surname ,String mail,String password ){
        hashResponse.clear();
        final Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");


        if(name.isEmpty() || surname.isEmpty() || mail.isEmpty() || password.isEmpty() ){
            hashResponse.put("isOK","0");
            hashResponse.put("error_message","Boş Alan Bırakmayınız.");
        }else{
            if ( EMAIL_ADDRESS_PATTERN.matcher(mail).matches()){
                if (password.length()>=8){
                    hashResponse.put("isOK", "1");
                }else{
                    hashResponse.put("isOK","0");
                    hashResponse.put("error_message","Şifreniz 8 karakterden kısa olamaz.");
                }
            }else{
                hashResponse.put("isOK","0");
                hashResponse.put("error_message","Geçerli bir mail adresi giriniz.");
            }

        }
        return  hashResponse;
    }
}
