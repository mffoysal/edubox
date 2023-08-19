package com.edubox.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edubox.admin.R;
import com.edubox.admin.subject.SubjectPanel;
import com.edubox.admin.subject.allSub;

import java.util.List;

public class AllSubjectsAdapter extends RecyclerView.Adapter<AllSubjectsAdapter.MyViewHolder5> {

    private Context context;
    private List<allSub> dataList;
    private AdapterView.OnItemClickListener itemClickListener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public void setSearchList(List<allSub> dataSearchList){
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }

    public AllSubjectsAdapter(Context context, List<allSub> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub, parent, false);
        return new MyViewHolder5(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder5 holder, int position) {

        holder.recTitle.setText(dataList.get(position).getsubName());
//        holder.recDesc.setText(dataList.get(position).getsubCode());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubjectPanel.class);
                intent.putExtra("subName", dataList.get(holder.getAdapterPosition()).getsubName());
                intent.putExtra("subCode", dataList.get(holder.getAdapterPosition()).getsubCode());
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
    public interface OnEditClickListener {
        void onEditClick(allSub sub);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(allSub sub);
    }

    public void removeItem(int index){
        dataList.remove(index);
        notifyItemRemoved(index);
        notifyDataSetChanged();
    }
    public void undoItem(allSub sub, int index){
        dataList.add(index, sub);
        notifyItemInserted(index);
    }

    public class MyViewHolder5 extends RecyclerView.ViewHolder{

        ImageView recImage;
        TextView recTitle, recDesc, recLang;
        CardView recCard;
        ImageView options;
        RelativeLayout foreground;
        private AdapterView.OnItemClickListener clickListener;
        private AllSubjectsAdapter.OnEditClickListener editClickListener;
        private AllSubjectsAdapter.OnDeleteClickListener deleteClickListener;
        public MyViewHolder5(@NonNull View itemView) {
            super(itemView);

            recImage = itemView.findViewById(R.id.subImg);
            recTitle = itemView.findViewById(R.id.sub_name);
//        recDesc = itemView.findViewById(R.id.recDesc);
            recCard = itemView.findViewById(R.id.subCardId);
            foreground = itemView.findViewById(R.id.foregroundId);

        }
    }

}

