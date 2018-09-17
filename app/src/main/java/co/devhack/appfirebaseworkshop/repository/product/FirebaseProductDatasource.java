package co.devhack.appfirebaseworkshop.repository.product;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import co.devhack.appfirebaseworkshop.domain.model.Product;
import io.reactivex.Observable;

public class FirebaseProductDatasource {

    private final FirebaseFirestore db;

    public FirebaseProductDatasource() {
        db = FirebaseFirestore.getInstance();
    }

    public Observable<Product> getProductById(String id) {

        return Observable.create(emitter -> {

            try {

                db.collection("products").document(id).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            Product product = new Product();
                            product.setName(documentSnapshot.getString("name"));
                            product.setPrice(documentSnapshot.getDouble("price"));
                            product.setDescription(documentSnapshot.getString("description"));
                            product.setDate(documentSnapshot.getString("date"));
                            product.setId(documentSnapshot.getId());
                            emitter.onNext(product);
                            emitter.onComplete();
                        })
                        .addOnFailureListener(emitter::onError);

            } catch (Exception e) {
                emitter.onError(e);
            }

        });
    }


    public Observable<Product> getProductByNameAndDate(String name, String date) {

        // Example query with operator AND

        return Observable.create(emitter -> {

            try {

                db.collection("products")
                        .whereEqualTo("name", name)
                        .whereEqualTo("date", date)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
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
                        })
                        .addOnFailureListener(emitter::onError);

            } catch (Exception e) {
                emitter.onError(e);
            }

        });
    }

    public Observable<String> addProduct(final Product product) {

        return Observable.create(emitter -> {

            try {
                db.collection("products").add(product)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                emitter.onNext(task.getResult().getId());
                                emitter.onComplete();
                            } else {
                                emitter.onError(task.getException());
                            }
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }


        });

    }

}
