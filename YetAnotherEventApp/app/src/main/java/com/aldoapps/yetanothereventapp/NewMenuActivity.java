package com.aldoapps.yetanothereventapp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.facebook.drawee.view.SimpleDraweeView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.bitmap;

/**
 * Created by aldo on 10/29/16.
 */

public class NewMenuActivity extends BaseActivity {

    private static final int RC_PICK_PHOTO = 15;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_resto_menu)
    EditText etMenu;

    @BindView(R.id.et_resto_description)
    EditText etDescription;

    @BindView(R.id.rating)
    RatingBar rating;

    @BindView(R.id.iv_menu)
    SimpleDraweeView ivMenu;

    String restoKey = "";

    private DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("menu");

    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private DatabaseReference userMenuRef = menuRef.child(uid);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();

        Bundle args = getIntent().getExtras();
        if (args != null) {
            RestoMenu restoMenu = args.getParcelable(BundleKeys.MENU_KEY);
            setRestoMenu(restoMenu);
        }
    }

    private void setRestoMenu(RestoMenu restoMenu) {
        restoKey = restoMenu.getKey();
        etMenu.setText(restoMenu.getMenu());
        etDescription.setText(restoMenu.getDescription());
        rating.setRating(restoMenu.getRating());
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.label_new_menu));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_new_menu;
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        DatabaseReference newMenuRef;

        if (TextUtils.isEmpty(restoKey)) {
            newMenuRef = userMenuRef.push();
            restoKey = newMenuRef.getKey();
        } else {
            newMenuRef = userMenuRef.child(restoKey);
        }

        RestoMenu restoMenu = new RestoMenu(
            restoKey,
            etMenu.getText().toString(),
            etDescription.getText().toString(),
            rating.getRating(),
            "http://lorempixel.com/400/200/"
        );

        uploadImages(restoKey);

        newMenuRef.setValue(restoMenu).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(NewMenuActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @OnClick(R.id.btn_take_picture)
    void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_PICK_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Failed to load iamge", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                ivMenu.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadImages(String key) {
        Bitmap bitmap = ((BitmapDrawable) ivMenu.getDrawable()).getBitmap();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage
            .getReferenceFromUrl("gs://concertapp-81665.appspot.com/menus" +
                "/" + key + ".png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewMenuActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(NewMenuActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
