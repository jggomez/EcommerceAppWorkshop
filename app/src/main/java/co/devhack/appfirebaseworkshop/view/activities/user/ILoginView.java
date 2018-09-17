package co.devhack.appfirebaseworkshop.view.activities.user;

import co.devhack.appfirebaseworkshop.view.activities.base.IView;

public interface ILoginView extends IView {

    void logIn();

    void gotoProducts();

    void gotoSignUp();

    void showMessageEmailLinkSent();
}
