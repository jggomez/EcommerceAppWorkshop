package co.devhack.appfirebaseworkshop.domain;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T> {

    private final CompositeDisposable compositeDisposable;
    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public UseCase(Scheduler executorThread,
                   Scheduler uiThread) {
        this.uiThread = uiThread;
        this.executorThread = executorThread;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void execute(DisposableObserver<T> disposableObserver) {

        if (disposableObserver == null) {
            throw new IllegalArgumentException("El disposableObserver no es valido");
        }

        final Observable<T> observable = this.crearObservableCasoUso()
                .subscribeOn(executorThread).observeOn(uiThread);

        DisposableObserver observer =
                observable.subscribeWith(disposableObserver);

        compositeDisposable.add(disposableObserver);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected abstract Observable<T> crearObservableCasoUso();
}
