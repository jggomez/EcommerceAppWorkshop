package co.devhack.appfirebaseworkshop.domain.user;

import co.devhack.appfirebaseworkshop.domain.UseCase;
import co.devhack.appfirebaseworkshop.domain.repository.IUserRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class LogInLinkEmail extends UseCase<Boolean> {

    private final IUserRepository userRepository;
    private String email;

    public LogInLinkEmail(Scheduler executorThread, Scheduler uiThread,
                          IUserRepository userRepository) {
        super(executorThread, uiThread);
        this.userRepository = userRepository;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    protected Observable<Boolean> crearObservableCasoUso() {
        return userRepository.logInLinkEmail(this.email);
    }
}
