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
import org.json.JSONObject;


public class JobsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    private Context context;
    private JSONArray arrayList;

    public JobsAdapter(Context context, JSONArray arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_jobs, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        try {
            JSONObject jsonObject = arrayList.getJSONObject(position);
            String jobTitle = jsonObject.getString("job_title");
            String companyName = jsonObject.getString("company_name");
            String startDate = jsonObject.getString("start_date");
            String endDate = jsonObject.getString("end_date");

            itemViewHolder.tvCompanyName.setText(companyName);
            itemViewHolder.tvJobTitle.setText(jobTitle);
            itemViewHolder.tvStartDate.setText(startDate + " - " + (endDate.equals("") ? "..." : endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }


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

        TextView tvCompanyName, tvJobTitle, tvStartDate;

        ItemViewHolder(View viewHolder) {
            super(viewHolder);
            tvCompanyName = (TextView)viewHolder.findViewById(R.id.tv_company_name);
            tvJobTitle = (TextView)viewHolder.findViewById(R.id.tv_job_title);
            tvStartDate = (TextView)viewHolder.findViewById(R.id.tv_duration);

        }

    }

    private static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }
}