package co.devhack.appfirebaseworkshop.view.activities.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.devhack.appfirebaseworkshop.R;
import co.devhack.appfirebaseworkshop.domain.model.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtNameProduct)
    TextView txtNameProduct;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    @BindView(R.id.txtPrice)
    TextView txtPrice;

    @BindView(R.id.txtDateProduct)
    TextView txtDateProduct;

    @BindView(R.id.imgProduct)
    ImageView imgProductImage;


    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(Product product) {

        txtNameProduct.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtDateProduct.setText(product.getDate());
        txtPrice.setText(String.valueOf(product.getPrice()));

        Picasso.get()
                .load(product.getUrlImage())
                .error(R.drawable.ecomerce)
                .fit()
                .centerCrop()
                .into(imgProductImage);
    }

}
