package co.devhack.appfirebaseworkshop.view.activities.base;

public interface IView {

    void mostrarCargando();

    void ocultarCargando();

    void habilitarControles();

    void deshabilitarControles();

    void mostrarError(String error);

}
