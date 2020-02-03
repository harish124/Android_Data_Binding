package com.example.cashit;


import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.cashit.models.ProductDetails;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firebase_storage.UploadToFirebaseStorage;
import print.Print;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    @BindView(R.id.postImageView)
    ImageView postImageView;
    @BindView(R.id.textInputLayout1)
    EditText pname;
    @BindView(R.id.textInputLayout2)
    EditText desc;
    @BindView(R.id.textInputLayout3)
    EditText cost;
    @BindView(R.id.textInputLayout4)
    EditText phno;
    //@BindView(R.id.linearLayout)
    //LinearLayout linearLayout;
    @BindView(R.id.prevBtn)
    Button prevBtn;
    @BindView(R.id.postBtn)
    Button postBtn;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.constraintLayout)
    RelativeLayout constraintLayout;
    @BindView(R.id.tl1)
    TextInputLayout tl1;
    @BindView(R.id.tl2)
    TextInputLayout tl2;
    @BindView(R.id.tl3)
    TextInputLayout tl3;
    @BindView(R.id.tl4)
    TextInputLayout tl4;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;


    private Print p;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private Bitmap receivedImageBitmap;
    private View postView;

    public PostFragment() {
        // Required empty public constructor
    }

    void hideBottomAppBar() {
        ActionBar ab=getActivity().getActionBar();
        if(ab!=null)
        {
            ab.hide();
        }
    }

    void changeVisibilityOfTextFields(int bool) {
        linearLayout.setVisibility(bool);
        pname.setVisibility(bool);
        desc.setVisibility(bool);
        cost.setVisibility(bool);
        phno.setVisibility(bool);
        tl1.setVisibility(bool);
        tl2.setVisibility(bool);
        tl3.setVisibility(bool);
        tl4.setVisibility(bool);
    }

    void init(View postView) {

        p = new Print(postView.getContext());
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        changeVisibilityOfTextFields(View.INVISIBLE);
        hideBottomAppBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        hideBottomAppBar();
        postView = inflater.inflate(R.layout.fragment_post, container, false);

        ButterKnife.bind(this, postView);
        init(postView);

        return postView;
    }

    void getChoosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/png");
        startActivityForResult(intent, 2000);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    postImageView.setImageBitmap(receivedImageBitmap);
                    postImageView.setBackground(null);
                    postBtn.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.i("onActivityResult Error", e.getMessage());
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getChoosenImage();
                }
            }
        }
    }

    public void rootLayoutTapped() {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) postView.getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(postView.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();    //Nothing to worry about this. It will be triggered when empty space is tapped when keyboard is not visible.
        }

    }

    @OnClick({R.id.postImageView, R.id.prevBtn, R.id.postBtn, R.id.constraintLayout})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.postImageView:
                if (Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission
                                (v.getContext(),
                                        Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]
                            {
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 1000);
                } else {
                    getChoosenImage();
                }
                break;
            case R.id.prevBtn:
                postImageView.setVisibility(View.VISIBLE);
                //linearLayout.setVisibility(View.INVISIBLE);
                changeVisibilityOfTextFields(View.INVISIBLE);
                prevBtn.setVisibility(View.GONE);
                postBtn.setText("Next");
                break;

            case R.id.postBtn:
                if (postBtn.getText().toString().trim().equals("Next")) {
                    //linearLayout.setVisibility(View.VISIBLE);
                    changeVisibilityOfTextFields(View.VISIBLE);
                    postImageView.setVisibility(View.INVISIBLE);
                    prevBtn.setVisibility(View.VISIBLE);
                    postBtn.setText("Post");

                } else {
                    String prodName = pname.getText().toString().trim();
                    if (prodName.isEmpty()) {
                        p.fprintf("Product name can't be empty");
                        break;
                    }
                    String prodDesc = desc.getText().toString().trim();
                    if (prodDesc.isEmpty()) {
                        p.fprintf("Product Description can't be empty");
                        break;
                    }
                    String prodCost = cost.getText().toString().trim();
                    if (prodCost.isEmpty()) {
                        p.fprintf("Product Selling Price can't be empty");
                        break;
                    }
                    String prodPhno = phno.getText().toString().trim();
                    if (prodPhno.isEmpty()) {
                        p.fprintf("PhoneNumber can't be empty");
                        break;
                    }
                    if (prodPhno.length() != 10) {
                        p.fprintf("Invalid Phone number");
                        break;
                    }
                    String id = UUID.randomUUID().toString();
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    ProductDetails productDetails = new ProductDetails();//new ProductDetails(prodName,null,currentDate,firebaseUser.getUid().toString(),prodDesc,prodCost);

                    productDetails.setPname(prodName);
                    productDetails.setCost(prodCost);
                    productDetails.setCompany(firebaseUser.getUid().toString());
                    productDetails.setDatePosted(currentDate);
                    productDetails.setDescription(prodDesc);

                    postProductToFirebase(productDetails, firebaseUser, id);

                }
                break;
            case R.id.constraintLayout:
                rootLayoutTapped();
                break;
        }
    }

    private void postProductToFirebase(ProductDetails productDetails, FirebaseUser firebaseUser, String id) {
        UploadToFirebaseStorage obj = new UploadToFirebaseStorage(postView.getContext());
        obj.uploadImageToStorage(receivedImageBitmap, id, firebaseUser, productDetails);
    }
}
