package app.android.payponseapp.pages;

import android.app.Activity;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import app.android.payponseapp.R;

public class CameraPages extends Activity implements QRCodeReaderView.OnQRCodeReadListener {
     QRCodeReaderView mydecoderview;

    @Override
    protected void onStart() {
        super.onStart();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_pages);
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Toast.makeText(getApplicationContext(),text+" points: "+points,Toast.LENGTH_LONG).show();
        mydecoderview.getCameraManager().stopPreview();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {
        Toast.makeText(getApplicationContext(),"QR code Yok",Toast.LENGTH_LONG).show();
    }
}
