package co.devhack.appfirebaseworkshop.view.activities.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.devhack.appfirebaseworkshop.R;
import co.devhack.appfirebaseworkshop.domain.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Product> lstProduct;

    public ProductAdapter() {
        this.lstProduct = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        Product product = lstProduct.get(position);
        viewHolder.render(product);
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

    public void addAllItems(Collection<Product> collection) {
        lstProduct.clear();
        lstProduct.addAll(collection);
    }
}
