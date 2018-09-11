package co.devhack.appfirebaseworkshop.view.activities.user;

import co.devhack.appfirebaseworkshop.domain.user.CreateUser;
import co.devhack.appfirebaseworkshop.view.activities.IView;

public interface IRegisterView extends IView {

    void registerUser();

    void goLogIn();

    void showSendVerificationEmail();

}
