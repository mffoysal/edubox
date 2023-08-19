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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edubox.admin.R;
import com.edubox.admin.session.Session;
import com.edubox.admin.session.SessionPanel;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.DepViewHolder> {

    private Context context;
    private List<Session> sessions;

    private OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public SessionAdapter(Context context, List<Session> sessions, OnItemClickListener listener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.sessions = sessions;
        this.itemClickListener = listener;
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
        return sessions.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Session session);
    }
    public interface OnEditClickListener {
        void onEditClick(Session session);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Session session);
    }

    @Override
    public void onBindViewHolder(@NonNull DepViewHolder holder, int position) {
//        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.depName.setText(sessions.get(position).getaYname());
//        holder.depStarEndId.setText(sessions.get(position).getstartId()+"~"+sessions.get(position).getendId());
//        holder.depPhone.setText(sessions.get(position).getPhone());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SessionPanel.class);
                intent.putExtra("aYname", sessions.get(holder.getAdapterPosition()).getaYname());
                intent.putExtra("id", sessions.get(holder.getAdapterPosition()).getId());
                intent.putExtra("SId", sessions.get(holder.getAdapterPosition()).getSId());
                intent.putExtra("UId",sessions.get(holder.getAdapterPosition()).getUniqueId());
                intent.putExtra("depSyncKey", sessions.get(holder.getAdapterPosition()).getSync_key());
                intent.putExtra("uniqueId", sessions.get(holder.getAdapterPosition()).getUniqueId());
                intent.putExtra("sMonth", sessions.get(holder.getAdapterPosition()).getsMonth());
                intent.putExtra("eMonth", sessions.get(holder.getAdapterPosition()).geteMonth());
                intent.putExtra("sYear", sessions.get(holder.getAdapterPosition()).getsYear());
                intent.putExtra("eYear", sessions.get(holder.getAdapterPosition()).geteYear());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        holder.options.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopUpMenu(holder,view);
//            }
//        });


    }
    private void showPopUpMenu(DepViewHolder holder ,View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popup_session_menu_item);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.menu_edit){
                    Toast.makeText(view.getContext(),"Edit Button is clicked "+sessions.get(holder.getAdapterPosition()).getId(),Toast.LENGTH_SHORT).show();
                    return true;
                }else if (menuItem.getItemId()==R.id.menu_delete){

                    return true;
                }else {
                    return false;
                }

            }
        });
        popupMenu.show();
    }
    public void searchSessionList(ArrayList<Session> searchList){
        sessions = searchList;
        notifyDataSetChanged();
    }

    class DepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView depName, depId, depSId, depUId, depSyncKey, depPhone, depLocation, depStarEndId;
        CardView cardView;
        ImageView options;
        RelativeLayout foreground;
        private OnItemClickListener itemClickListener;
        private SessionAdapter.OnEditClickListener editClickListener;
        private SessionAdapter.OnDeleteClickListener deleteClickListener;

        public DepViewHolder(@NonNull View itemView, OnItemClickListener listener, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
            super(itemView);
            this.itemClickListener = listener;
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
        public void onClick(View view) {
            if (view.getId()==R.id.options){
                showPopUpMenu(view);
            }
        }

        private void showPopUpMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_session_menu_item);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId()==R.id.menu_edit){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            editClickListener.onEditClick(sessions.get(pos));
                        }
//                        Toast.makeText(view.getContext(),"Edit Button is clicked",Toast.LENGTH_SHORT).show();
                        return true;
                    }else if (menuItem.getItemId()==R.id.menu_delete){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            deleteClickListener.onDeleteClick(sessions.get(pos));
                        }
                        return true;
                    }else {
                        return false;
                    }

                }
            });
            popupMenu.show();
        }

        public void showData(Session session) {

        }
    }
}
