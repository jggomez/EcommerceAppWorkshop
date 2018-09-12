package co.devhack.appfirebaseworkshop.domain.user;

import co.devhack.appfirebaseworkshop.domain.UseCase;
import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.repository.IUserRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class LogInCredentials extends UseCase<User> {

    private final IUserRepository userRepository;
    private String token;

    public LogInCredentials(Scheduler executorThread, Scheduler uiThread,
                            IUserRepository userRepository) {
        super(executorThread, uiThread);
        this.userRepository = userRepository;
    }

    public void setToken(String token) {
        this.token = token;

    }

    @Override
    protected Observable<User> crearObservableCasoUso() {
        return userRepository.logInCrendentials(this.token);
    }

}
