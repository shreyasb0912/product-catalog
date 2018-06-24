package com.shreyasbhondve.productlist.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shreyasbhondve.productlist.R;
import com.shreyasbhondve.productlist.pojo.ProductCatalog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private RecyclerViewAdapter.ClickListener clickListener;
    List<ProductCatalog.Category.Product> productList = null;
    Context mContext;

    @Inject
    public RecyclerViewAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
        this.productList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtName.setText(productList.get(position).getName());
        String priceRange = "Price: " + mContext.getString(R.string.Rs) + " " + productList.get(position).getMinPrice() + " - " + productList.get(position).getMaxPrice();
        holder.txtPrice.setText(priceRange);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtPrice;
        private CardView constraintLayoutContainer;

        ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.priceRange);

            constraintLayoutContainer = itemView.findViewById(R.id.card_view);

            constraintLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.launchIntent();
                }
            });
        }
    }

    public interface ClickListener {
        void launchIntent();
    }

    public void setData(List<ProductCatalog.Category.Product> productList) {
        this.productList = new ArrayList<>();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }
}
