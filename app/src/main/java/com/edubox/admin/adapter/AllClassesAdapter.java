package com.edubox.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edubox.admin.R;
import com.edubox.admin.cls.Class;
import com.edubox.admin.cls.ClassPanel;

import java.util.List;

public class AllClassesAdapter extends RecyclerView.Adapter<AllClassesAdapter.MyViewHolder4> {

    private Context context;
    private List<Class> dataList;
    private AdapterView.OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public void setSearchList(List<Class> dataSearchList){
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }

    public AllClassesAdapter(Context context, List<Class> dataList, AdapterView.OnItemClickListener listener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener){
        this.context = context;
        this.dataList = dataList;
        this.itemClickListener = listener;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_two, parent, false);
        return new MyViewHolder4(view,itemClickListener,editClickListener,deleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder4 holder, int position) {

        holder.recTitle.setText(dataList.get(position).getclsName());
        holder.recDesc.setText(dataList.get(position).getclsCode());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClassPanel.class);
                intent.putExtra("clsName", dataList.get(holder.getAdapterPosition()).getclsName());
                intent.putExtra("clsCode", dataList.get(holder.getAdapterPosition()).getclsCode());
                intent.putExtra("uniqueId", dataList.get(holder.getAdapterPosition()).getUniqueId());
                intent.putExtra("sId", dataList.get(holder.getAdapterPosition()).getSId());

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
        void onItemClick(int position);
    }

    public interface OnEditClickListener{
        void onEditClick(Class cls);
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(Class cls);
    }

    class MyViewHolder4 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recImage;
        TextView recTitle, recDesc, recLang;
        CardView recCard;
        ImageView options;
        RelativeLayout foreground;
        private AdapterView.OnItemClickListener clickListener;
        private AllClassesAdapter.OnEditClickListener editClickListener;
        private AllClassesAdapter.OnDeleteClickListener deleteClickListener;

        public MyViewHolder4(@NonNull View itemView, AdapterView.OnItemClickListener clickListener, AllClassesAdapter.OnEditClickListener editClickListener, AllClassesAdapter.OnDeleteClickListener deleteClickListener) {
            super(itemView);
            this.clickListener = clickListener;
            this.editClickListener = editClickListener;
            this.deleteClickListener = deleteClickListener;

            recImage = itemView.findViewById(R.id.recImage);
            recTitle = itemView.findViewById(R.id.recTitle);
            recDesc = itemView.findViewById(R.id.recDesc);
            recCard = itemView.findViewById(R.id.recCard);
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

        public void showData(Class cls) {

        }

    }

}

