package com.example.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MyCustomAdapter extends ArrayAdapter<Country> {
    private Context context;
    private List<Country> countryList;
    private List<Country> countryListFilter;

    public MyCustomAdapter(@NonNull Context context, List<Country> countryList) {
        super(context, R.layout.list_custom_item, countryList);
        this.context = context;
        this.countryList = countryList;
        this.countryListFilter = countryList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, null, true);
        TextView countryName = view.findViewById(R.id.countryName);
        ImageView imageView = view.findViewById(R.id.imageFlag);

        countryName.setText(countryListFilter.get(position).getCountry());
        Glide.with(context).load(countryListFilter.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countryListFilter.size();
    }

    @Nullable
    @Override
    public Country getItem(int position) {
        return countryListFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null || constraint.length()==0){
                    filterResults.count=countryList.size();
                    filterResults.values=countryList;
                }
                else {
                    List<Country> resultModel=new ArrayList<>();
                    String searchStr=constraint.toString().toLowerCase();

                    for (Country itemsModel:countryList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultModel.add(itemsModel);
                        }
                        filterResults.count=resultModel.size();
                        filterResults.values=resultModel;
                    }
                }
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                   countryListFilter=(List<Country>)results.values;
                   Main2Activity.countryList=(List<Country>)results.values;
                   notifyDataSetChanged();
            }
        };
        return filter;
    }
}
