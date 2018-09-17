package co.devhack.appfirebaseworkshop.view.presenter.user;

import co.devhack.appfirebaseworkshop.domain.UseCaseObserver;
import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.user.CreateUser;
import co.devhack.appfirebaseworkshop.view.activities.user.IRegisterView;
import co.devhack.appfirebaseworkshop.view.presenter.Presenter;

public class UserPresenter extends Presenter<IRegisterView> {

    private final CreateUser createUser;

    public UserPresenter(CreateUser createUser) {
        this.createUser = createUser;
    }

    public void dispose() {
        createUser.dispose();
    }

    public void createUser(String names,
                           String cellPhoneNumber,
                           String email,
                           String password) {

        getView().mostrarCargando();
        getView().deshabilitarControles();

        User user = new User();
        user.setNames(names);
        user.setCellPhoneNumber(cellPhoneNumber);
        user.setEmail(email);
        user.setPassword(password);

        createUser.setUser(user);
        createUser.execute(new UserObserver());

    }


    private class UserObserver extends UseCaseObserver<String> {

        @Override
        public void onNext(String idUser) {
            getView().showSendVerificationEmail();
            getView().goLogIn();
        }

        @Override
        public void onComplete() {
            super.onComplete();
            getView().habilitarControles();
            getView().ocultarCargando();
        }

        @Override
        public void onError(Throwable e) {
            getView().mostrarError(e.getMessage());
            getView().habilitarControles();
            getView().ocultarCargando();
        }
    }
}
