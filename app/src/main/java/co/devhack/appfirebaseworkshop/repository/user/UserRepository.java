package co.devhack.appfirebaseworkshop.repository.user;

import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.repository.IUserRepository;
import io.reactivex.Observable;

public class UserRepository implements IUserRepository {

    private final FirebaseUserRepository firebaseUserRepository;

    public UserRepository(FirebaseUserRepository firebaseUserRepository) {
        this.firebaseUserRepository = firebaseUserRepository;
    }

    @Override
    public Observable<Boolean> createUser(User user) {
        return firebaseUserRepository.createUser(user);
    }

    @Override
    public Observable<User> logIn(String email, String password) {
        return firebaseUserRepository.logIn(email, password)
                .map(firebaseUser -> {
                    User user = new User();
                    user.setEmail(firebaseUser.getEmail());
                    user.setNames(firebaseUser.getDisplayName());
                    return user;
                });
    }

    @Override
    public Observable<User> logInFacebook(String token) {
        return firebaseUserRepository.logInCredential(token)
                .map(firebaseUser -> {
                    User user = new User();
                    user.setEmail(firebaseUser.getEmail());
                    user.setNames(firebaseUser.getDisplayName());
                    return user;
                });
    }
}
