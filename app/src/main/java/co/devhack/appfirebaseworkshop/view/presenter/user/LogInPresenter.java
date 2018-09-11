package co.devhack.appfirebaseworkshop.view.presenter.user;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import co.devhack.appfirebaseworkshop.domain.UseCaseObserver;
import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.user.LogInCredentials;
import co.devhack.appfirebaseworkshop.domain.user.LogInUser;
import co.devhack.appfirebaseworkshop.view.activities.user.ILoginView;
import co.devhack.appfirebaseworkshop.view.presenter.Presenter;

public class LogInPresenter extends Presenter<ILoginView> {

    private final LogInUser logInUser;
    private final LogInCredentials logInCredentials;

    public LogInPresenter(LogInUser logInUser, LogInCredentials logInCredentials) {
        this.logInUser = logInUser;
        this.logInCredentials = logInCredentials;
    }

    public void dispose() {
        logInUser.dispose();
        setView(null);
    }

    public void logIn(String email, String password) {
        getView().deshabilitarControles();
        getView().mostrarCargando();

        logInUser.setEmailAndPassword(email, password);
        logInUser.execute(new LogInObserver());
    }

    public void logInCredentials(String token) {

        getView().deshabilitarControles();
        getView().mostrarCargando();

        logInCredentials.setToken(token);
        logInCredentials.execute(new LogInCredentialObserver());

    }


    private class LogInObserver extends UseCaseObserver<User> {

        @Override
        public void onNext(User user) {
            getView().gotoProducts();
        }

        @Override
        public void onComplete() {
            getView().ocultarCargando();
            getView().habilitarControles();
        }

        @Override
        public void onError(Throwable e) {
            getView().mostrarError(e.getMessage());
            getView().ocultarCargando();
            getView().habilitarControles();
        }
    }

    private class LogInCredentialObserver extends UseCaseObserver<User> {

        @Override
        public void onNext(User user) {
            getView().gotoProducts();
        }

        @Override
        public void onComplete() {
            getView().ocultarCargando();
            getView().habilitarControles();
        }

        @Override
        public void onError(Throwable e) {
            getView().mostrarError(e.getMessage());
            getView().ocultarCargando();
            getView().habilitarControles();
        }
    }
}
