package co.devhack.appfirebaseworkshop.repository.user;

import com.google.firebase.auth.FirebaseAuth;

import co.devhack.appfirebaseworkshop.domain.model.User;
import io.reactivex.Observable;

public class FirebaseUserRepository {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public Observable<Boolean> createUser(User user) {

        return Observable.create(emitter -> {

            auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onNext(true);
                            emitter.onComplete();

                            task.getResult().getUser().sendEmailVerification();
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public void logIn(String email, String password) {

    }
}
