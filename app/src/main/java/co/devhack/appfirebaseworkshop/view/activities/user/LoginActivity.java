package co.devhack.appfirebaseworkshop.view.activities.user;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.devhack.appfirebaseworkshop.R;
import co.devhack.appfirebaseworkshop.domain.user.LogInCredentials;
import co.devhack.appfirebaseworkshop.domain.user.LogInLinkEmail;
import co.devhack.appfirebaseworkshop.domain.user.LogInUser;
import co.devhack.appfirebaseworkshop.repository.user.FirebaseUserRepository;
import co.devhack.appfirebaseworkshop.repository.user.UserRepository;
import co.devhack.appfirebaseworkshop.util.Util;
import co.devhack.appfirebaseworkshop.view.activities.base.BaseActivity;
import co.devhack.appfirebaseworkshop.view.activities.products.ProductsActivity;
import co.devhack.appfirebaseworkshop.view.presenter.user.LogInPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements
        ILoginView, Validator.ValidationListener {

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtEmail)
    EditText txtEmail;

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @BindView(R.id.btnLogIn)
    Button btnLogIn;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.btnLogInFacebook)
    LoginButton btnLogInFacebook;

    Validator validator;
    private MaterialDialog materialDialog;
    private LogInPresenter presenter;
    private CallbackManager callbackManager;

    @Override
    public void initView() {
        super.initView();

        validator = new Validator(this);
        validator.setValidationListener(this);

        presenter = new LogInPresenter(
                new LogInUser(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new UserRepository(
                                new FirebaseUserRepository()
                        )
                ),
                new LogInCredentials(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new UserRepository(
                                new FirebaseUserRepository()
                        )),
                new LogInLinkEmail(Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new UserRepository(
                                new FirebaseUserRepository()
                        ))
        );

        presenter.setView(this);

        txtEmail.requestFocus();

        initLoginFacebook();

    }

    private void initLoginFacebook() {
        btnLogInFacebook.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();

        btnLogInFacebook.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        presenter.logInCredentials(loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        mostrarError(error.getMessage());
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btnLogIn)
    @Override
    public void logIn() {
        validator.validate();
    }

    @Override
    public void gotoProducts() {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnSignUp)
    @Override
    public void gotoSignUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessageEmailLinkSent() {
        Snackbar.make(this.findViewById(android.R.id.content),
                R.string.message_email_link_sent,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void mostrarCargando() {
        materialDialog = Util.showMaterialProgressDialog(this, getString(R.string.loading));
    }

    @Override
    public void ocultarCargando() {
        materialDialog.dismiss();
    }

    @Override
    public void habilitarControles() {
        txtEmail.setEnabled(true);
        txtPassword.setEnabled(true);
        btnLogIn.setEnabled(true);
        btnSignUp.setEnabled(true);
    }

    @Override
    public void deshabilitarControles() {
        txtEmail.setEnabled(false);
        txtPassword.setEnabled(false);
        btnLogIn.setEnabled(false);
        btnSignUp.setEnabled(false);
    }

    @Override
    public void mostrarError(String error) {
        Snackbar.make(this.findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onValidationSucceeded() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        presenter.logIn(email, password);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validationFailed(errors);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
