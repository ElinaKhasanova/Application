package com.example.elina.application.interfaces;

import com.example.elina.application.model.Equipment;

public interface ListView {
    void addEquipmentToList(Equipment equipment);
    void setAdapter();
    void makeToast();

//    void getAllEquipsOfUser();
//    void initRecyclerView();
}
