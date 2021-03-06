package com.example.elina.application.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elina.application.R;
import com.example.elina.application.model.Equipment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import rx.Observer;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {

    private LayoutInflater inflater;
    public List<Equipment> equipmentList;
    private OnUserClickListener onUserClickListener;

    private PublishSubject<View> mViewClickSubject = PublishSubject.create();

    public Observable<View> getViewClickedObservable() {
        return mViewClickSubject;
    }

    public EquipmentAdapter() {
    }

    public EquipmentAdapter(Context context, List<Equipment> equipmentList, OnUserClickListener onUserClickListener) {
        this.equipmentList = equipmentList;
        this.inflater = LayoutInflater.from(context);
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

//        RxView.clicks(view)
//                .takeUntil(RxView.attaches(parent))
//                .map(aVoid -> view)
//                .
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentAdapter.ViewHolder holder, int position) {
        Equipment equipment = equipmentList.get(position);
        holder.type_tv.setText(equipment.getType());
        holder.name_tv.setText(equipment.getName());
        holder.number_tv.setText(equipment.getNumber());
        holder.image_iv.setImageURI(Uri.parse(equipment.getImage_url()));
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public interface OnUserClickListener {
        void onUserClick(Equipment equipment);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView image_iv;
        final TextView type_tv, name_tv, number_tv;

        ViewHolder(final View view) {
            super(view);
            image_iv = view.findViewById(R.id.image_iv_list);
            type_tv = view.findViewById(R.id.type_tv);
            name_tv = view.findViewById(R.id.name_tv);
            number_tv = view.findViewById(R.id.number_tv);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Equipment equipment = equipmentList.get(getLayoutPosition());
                    onUserClickListener.onUserClick(equipment);
                }
            });
        }
    }
}
