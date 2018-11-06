package com.confirmu.up.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.confirmu.R;

import java.util.List;


public class IndustriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private Context context;
    private List<String> arrayList;
    View.OnClickListener onClickListener;
    ChatAdapter.Living living;

    public IndustriesAdapter(Context context, List<String> arrayList, View.OnClickListener onClickListener, ChatAdapter.Living living) {
        this.context = context;
        this.arrayList = arrayList;
        this.onClickListener = onClickListener;
        this.living = living;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_loans_home, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tvName.setText(arrayList.get(position));
        //itemViewHolder.tvAddress.setText(arrayList.get(position));

        //itemViewHolder.itemView.setOnClickListener(onClickListener);
        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                living.tvIndustry.setText(arrayList.get(position));
                living.hideSpinner();
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

        TextView tvName;

        ItemViewHolder(View viewHolder) {
            super(viewHolder);
            tvName = viewHolder.findViewById(R.id.tv_name);

        }

    }

    private static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }
}