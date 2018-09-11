package co.devhack.appfirebaseworkshop.domain.user;

import co.devhack.appfirebaseworkshop.domain.UseCase;
import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.repository.IUserRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class LogInUser extends UseCase<User> {

    private final IUserRepository userRepository;
    private String email;
    private String password;

    public LogInUser(Scheduler executorThread, Scheduler uiThread, IUserRepository userRepository) {
        super(executorThread, uiThread);
        this.userRepository = userRepository;
    }

    public void setEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;

    }

    @Override
    protected Observable<User> crearObservableCasoUso() {
        return userRepository.logIn(this.email, this.password);
    }
}
