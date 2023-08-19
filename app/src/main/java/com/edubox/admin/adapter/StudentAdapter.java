package com.edubox.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edubox.admin.R;
import com.edubox.admin.std.StudentDetails;
import com.edubox.admin.std.student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private Context context;
    private student student;
    private List<student> dataList;
    private OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public void setSearchList(List<student> dataSearchList){
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }

    public StudentAdapter(Context context, List<student> dataList, OnItemClickListener listener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener){
        this.context = context;
        this.dataList = dataList;
        this.itemClickListener = listener;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new MyViewHolder(view, itemClickListener, editClickListener, deleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recTitle.setText(dataList.get(position).getStdName());
        holder.recDesc.setText(dataList.get(position).getSId());
        holder.recLang.setText(dataList.get(position).getStdId());

        student = dataList.get(position);

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentDetails.class);

                intent.putExtra("stdName", dataList.get(holder.getAdapterPosition()).getStdName());
                intent.putExtra("phone", dataList.get(holder.getAdapterPosition()).getstdPhone());
                intent.putExtra("stdId", dataList.get(holder.getAdapterPosition()).getStdId());
                intent.putExtra("sId", dataList.get(holder.getAdapterPosition()).getSId());
                intent.putExtra("student",student);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(student sub);
    }
    public interface OnEditClickListener {
        void onEditClick(student sub);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(student sub);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recImage;
        TextView recTitle, recDesc, recLang;
        CardView recCard;
        ImageView options;
        RelativeLayout foreground;
        private OnItemClickListener clickListener;
        private OnEditClickListener editClickListener;
        private OnDeleteClickListener deleteClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
            super(itemView);
            this.clickListener = itemClickListener;
            this.editClickListener = editClickListener;
            this.deleteClickListener = deleteClickListener;

            recImage = itemView.findViewById(R.id.recImage);
            recTitle = itemView.findViewById(R.id.recTitle);
            recDesc = itemView.findViewById(R.id.recDesc);
            recLang = itemView.findViewById(R.id.recLang);
            recCard = itemView.findViewById(R.id.recCard);
            options = itemView.findViewById(R.id.options);
            options.setOnClickListener(this);
            recCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.options){
                showPopUpMenu(v);
            }else if (v.getId()==R.id.recCard){
                int pos = getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION){
                    itemClickListener.onItemClick(dataList.get(pos));
                }
            }
        }

        private void showPopUpMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu_item);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId()==R.id.menu_edit){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            editClickListener.onEditClick(dataList.get(pos));
                        }
//                        Toast.makeText(view.getContext(),"Edit Button is clicked",Toast.LENGTH_SHORT).show();
                        return true;
                    }else if (menuItem.getItemId()==R.id.menu_delete){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            deleteClickListener.onDeleteClick(dataList.get(pos));
                        }
                        return true;
                    }else {
                        return false;
                    }

                }
            });
            popupMenu.show();
        }

        public void showData(student student) {

        }
    }

}

