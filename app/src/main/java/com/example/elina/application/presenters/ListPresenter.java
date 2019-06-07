package com.example.elina.application.presenters;

import android.support.annotation.NonNull;

import com.example.elina.application.interfaces.ListView;
import com.example.elina.application.model.Equipment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ListPresenter {
    private final ListView listView;

    public ListPresenter(ListView listView) {
        this.listView = listView;
    }

    public void getAllEquipsOfUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference equipsCollectionReference = db.collection("objects");

        Query equipsQuery =     equipsCollectionReference
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());

        equipsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for(QueryDocumentSnapshot document : task.getResult()){
                        Equipment equipment = document.toObject(Equipment.class);
//                        equipmentList.add(equipment);
                        listView.addEquipmentToList(equipment);
                        System.out.println(equipment.getName());
                    }

//                    adapter.equipmentList = equipmentList;
//                    adapter.notifyDataSetChanged();
                    listView.setAdapter();

                } else {
                    listView.makeToast();
                }
            }
        });
    }
}
