package com.example.elina.application.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.elina.application.R;
import com.example.elina.application.activities.LoginActivity;
import com.example.elina.application.interfaces.AddView;
import com.example.elina.application.presenters.AddPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AddFragment extends Fragment
                        implements View.OnClickListener, AddView{

    private EditText type, name, number, prodectionYear, location, nameResponsible;
    private Button createBtn,  addImageBtn;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private AddPresenter mAddPresenter;

    private ImageView imageViewPhoto;

    private File tempPhoto;

    private String imageUri = "";

    private String mRereference = "";

    private static final int REQUEST_CODE_PERMISSION_RECEIVE_CAMERA = 102;
    private static final int REQUEST_CODE_TAKE_PHOTO = 103;

    private StorageReference mStorageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, null);
        type = view.findViewById(R.id.type_et);
        name = view.findViewById(R.id.name_et);
        number = view.findViewById(R.id.number_et);
        prodectionYear = view.findViewById(R.id.prodectionYear_et);
        location = view.findViewById(R.id.location_et);
        nameResponsible = view.findViewById(R.id.nameResponsible_et);
        imageViewPhoto = view.findViewById(R.id.photo_iv);
        createBtn = view.findViewById(R.id.create_btn);
        addImageBtn = view.findViewById(R.id.add_image_btn);

        addImageBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);

        File localFile = null;
        mStorageRef = FirebaseStorage.getInstance().getReference();

        try {
            localFile = createTempImageFile(getActivity().getBaseContext().getExternalCacheDir());
            final File finalLocalFile = localFile;

            mStorageRef.child("images/" + mRereference).getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Picasso.with(getContext())
                                    .load(Uri.fromFile(finalLocalFile))
                                    .into(imageViewPhoto);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("Load","" + e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mAddPresenter = new AddPresenter(this);
        mAddPresenter.init();

    }

    @Override
    public void openLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void clearFields(){
        type.getText().clear();
        name.getText().clear();
        number.getText().clear();
        prodectionYear.getText().clear();
        location.getText().clear();
        nameResponsible.getText().clear();
        imageViewPhoto.setImageURI(null);
    }

    @Override
    public void makeToast(int i) {
        switch (i){
            case 1:
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG);
                break;
            case 2:
                Toast.makeText(getActivity(), "Error.Please, try again", Toast.LENGTH_LONG);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_image_btn:
                addPhoto();
                break;
            case R.id.create_btn:
                String typeOfE = type.getText().toString();
                String nameOfE = name.getText().toString();
                String numberOfE = number.getText().toString();
                String prodectionYearOfE = prodectionYear.getText().toString();
                String locationOfE = location.getText().toString();
                String nameResponsibleOfE = nameResponsible.getText().toString();
                String imageUrlFb = imageUri;
                if (checkFields(typeOfE, nameOfE, numberOfE, prodectionYearOfE, locationOfE, nameResponsibleOfE, imageUrlFb)) {
                    mAddPresenter.createEquip(typeOfE, nameOfE, numberOfE, prodectionYearOfE, locationOfE, nameResponsibleOfE, imageUrlFb);
                }
                break;
        }
    }

    public boolean checkFields(final String type, String name, String number, String prodectionYear, String location, String nameResponsible, String imageUrl){
        if (TextUtils.isEmpty(type) && TextUtils.isEmpty(name) && TextUtils.isEmpty(number) &&
                TextUtils.isEmpty(prodectionYear) && TextUtils.isEmpty(location) && TextUtils.isEmpty(nameResponsible) &&
                TextUtils.isEmpty(imageUrl)){
            Toast.makeText(getActivity(), "Fill in all the fields and load photo.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(type) && TextUtils.isEmpty(name) && TextUtils.isEmpty(number) &&
                TextUtils.isEmpty(prodectionYear) && TextUtils.isEmpty(location) && TextUtils.isEmpty(nameResponsible)){
            Toast.makeText(getActivity(), "Fill in all the fields.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(type)){
            Toast.makeText(getActivity(), "Type is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Type is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(number)){
            Toast.makeText(getActivity(), "Type is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(prodectionYear)){
            Toast.makeText(getActivity(), "Type is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(location)){
            Toast.makeText(getActivity(), "Type is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(nameResponsible)){
            Toast.makeText(getActivity(), "Type is required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(imageUrl)){
            Toast.makeText(getActivity(), "Please, add the photo.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }





//    private void createNewEquipment(final String type, String name, String number, String prodectionYear, String location, String nameResponsible, String imageUrl){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        CollectionReference newEquipRef = db.collection("objects");
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        Equipment equipment = new Equipment();
//        equipment.setType(type);
//        equipment.setName(name);
//        equipment.setNumber(number);
//        equipment.setProdectionYear(prodectionYear);
//        equipment.setLocation(location);
//        equipment.setNameResponsible(nameResponsible);
//        equipment.setEquip_id(UUID.randomUUID().toString());
//        equipment.setUser_id(userID);
//        equipment.setImage_url(imageUrl);
//
//        newEquipRef.document(equipment.getEquip_id()).set(equipment).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG);
//                    clearFields();
//                } else{
//                    Toast.makeText(getActivity(), "Error.Please, try again", Toast.LENGTH_LONG);
//                }
//            }
//        });
//    }

    private void addPhoto() {

        boolean isCameraPermissionGranted = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean isWritePermissionGranted = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if(!isCameraPermissionGranted || !isWritePermissionGranted) {

            String[] permissions;

            if (!isCameraPermissionGranted && !isWritePermissionGranted) {
                permissions = new String[] {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            } else if (!isCameraPermissionGranted) {
                permissions = new String[] {android.Manifest.permission.CAMERA};
            } else {
                permissions = new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            }
            ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_PERMISSION_RECEIVE_CAMERA);
        } else {
            try {
                tempPhoto = createTempImageFile(getActivity().getExternalCacheDir());
                imageUri = tempPhoto.getAbsolutePath();

                List<Intent> intentList = new ArrayList<>();
                Intent chooserIntent = null;

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                takePhotoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPhoto));

                intentList = addIntentsToList(getActivity().getApplicationContext(), intentList, pickIntent);
                intentList = addIntentsToList(getActivity().getApplicationContext(), intentList, takePhotoIntent);

                if (!intentList.isEmpty()) {
                    chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),"Choose your image source");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
                }

                startActivityForResult(chooserIntent, REQUEST_CODE_TAKE_PHOTO);
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int columnIndex = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    public static File createTempImageFile(File storageDir) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());//получаем время
        String imageFileName = "photo_" + timeStamp;

        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    public static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case REQUEST_CODE_TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        imageUri = getRealPathFromURI(data.getData());

                        Picasso.with(getContext())
                                .load(data.getData())
                                .into(imageViewPhoto);
                        uploadFileInFireBaseStorage(data.getData());
                    } else if (imageUri != null) {
                        imageUri = Uri.fromFile(tempPhoto).toString();

                        Picasso.with(getActivity())
                                .load(imageUri)
                                .into(imageViewPhoto);
                        uploadFileInFireBaseStorage(Uri.fromFile((tempPhoto)));
                    }
                }
                break;
        }
    }

    public void uploadFileInFireBaseStorage (Uri uri){
        UploadTask uploadTask = mStorageRef.child("images/" + mRereference).putFile(uri);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred());
                Log.i("Load","Upload is " + progress + "% done");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri donwoldUri = taskSnapshot.getMetadata().getDownloadUrl();
                Log.i("Load" , "Uri donwlod" + donwoldUri);
            }
        });
    }
}