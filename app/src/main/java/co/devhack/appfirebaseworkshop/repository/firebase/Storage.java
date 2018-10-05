package co.devhack.appfirebaseworkshop.repository.firebase;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.reactivex.Observable;

public class Storage {

    private static StorageReference mStorageRef;

    public static Observable<String> addImage(String path, Uri uri, String nameFile) {

        return Observable.create(emitter -> {
            try {

                FirebaseStorage.getInstance().setMaxUploadRetryTimeMillis(30000);

                mStorageRef = FirebaseStorage.getInstance().getReference();

                StorageReference riversRef = mStorageRef.child(path).child(nameFile);
                riversRef.putFile(uri)
                        .continueWithTask(task -> riversRef.getDownloadUrl())
                        .addOnCompleteListener(task -> {
                            Uri downloadUrl = task.getResult();
                            emitter.onNext(downloadUrl.toString());
                            emitter.onComplete();
                        })
                        .addOnFailureListener(exception -> {
                            Log.e("ERROR", exception.getMessage());
                            emitter.onError(exception);
                        });
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
