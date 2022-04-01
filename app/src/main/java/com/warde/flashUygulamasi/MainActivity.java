package com.warde.flashUygulamasi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MainActivity extends AppCompatActivity {

    Switch anahtar;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= findViewById(R.id.imageView);
        anahtar = findViewById(R.id.switch1);



        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Kamera izni gerekli", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();

    }

    private void runFlashlight() {
        anahtar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId= cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,true);
                        img.setVisibility(View.VISIBLE);

                    }catch (CameraAccessException e){

                    }

                }else{
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId= cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,false);
                        img.setVisibility(View.GONE);
                    }catch (CameraAccessException e){

                    }
                }
            }
        });
    }
}