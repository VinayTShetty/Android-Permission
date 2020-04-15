package com.permissionTutorials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private Button  camera;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               int permsissionAcknoledgement= checkPermissionIsGivenorNot(
                        Manifest.permission.CAMERA);

               if(permsissionAcknoledgement==0){
                   showToast("Permission Already Granted");
               }else{
                   if(permsissionAcknoledgement==-1){
                       showToast("Permission Denied Button click");
                       requestPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
                   }
               }
            }
        });
    }

    private int checkPermissionIsGivenorNot(String permission){
        int result=-1;
        int permissionNumber=ContextCompat.checkSelfPermission(MainActivity.this,permission);
        /**
         * if the permission is given means it will give the  permissionNumber = 0
         * if the permission is not  given means it will give the  permissionNumber =-1
         * It s same as we are checking for PackageManager.PERMISSION_DENIED =-1 & PackageManager.GRANTED=0
         */
        if(permissionNumber==0){
            result=0;
        }else{
            result=-1;
        }
        return result;
    }

    private boolean checkuserhasDeniedthePermission(String permission){
        boolean result=false;
            boolean permissionDeniedFromUser=  ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        /**
         * shouldShowRequestPermissionRationale Method Summary.
         *  permissionDeniedFromUser =true is the user has  denied the permission.i.e is frist time when asked if user denies it will be true.
         *  permissionDeniedFromUser=false if the user has denied the permission and selected don t ask again..
         *
         *  Note:- shouldShowRequestPermissionRationale gives false for the frist time dialog.
         *  when we request the permisssion.Before user selects Allow or Deny.
         */
        if(permissionDeniedFromUser){
            System.out.println("Permission Denied from the user Frist Time =  "+permissionDeniedFromUser);
        }else{
            System.out.println("Permission Denied from the user and made as dont  ask again = "+permissionDeniedFromUser);
        }
        result=permissionDeniedFromUser;
        return  result;
    }


    private void requestPermission(String permission,int requestCode){
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] { permission },
                requestCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /**
         * requestCode =int: The request code passed in ActivityCompat.requestPermissions(android.app.Activity, String[], int)
         */
        if(requestCode==CAMERA_PERMISSION_CODE){
            if(permissions.length>0){
              if(permissions[0].toString().equalsIgnoreCase(Manifest.permission.CAMERA.toString())){ // comparing 2 Strings.
                 if(grantResults.length>0){
                     if(grantResults[0]==PackageManager.PERMISSION_GRANTED){ // comapring 2 int values.
                         showToast("Permission Granted");
                     }else{
                         if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                          boolean deniedStautus=  checkuserhasDeniedthePermission(permissions[0]);
                             showToast("Permission Denied onRequestPermissionsResult");
                             if(deniedStautus){
                                 showToast("Permission Denied once");
                             }else{
                                 showToast("Permission Denied Ever");
                             }
                         }
                     }
                 }
              }
            }
        }
    }


    private void showToast(String message){
        Toast.makeText(this,""+message,Toast.LENGTH_SHORT).show();
    }
}
