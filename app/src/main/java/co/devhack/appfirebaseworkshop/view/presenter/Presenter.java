package co.devhack.appfirebaseworkshop.view.presenter;

import co.devhack.appfirebaseworkshop.view.activities.base.IView;

public class Presenter<T extends IView> {

    private T view;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public void init() {
    }

}
