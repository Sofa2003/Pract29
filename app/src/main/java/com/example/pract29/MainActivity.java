package com.example.pract29;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    File directory;
    final int TYPE_PHOTO = 1;
    final int TYPE_VIDEO = 2;

    final int REQUEST_CODE_PHOTO = 1;
    final int REQUEST_CODE_VIDEO = 2;

    final String TAG = "myLogs";

    ImageView ivPhoto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDirectory();
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
    }


    public void onClickPhoto(View view) {
        try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClickTelefon(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: "));
        startActivity(intent);

    }

    public void onClickInternet(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com.kh"));
    startActivity(intent);
    }
    public void onClickAddress(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:")));
        }
    public void onClickGalerey(View view)
    {Intent i = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }

        if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Video uri: " + intent.getData());
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }
    }

    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
            case TYPE_VIDEO:
                file = new File(directory.getPath() + "/" + "video_"
                        + System.currentTimeMillis() + ".mp4");
                break;
        }
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }


    private void createDirectory() {
        directory = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }


}

