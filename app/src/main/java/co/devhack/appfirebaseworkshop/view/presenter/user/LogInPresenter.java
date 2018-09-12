package co.devhack.appfirebaseworkshop.view.presenter.user;

import com.crashlytics.android.Crashlytics;

import co.devhack.appfirebaseworkshop.domain.UseCaseObserver;
import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.user.LogInCredentials;
import co.devhack.appfirebaseworkshop.domain.user.LogInLinkEmail;
import co.devhack.appfirebaseworkshop.domain.user.LogInUser;
import co.devhack.appfirebaseworkshop.view.activities.user.ILoginView;
import co.devhack.appfirebaseworkshop.view.presenter.Presenter;

public class LogInPresenter extends Presenter<ILoginView> {

    private final LogInUser logInUser;
    private final LogInCredentials logInCredentials;
    private final LogInLinkEmail logInLinkEmail;

    public LogInPresenter(LogInUser logInUser, LogInCredentials logInCredentials, LogInLinkEmail logInLinkEmail) {
        this.logInUser = logInUser;
        this.logInCredentials = logInCredentials;
        this.logInLinkEmail = logInLinkEmail;
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

    public void logInEmailLink(String email) {

        getView().deshabilitarControles();
        getView().mostrarCargando();

        logInLinkEmail.setEmail(email);
        logInLinkEmail.execute(new LogInEmailLinkObserver());

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

    private class LogInEmailLinkObserver extends UseCaseObserver<Boolean> {

        @Override
        public void onNext(Boolean sucessful) {
            getView().showMessageEmailLinkSent();
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
