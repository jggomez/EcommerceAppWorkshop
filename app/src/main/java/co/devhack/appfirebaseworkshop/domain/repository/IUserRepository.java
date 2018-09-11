package co.devhack.appfirebaseworkshop.domain.repository;

import com.facebook.login.widget.LoginButton;

import co.devhack.appfirebaseworkshop.domain.model.User;
import io.reactivex.Observable;

public interface IUserRepository {

    Observable<Boolean> createUser(User user);

    Observable<User> logIn(String email, String password);

    Observable<User> logInFacebook(String token);

}
