package com.shreyasbhondve.productlist.adapter;

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

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{
    private CategoryListAdapter.ClickListener clickListener;
    List<ProductCatalog.Category> categoryList = null;

    @Inject
    public CategoryListAdapter(ClickListener clickListener) {
        this.categoryList = new ArrayList<>();
        this.clickListener = clickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;

        private CardView constraintLayoutContainer;

        ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);

            constraintLayoutContainer = itemView.findViewById(R.id.card_view);

            constraintLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.launchIntent(categoryList.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_row, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtName.setText(categoryList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setData(List<ProductCatalog.Category> categoryList) {
        this.categoryList = new ArrayList<>();
        this.categoryList.addAll(categoryList);
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void launchIntent(String id);
    }
}
