package com.example.elina.application.presenters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.elina.application.R;
import com.example.elina.application.fragments.ListFragment;
import com.example.elina.application.interfaces.AddView;
import com.example.elina.application.interfaces.DetailView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailPresenter {
    private final DetailView detailView;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    public DetailPresenter(DetailView detailView) {
        this.detailView = detailView;
    }

    public void deleteEquipOfUser(String equipID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference equipsCollectionReference = db.collection("objects");

        equipsCollectionReference.document(equipID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        detailView.makeToast(1);
                        detailView.replaceFragment();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        detailView.makeToast(2);
                    }
                });
    }
}
