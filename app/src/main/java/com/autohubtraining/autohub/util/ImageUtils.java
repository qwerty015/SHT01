package com.autohubtraining.autohub.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.widget.ImageView;


import com.autohubtraining.autohub.R;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static final int REQUEST_LOAD_IMAGE = 995;
    public static final int REQUEST_LOAD_MULTIPLE_IMAGE = 996;
    private Context context;
    private ImageView imageView;
    private File mImageFile;
    private Fragment fragment;
    private Bitmap bitmap;
    private String base64String;

    public ImageUtils(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public static Bitmap getCompressedScaledBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] compressedBytes = byteArrayOutputStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(compressedBytes, 0, compressedBytes.length);
        float newWidth = 512.0f;
        int newHeight = (int) (bitmap.getHeight() * (newWidth / bitmap.getWidth()));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) newWidth, newHeight, true);
        return scaledBitmap;
    }


    public static String getFilePath(Context context, Uri selectedImage) {

        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void chooseFromLibrary() {
        Intent browsePhotoIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (fragment != null) {
            fragment.startActivityForResult(browsePhotoIntent, REQUEST_LOAD_IMAGE);
        } else {
            ((Activity) context).startActivityForResult(browsePhotoIntent, REQUEST_LOAD_IMAGE);
        }
    }

    public void chooseMultipleImagesFromGallery() {
        Utill.openGallery(context, ImageUtils.REQUEST_LOAD_MULTIPLE_IMAGE);
    }

    public void resetProfile() {
        mImageFile = null;
        if (imageView != null) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_LOAD_IMAGE && null != data) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                        String base64String = Utill.convertBitmapToBase64(bitmap);
                        this.bitmap = bitmap;
                        this.base64String = base64String;


                        Uri selectedImage = data.getData();
                        mImageFile = new File(ImageUtils.getFilePath(context, selectedImage));
                        //Log.e("filePath",path);
                        //Glide.with(this).load(new File(path)).into(profilePic);


                        //imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == REQUEST_LOAD_MULTIPLE_IMAGE && null != data) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                            /*Selfie selfie = new Selfie();
                            selfie.setBitmap(bitmap);
                            selfie.setImageUrl(String.valueOf(imageUri));
                            bitmaps.add(selfie);*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public   File getImageFile() {
        return mImageFile;
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Image", null);
        return Uri.parse(path);
    }

    private Bitmap getRotatedBitmap(String filePath) {
        int rotate = 0;
        try {
            File imageFile = new File(filePath);

            Bitmap bmCameraCapture = BitmapFactory.decodeFile(Uri.fromFile(imageFile).getPath());


            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            return Bitmap.createBitmap(bmCameraCapture, 0, 0, bmCameraCapture.getWidth(), bmCameraCapture.getHeight(), matrix, true);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getBase64String() {
        return base64String;
    }

}
