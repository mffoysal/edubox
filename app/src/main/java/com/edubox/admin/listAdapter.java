package com.edubox.admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class listAdapter extends ArrayAdapter<User> {

    private Activity context;
    private List<User> users;
    private TextView t1,t2,t3,t4,t5;

    public listAdapter(Activity context, List<User> objects) {
        super(context, R.layout.list_layout, objects);
        this.context = context;
        this.users = objects;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.list_layout,null,true);

        User user = users.get(position);

        t1 = view.findViewById(R.id.nameTId);
        t2 = view.findViewById(R.id.phoneTId);
        t3 = view.findViewById(R.id.emailTId);
        t4 = view.findViewById(R.id.passwordSclTId);
        t5 = view.findViewById(R.id.stdIdTId);

        t1.setText(user.getStdName());
        t2.setText("Phone : "+user.getPhone());
        t3.setText("Email : "+user.getEmail());
        t4.setText("Password : "+user.getPass()+"\nSchool ID: "+user.getSId());
        t5.setText("ID : "+user.getStdId());

        return view;
    }

    public void updateData(List<User> users){
        users.clear();
        users.addAll(users);
        notifyDataSetChanged();
    }
}
