package co.devhack.appfirebaseworkshop.repository.product;

import android.arch.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import co.devhack.appfirebaseworkshop.domain.model.Product;

public class ProductsLiveData extends LiveData<List<Product>>
        implements EventListener<QuerySnapshot> {

    private ListenerRegistration listenerRegistration;
    private final CollectionReference collectionReference;

    public ProductsLiveData(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }

    @Override
    protected void onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                        @Nullable FirebaseFirestoreException e) {

        List<DocumentSnapshot> lst =
                queryDocumentSnapshots.getDocuments();

        List<Product> lstProduct = new ArrayList<>();

        for (DocumentSnapshot ds :
                lst) {
            Product product = ds.toObject(Product.class);
            if (product != null) {
                product.setId(ds.getId());
                lstProduct.add(product);
            }
        }

        setValue(lstProduct);
    }
}
