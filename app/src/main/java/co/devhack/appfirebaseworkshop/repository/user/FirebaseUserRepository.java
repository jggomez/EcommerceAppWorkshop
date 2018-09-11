package co.devhack.appfirebaseworkshop.repository.user;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.devhack.appfirebaseworkshop.domain.model.User;
import io.reactivex.Observable;

public class FirebaseUserRepository {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public Observable<Boolean> createUser(User user) {

        return Observable.create(emitter -> auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onNext(true);
                        emitter.onComplete();

                        task.getResult().getUser().sendEmailVerification();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }


    public Observable<FirebaseUser> logIn(String email, String password) {
        return Observable.create(emitter -> auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().getUser().isEmailVerified()) {
                            emitter.onError(new Exception("Email User does not verified"));
                        }

                        emitter.onNext(task.getResult().getUser());
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    public Observable<FirebaseUser> logInCredential(String token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        return Observable.create(emitter -> auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onNext(task.getResult().getUser());
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }
}
