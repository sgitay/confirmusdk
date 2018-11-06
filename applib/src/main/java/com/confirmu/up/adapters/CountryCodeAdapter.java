package com.confirmu.up.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.confirmu.R;
import com.confirmu.up.CountryCodeActivity;
import com.confirmu.up.model.CountryCode;

import java.util.ArrayList;
import java.util.List;


public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.ViewHolder>{

    Activity context;
    List<CountryCode> homeMs;
    RecyclerView recyclerView;
    List<CountryCode> filterArr;

    public CountryCodeAdapter(Activity context, List<CountryCode> homeMs, RecyclerView recyclerView) {
        this.context = context;
        this.homeMs = homeMs;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_country_code_spinner, null);

        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final CountryCode homeM = homeMs.get(position);

        holder.tvCountryName.setText(homeM.getCountriesIsdCode());
        holder.tvLanguage.setText(homeM.getCountriesName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((CountryCodeActivity)context).setCountryCode(homeM.getCountriesName(), homeM.getCountriesIsdCode());
                /*Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeMs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvLanguage;
        TextView tvCountryName;


        ViewHolder(View itemView) {

            super(itemView);

            tvLanguage = (TextView) itemView.findViewById(R.id.spinn_text);
            tvCountryName = (TextView)itemView.findViewById(R.id.tv_country);

        }

    }



    @SuppressWarnings("unchecked")
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<CountryCode> FilteredArrList = new ArrayList<CountryCode>();

                if (filterArr == null) {
                    filterArr = new ArrayList<CountryCode>(homeMs); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = filterArr.size();
                    results.values = filterArr;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < filterArr.size(); i++) {
                        String data = filterArr.get(i).getCountriesName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(filterArr.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                homeMs = (ArrayList<CountryCode>) filterResults.values;

//                PageFragment frag = PageFragment.getInstance();
//                frag.sortArr = (List<GalleyAllDesignModel>) filterResults.values;

                if (homeMs == null)
                    homeMs = new ArrayList<>();

                notifyDataSetChanged();
            }
        };
    }


}
