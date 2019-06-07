package com.example.elina.application.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.elina.application.activities.LoginActivity;
import com.example.elina.application.interfaces.AddView;
import com.example.elina.application.model.Equipment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddPresenter {
    private final AddView mAddView;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    public AddPresenter(AddView mAddView) {
        this.mAddView = mAddView;
    }

    public void init(){
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    mAddView.openLoginActivity();
                }
            }
        };
    }

    public void createEquip (final String type, String name, String number, String prodectionYear, String location, String nameResponsible, String imageUrl){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference newEquipRef = db.collection("objects");
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Equipment equipment = new Equipment();
        equipment.setType(type);
        equipment.setName(name);
        equipment.setNumber(number);
        equipment.setProdectionYear(prodectionYear);
        equipment.setLocation(location);
        equipment.setNameResponsible(nameResponsible);
        equipment.setEquip_id(UUID.randomUUID().toString());
        equipment.setUser_id(userID);
        equipment.setImage_url(imageUrl);

        newEquipRef.document(equipment.getEquip_id()).set(equipment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mAddView.makeToast(1);
                    mAddView.clearFields();
                } else{
                    mAddView.makeToast(2);
                }
            }
        });
    }
}
