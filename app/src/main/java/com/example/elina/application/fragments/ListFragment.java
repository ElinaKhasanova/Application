package com.example.elina.application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elina.application.R;
import com.example.elina.application.activities.LoginActivity;
import com.example.elina.application.interfaces.ListView;
import com.example.elina.application.model.Equipment;
import com.example.elina.application.presenters.ListPresenter;
import com.example.elina.application.utils.EquipmentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment
                        implements  ListView{
    private ListPresenter mListPresenter;

    private TextView type_inv, name_inv, number_inv;
    private ImageView imageEquip;
    private RecyclerView recyclerView;

    private List<Equipment> equipmentList;
    private EquipmentAdapter adapter;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DocumentSnapshot documentSnapshot;

    public static final String USER_ID = "userId";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        type_inv = (EditText) view.findViewById(R.id.type_tv);
        name_inv = (EditText) view.findViewById(R.id.name_tv);
        number_inv = (EditText) view.findViewById(R.id.number_tv);
        imageEquip = view.findViewById(R.id.image_iv_list);
        recyclerView = view.findViewById(R.id.recyclerView_frag);
        equipmentList = new ArrayList<>();

        initRecyclerView();
//        getAllEquipsOfUser();
        mListPresenter.getAllEquipsOfUser();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListPresenter = new ListPresenter(this);

        auth = FirebaseAuth.getInstance();
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

    @Override
    public void addEquipmentToList(Equipment equipment) {
        equipmentList.add(equipment);
    }

    @Override
    public void setAdapter() {
        adapter.equipmentList = equipmentList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void makeToast() {
        Toast.makeText(getActivity(), "Query failed. Try again", Toast.LENGTH_LONG);
    }

    private void getAllEquipsOfUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference equipsCollectionReference = db.collection("objects");

        Query equipsQuery = equipsCollectionReference
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());

        equipsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for(QueryDocumentSnapshot document : task.getResult()){
                        Equipment equipment = document.toObject(Equipment.class);
                        equipmentList.add(equipment);
                        System.out.println(equipment.getName());
                    }

                    adapter.equipmentList = equipmentList;
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "Query failed. Try again", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private EquipmentAdapter.OnUserClickListener setRecyclerClickListener(){
        EquipmentAdapter.OnUserClickListener onUserClickListener = new EquipmentAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(Equipment equipment) {

                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", equipment.getType());
                bundle.putString("name", equipment.getName());
                bundle.putString("number", equipment.getNumber());
                bundle.putString("year", equipment.getProdectionYear());
                bundle.putString("location", equipment.getLocation());
                bundle.putString("nameResp", equipment.getNameResponsible());
                bundle.putString("equip_id", equipment.getEquip_id());
                bundle.putString("user_id", equipment.getUser_id());
                bundle.putString("date", equipment.getTimeStamp().toString());
                bundle.putString("image_url", equipment.getImage_url());
                detailFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,  detailFragment).commit();
            }
        };

        return onUserClickListener;
    }



    private void initRecyclerView(){
//        EquipmentAdapter.OnUserClickListener onUserClickListener = new EquipmentAdapter.OnUserClickListener() {
//            @Override
//            public void onUserClick(Equipment equipment) {
//
//                DetailFragment detailFragment = new DetailFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("type", equipment.getType());
//                bundle.putString("name", equipment.getName());
//                bundle.putString("number", equipment.getNumber());
//                bundle.putString("year", equipment.getProdectionYear());
//                bundle.putString("location", equipment.getLocation());
//                bundle.putString("nameResp", equipment.getNameResponsible());
//                bundle.putString("equip_id", equipment.getEquip_id());
//                bundle.putString("user_id", equipment.getUser_id());
//                bundle.putString("date", equipment.getTimeStamp().toString());
//                bundle.putString("image_url", equipment.getImage_url());
//                detailFragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content,  detailFragment).commit();
//            }
//        };

        if (adapter == null){
            adapter = new EquipmentAdapter(getActivity(), equipmentList, setRecyclerClickListener());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }
}
