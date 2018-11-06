package com.confirmu.up.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.confirmu.R;
import com.confirmu.up.HomeActivity;
import com.confirmu.up.model.LoanType;

import java.util.List;


public class LoansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private Context context;
    private List<LoanType> arrayList;

    public LoansAdapter(Context context, List<LoanType> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        itemViewHolder.tvName.setText(arrayList.get(position).getLoanName());
        //itemViewHolder.tvAddress.setText(arrayList.get(position));

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)context).selectLoan(arrayList.get(position).getLoanName(), arrayList.get(position).getImage(),
                        arrayList.get(position).getKey());
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