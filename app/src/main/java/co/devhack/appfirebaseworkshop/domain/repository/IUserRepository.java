package co.devhack.appfirebaseworkshop.domain.repository;

import co.devhack.appfirebaseworkshop.domain.model.User;
import io.reactivex.Observable;

public interface IUserRepository {

    Observable<Boolean> createUser(User user);

    Observable<User> logIn(String email, String password);

    Observable<User> logInCrendentials(String token);

    Observable<Boolean> logInLinkEmail(String email);

    Observable<String> insertUser(User user);

}
