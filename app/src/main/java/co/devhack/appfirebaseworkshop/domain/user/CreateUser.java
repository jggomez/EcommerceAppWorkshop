package co.devhack.appfirebaseworkshop.domain.user;

import co.devhack.appfirebaseworkshop.domain.UseCase;
import co.devhack.appfirebaseworkshop.domain.model.User;
import co.devhack.appfirebaseworkshop.domain.repository.IUserRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class CreateUser extends UseCase<Boolean> {

    private final IUserRepository userRepository;
    private User user;

    public CreateUser(Scheduler executorThread, Scheduler uiThread,
                      IUserRepository userRepository) {
        super(executorThread, uiThread);
        this.userRepository = userRepository;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    protected Observable<Boolean> crearObservableCasoUso() {
        return userRepository.createUser(this.user);
    }
}
