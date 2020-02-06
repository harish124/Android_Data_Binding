package firebase_storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cashit.models.ProductDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import print.Print;

public class UploadToFirebaseStorage {

    private String imageIdentifier;
    private Print p;
    private Context ctx;
    private String imageURI;

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public UploadToFirebaseStorage(Context ctx)
    {
        this.ctx=ctx;
        p=new Print(ctx);
    }

    private void makeUUID()
    {
        imageIdentifier = UUID.randomUUID().toString()+".png";
    }


    public int uploadImageToStorage(Bitmap bitmap,String id,FirebaseUser firebaseUser,ProductDetails productDetails)
    {
        if(bitmap==null)
        {
            p.fprintf("Select an Image/Different Image to continue");
            return -1;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        makeUUID();
        UploadTask uploadTask = FirebaseStorage.getInstance()
                .getReference()
                .child("my_images")
                .child(imageIdentifier)
                .putBytes(data);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                p.fprintf("Error: "+e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //p.sprintf("Image uploaded successfully!!!!");
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageURI=uri.toString();
                        //p.sprintf("ImageUrl = "+imageURI);
                        Log.d("ImageUrl",imageURI);

                        postProduct(firebaseUser,id,productDetails);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.fprintf("Unable To get the download url of the image!!!");
                    }
                });
            }
        });
        return 1;
    }

    private void postProduct(FirebaseUser firebaseUser,String id,ProductDetails productDetails) {



        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mReference=database.getReference("Products");


        productDetails.setProdImage(imageURI);
        mReference.child(firebaseUser.getUid().toString())
                .child(id)
                .setValue(productDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                p.sprintf("Product was posted successfully");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        p.fprintf("Unable to post product");
                    }
                });
    }

}