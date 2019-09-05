package com.apk.sqlitedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder> {

    Context context;
    OnUserClickListener listener;

    List<PersonBean> listPersonInfo;
    public RecyclerViewAdapter(Context context,List<PersonBean>
                               listPersonInfo,OnUserClickListener listener){
        this.context=context;
        this.listener=listener;
        this.listPersonInfo=listPersonInfo;
    }

    public interface OnUserClickListener{
        void onUserClick(PersonBean currentPerson,String action);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_item,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.UserViewHolder holder,final int position) {
        final PersonBean currentPerson = listPersonInfo.get(position);
        holder.ctxtName.setText(currentPerson.getName());
        holder.ctxtAge.setText(currentPerson.getAge()+"");
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(currentPerson,"Edit");
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(currentPerson,"Delete");
            }
        });

    }

    @Override
    public int getItemCount() {
       return listPersonInfo.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView ctxtAge,ctxtName;
        ImageView imgDelete,imgEdit;

        public UserViewHolder (@NonNull View itemView){
            super(itemView);
            ctxtAge=itemView.findViewById(R.id.ctxtAge);
            ctxtName=itemView.findViewById(R.id.ctxtName);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
}
