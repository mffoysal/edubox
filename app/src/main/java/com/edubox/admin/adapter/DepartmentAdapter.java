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
import com.edubox.admin.major.MajorPanel;
import com.edubox.admin.scl.schoolDep;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepViewHolder> {

    private Context context;
    private List<schoolDep> schoolDeps;
    private OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public DepartmentAdapter(Context context, List<schoolDep> schoolDeps, OnItemClickListener onItemClickListener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.schoolDeps = schoolDeps;
        this.itemClickListener = onItemClickListener;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public DepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department,parent,false);

        return new DepViewHolder(view,itemClickListener,editClickListener,deleteClickListener);
    }

    @Override
    public int getItemCount() {
        return schoolDeps.size();
    }

    public interface OnItemClickListener {
        void onItemClick(schoolDep schoolDep);
    }

    public interface OnEditClickListener{
        void onEditClick(schoolDep schoolDep);
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(schoolDep schoolDep);
    }

    @Override
    public void onBindViewHolder(@NonNull DepViewHolder holder, int position) {
//        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.depName.setText(schoolDeps.get(position).getmName());
        holder.depStarEndId.setText(schoolDeps.get(position).getstartId()+"~"+schoolDeps.get(position).getendId());
        holder.depPhone.setText(schoolDeps.get(position).getPhone());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MajorPanel.class);
                intent.putExtra("depName", schoolDeps.get(holder.getAdapterPosition()).getmName());
                intent.putExtra("depId", schoolDeps.get(holder.getAdapterPosition()).getid());
                intent.putExtra("depSId", schoolDeps.get(holder.getAdapterPosition()).getsId());
                intent.putExtra("depUId",schoolDeps.get(holder.getAdapterPosition()).getUniqueId());
                intent.putExtra("depPhone", schoolDeps.get(holder.getAdapterPosition()).getPhone());
                intent.putExtra("depSyncKey", schoolDeps.get(holder.getAdapterPosition()).getSync_key());
                intent.putExtra("deanId", schoolDeps.get(holder.getAdapterPosition()).getDean());
                intent.putExtra("startId", schoolDeps.get(holder.getAdapterPosition()).getstartId());
                intent.putExtra("endId", schoolDeps.get(holder.getAdapterPosition()).getendId());
                intent.putExtra("currentId", schoolDeps.get(holder.getAdapterPosition()).getcurrentId());
                intent.putExtra("location", schoolDeps.get(holder.getAdapterPosition()).getLocation());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
    public void searchDepList(ArrayList<schoolDep> searchList){
        schoolDeps = searchList;
        notifyDataSetChanged();
    }

    class DepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView depName, depId, depSId, depUId, depSyncKey, depPhone, depLocation, depStarEndId;
        CardView cardView;
        ImageView options;
        RelativeLayout foreground;
        private OnItemClickListener clickListener;
        private DepartmentAdapter.OnEditClickListener editClickListener;
        private DepartmentAdapter.OnDeleteClickListener deleteClickListener;

        public DepViewHolder(@NonNull View itemView, DepartmentAdapter.OnItemClickListener itemClickListener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
            super(itemView);
            this.clickListener = itemClickListener;
            this.editClickListener = editClickListener;
            this.deleteClickListener = deleteClickListener;
            depName = itemView.findViewById(R.id.depNameI);
            depStarEndId = itemView.findViewById(R.id.startAndEndId);
            depPhone = itemView.findViewById(R.id.depPhoneI);
            cardView = itemView.findViewById(R.id.depCardId);
            options = itemView.findViewById(R.id.options);
            options.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.options){
                showPopUpMenu(v);
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
                            editClickListener.onEditClick(schoolDeps.get(pos));
                        }
//                        Toast.makeText(view.getContext(),"Edit Button is clicked",Toast.LENGTH_SHORT).show();
                        return true;
                    }else if (menuItem.getItemId()==R.id.menu_delete){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            deleteClickListener.onDeleteClick(schoolDeps.get(pos));
                        }
                        return true;
                    }else {
                        return false;
                    }

                }
            });
            popupMenu.show();
        }

        public void showData(schoolDep schoolDep) {

        }
    }
}
