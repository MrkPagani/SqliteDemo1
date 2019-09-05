package com.apk.sqlitedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class FragmentMain extends Fragment implements View.OnClickListener, RecyclerViewAdapter.OnUserClickListener{

    RecyclerView recyclerview;
    EditText edtName,edtAge;
    Button btn_submit;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<PersonBean> listPersonInfo;

    public FragmentMain(){

    }

    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        return Inflater.inflate(R.layout.activity_fragment, container, false);
    }
    
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState){
        
        super.onViewCreated(view,savedInstanceState);
        context=getActivity();
        recyclerview = view.findViewById(R.id.recycleView);
        layoutManager= new LinearLayoutManager(context);
        recyclerview.setLayoutManager(layoutManager);
        
        edtName = view.findViewById(R.id.editname);
        edtAge = view.findViewById(R.id.editage);
        btn_submit = view.findViewById(R.id.btnSubmit);
        btn_submit.setOnClickListener(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(context);
        listPersonInfo = db.selectUserData();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context,listPersonInfo,this);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v){
        if (v.getId()==R.id.btnSubmit){
            DatabaseHelper db = new DatabaseHelper(context);
            PersonBean currentPerson = new PersonBean();
            String btnStatus = btn_submit.getText().toString();
            if (btnStatus.equals("Submit")){
                currentPerson.setName(edtName.getText().toString());
                currentPerson.setAge(Integer.parseInt(edtAge.getText().toString()));
                db.insert(currentPerson);
            }
            if (btnStatus.equals("Update")){
                currentPerson.setName(edtName.getText().toString());
                currentPerson.setAge(Integer.parseInt(edtAge.getText().toString()));
                db.update(currentPerson);
            }
            setupRecyclerView();
            edtName.setText("");
            edtAge.setText("");
            edtName.setFocusable(true);
            btn_submit.setText("Submit");
        }

    }

    @Override
    public void onUserClick(PersonBean currentPerson,String action){
        if (action.equals("Edit")){
            edtName.setText(currentPerson.getName());
            edtName.setFocusable(false);
            edtAge.setText(currentPerson.getAge()+"");
            btn_submit.setText("Update");
        }
        if (action.equals("Delete")){
            DatabaseHelper db = new DatabaseHelper(context);
            db.delete(currentPerson.getName());
            setupRecyclerView();
        }
    }

}
