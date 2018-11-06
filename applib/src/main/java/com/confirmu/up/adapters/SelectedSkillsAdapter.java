package com.confirmu.up.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.confirmu.R;
import com.confirmu.up.ChatActivity;

import java.util.List;


public class SelectedSkillsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private ChatActivity context;
    private List<String> arrayList;

    public SelectedSkillsAdapter(ChatActivity context, List<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_skills, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvSkill.setText(arrayList.get(position));
        //itemViewHolder.tvAddress.setText(arrayList.get(position));

        itemViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.removeSkill(arrayList.get(position));
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvSkill;
        ImageView ivDelete;

        ItemViewHolder(View viewHolder) {
            super(viewHolder);
            tvSkill = viewHolder.findViewById(R.id.tv_skill);
            ivDelete = viewHolder.findViewById(R.id.iv_delete);

        }

    }

    private static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }
}