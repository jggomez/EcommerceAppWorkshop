package co.devhack.appfirebaseworkshop.domain;

import io.reactivex.observers.DisposableObserver;

public abstract class UseCaseObserver<T> extends DisposableObserver<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }
}

