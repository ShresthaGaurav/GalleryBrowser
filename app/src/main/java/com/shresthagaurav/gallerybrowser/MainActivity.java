package com.shresthagaurav.gallerybrowser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    String selected;
    TextView tvres;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        tvres=findViewById(R.id.tvres);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose();
            }
        });
    }

    void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 0);
        }
    }

    private void Choose() {

        final CharSequence[] options_list = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Image From");
        builder.setItems(options_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int count) {
                if (options_list[count].equals("Camera")) {
                    Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                    dispatchTakePictureIntent();
                    selected = "camera";
                } else if (options_list[count].equals("Gallery")) {
                    Toast.makeText(MainActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
                    BrowseImage();
                    selected = "Gallery";

                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();
            }
        }
        tvres.setText("");
        if (selected.equals("camera")) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        } else if (selected.equals("Gallery")) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }
    }
}
