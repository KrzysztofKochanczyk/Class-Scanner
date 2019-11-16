package com.example.barcodescanner;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class JnumScanner extends AppCompatActivity {

    Toolbar jNumScan_tb;
    EditText resultET;
    ImageView previewIV;
    Uri image_uri;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_number_scanner);

        //initToolBar();

        jNumScan_tb = (Toolbar)findViewById(R.id.jTB);
        resultET = (EditText)findViewById(R.id.resultTv);
        previewIV = (ImageView)findViewById(R.id.jImageV);

        cameraPermission = new String[] {Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        setSupportActionBar(jNumScan_tb);
        getSupportActionBar().setTitle("Class");
    }


    //action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    //handles action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addImage)
        {
            showImageImportDialog();
        }
        else if (id == R.id.settings)
        {
            Toast.makeText(this, "Nothing but nothing", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        //items that will be displaying in the dialog
        String[] items = {" Camera", " Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //setting title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                {
                    //this means camera is tapped
                    if (!cameraPermission())
                    {
                        //this means the camera permissions is not being allowed
                        //we are requesting it here
                        requestingCamera();
                    }
                    else
                    {
                        //else the permission is allowed
                        cameraPicked();
                    }
                }
                else if (which == 1)
                {
                    //gallery option is tapped
                    if (!storagePermission())
                    {
                        //this means the storage permissions is not being allowed
                        //we are requesting it here
                        requestingStorage();
                    }
                    else
                    {
                        //else the permission is allowed
                        galleryPicked();
                    }
                }
            }
        });
        dialog.create().show(); //shows dialog
    }

    private void galleryPicked() {
        //this will grab your image from gallery
        Intent gal = new Intent(Intent.ACTION_PICK);
        //setting intent to type image
        gal.setType("image/*");
        startActivityForResult(gal, IMAGE_PICK_GALLERY_CODE);
    }

    private void cameraPicked() {
        //this will take the camera picture and store it into the images
        ContentValues values = new ContentValues();
        //creates title of picture
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        //description
        values.put(MediaStore.Images.Media.DESCRIPTION, "Reading this image");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(camIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestingStorage() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean storagePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestingCamera() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean cameraPermission() {
        boolean result0 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result0 && result1;
    }

    //handling permissions here
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccept && storageAccept)
                    {
                        cameraPicked();
                    }
                    else
                    {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    boolean storageAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccept)
                    {
                        galleryPicked();
                    }
                    else
                    {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    //handling image result\
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == IMAGE_PICK_GALLERY_CODE)
            {
                //cropping image now
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE)
            {
                //same thing on top to this one
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
        }

        //getting cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                //getting image uri
                Uri resultURI = result.getUri();
                //setting image to the imageview in xml
                previewIV.setImageURI(resultURI);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable)previewIV.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational())
                {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();

                    //this will get the text until it doesnt have any more
                    for (int i = 0; i < items.size(); i++)
                    {
                        TextBlock myitem = items.valueAt(i);
                        sb.append(myitem.getValue());
                        sb.append("\n");
                    }
                    //setting text to show up
                    resultET.setText(sb.toString());
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
