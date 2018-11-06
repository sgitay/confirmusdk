package com.confirmu.up.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.confirmu.R;

import org.json.JSONArray;
import org.json.JSONException;


public class MCQsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private Context context;
    private JSONArray arrayList;
    ChatAdapter chatAdapter;

    public MCQsAdapter(Context context, JSONArray arrayList, ChatAdapter chatAdapter) {
        this.context = context;
        this.arrayList = arrayList;
        this.chatAdapter = chatAdapter;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_mcq, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        try {
            String option = arrayList.getString(position);
            itemViewHolder.tvOption.setText(option);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    chatAdapter.setAnswer(arrayList.getString(position));
                    itemViewHolder.itemView.setOnClickListener(null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.length();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvOption;

        ItemViewHolder(View viewHolder) {
            super(viewHolder);
            tvOption = (TextView)viewHolder.findViewById(R.id.tv_option);

        }

    }

    private static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }
}