package co.devhack.appfirebaseworkshop.view.activities.products;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.devhack.appfirebaseworkshop.BuildConfig;
import co.devhack.appfirebaseworkshop.R;
import co.devhack.appfirebaseworkshop.domain.model.Product;
import co.devhack.appfirebaseworkshop.util.Util;
import co.devhack.appfirebaseworkshop.util.UtilAnalytics;
import co.devhack.appfirebaseworkshop.view.activities.base.BaseActivity;
import co.devhack.appfirebaseworkshop.view.viewmodel.ProductViewModel;
import io.reactivex.observers.DisposableObserver;

public class CreateProductActivity extends BaseActivity implements
        Validator.ValidationListener {

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtNameProduct)
    EditText txtNameProduct;

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 200, messageResId = R.string.long_max)
    @BindView(R.id.txtProductDescription)
    EditText txtProductDescription;

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 10, messageResId = R.string.long_max)
    @BindView(R.id.txtProductPrice)
    EditText txtProductPrice;

    @BindView(R.id.btnSaveProduct)
    Button btnSaveProduct;

    @BindView(R.id.imgProductImage)
    ImageButton imgProductImage;

    FirebaseRemoteConfig remoteConfig;

    private ProductViewModel productViewModel;
    private MaterialDialog materialDialog;
    private final int SELECT_PICTURE = 888;
    private Uri photoURI;
    Validator validator;
    private static final String CREATE_PRODUCTS = "create_products";

    @Override
    public void initView() {
        super.initView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        remoteConfig.setConfigSettings(configSettings);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.app_bar_create_product;
    }

    @OnClick(R.id.btnSaveProduct)
    public void clickAddProduct() {

        materialDialog = Util.showMaterialProgressDialog(this, getString(R.string.loading));

        remoteConfig.fetch(10)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        remoteConfig.activateFetched();
                    } else {
                        materialDialog.dismiss();
                        mostrarError(getString(R.string.lbl_fetch_failed));
                    }
                    validateCreateProducts();
                });
    }

    private void validateCreateProducts() {
        boolean createProducts = remoteConfig.getBoolean(CREATE_PRODUCTS);

        if (!createProducts) {
            mostrarError(getString(R.string.lbl_cant_create_products));
            materialDialog.dismiss();
            return;
        }

        validator.validate();

    }

    @OnClick(R.id.imgProductImage)
    public void clickAddImageProduct() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Escoje la Imagen"),
                SELECT_PICTURE);
    }


    public void mostrarError(String error) {
        Snackbar.make(this.findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onValidationSucceeded() {

        if (photoURI == null) {
            mostrarError(getString(R.string.lbl_image_mandatory));
            return;
        }

        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        Product product = new Product();
        product.setName(txtNameProduct.getText().toString());
        product.setDescription(txtProductDescription.getText().toString());
        product.setPrice(Double.parseDouble(txtProductPrice.getText().toString()));
        product.setDate(timeStamp);
        product.setUriImage(photoURI);

        productViewModel.setProduct(product);

        productViewModel.addProduct(new DisposableObserver<String>() {
            @Override
            public void onNext(String idNew) {
                UtilAnalytics.logEventNewUser(CreateProductActivity.this, idNew, txtNameProduct.getText().toString());

                Log.i(CreateProductActivity.class.getName(), "ID new product -> " + idNew);
                Intent intent = new Intent(CreateProductActivity.this, ProductsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                mostrarError(e.getMessage());
                materialDialog.dismiss();
            }

            @Override
            public void onComplete() {
                materialDialog.dismiss();
            }
        });

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validationFailed(errors);
        materialDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_PICTURE:
                    photoURI = data.getData();
                    Picasso.get().load(photoURI).fit().centerCrop().into(imgProductImage);
            }
        }
    }
}
