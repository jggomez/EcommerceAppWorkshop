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
        return null;
    }
}
