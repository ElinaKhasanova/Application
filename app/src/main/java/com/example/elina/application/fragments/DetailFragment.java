package com.example.elina.application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elina.application.R;
import com.example.elina.application.activities.LoginActivity;
import com.example.elina.application.model.Equipment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailFragment extends Fragment{

    private TextView type, name, number, prodectionYear, location, nameResponsible, date;
    private ImageView imageView;
    private Button editBtn, deleteBtn;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail, null);
        type = view.findViewById(R.id.type_det_tv);
        name = view.findViewById(R.id.name_det_tv);
        number = view.findViewById(R.id.number_det_tv);
        prodectionYear = view.findViewById(R.id.year_det_tv);
        location = view.findViewById(R.id.location_det_tv);
        nameResponsible = view.findViewById(R.id.nameR_det_tv);
        date = view.findViewById(R.id.date_det_tv);
        imageView = view.findViewById(R.id.photo_det_iv);
        editBtn = view.findViewById(R.id.edit_btn);
        deleteBtn = view.findViewById(R.id.delete_btn);

        Bundle bundle = getArguments();
        type.setText(bundle.getString("type"));
        name.setText(bundle.getString("name"));
        number.setText(bundle.getString("number"));
        prodectionYear.setText(bundle.getString("year"));
        location.setText(bundle.getString("location"));
        nameResponsible.setText(bundle.getString("nameResp"));
        final String getEQUIPID = bundle.getString("equip_id");

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEquipOfUser(getEQUIPID);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        };

    }

    private void deleteEquipOfUser(String equipID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference equipsCollectionReference = db.collection("objects");

        equipsCollectionReference.document(equipID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "DocumentSnapshot successfully deleted!", Toast.LENGTH_SHORT);
                        ListFragment listFragment = new ListFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content,  listFragment).commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error deleting document", Toast.LENGTH_SHORT);
                    }
                });
    }

    private void updateEquipOfUser(String equipID){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference equipsCollectionReference = db.collection("objects");

        equipsCollectionReference.document(equipID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "DocumentSnapshot successfully deleted!", Toast.LENGTH_SHORT);
                        ListFragment listFragment = new ListFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content,  listFragment).commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error deleting document", Toast.LENGTH_SHORT);
                    }
                });
    }
}
