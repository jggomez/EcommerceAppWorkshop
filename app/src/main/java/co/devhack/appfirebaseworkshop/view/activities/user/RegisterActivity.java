package co.devhack.appfirebaseworkshop.view.activities.user;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.devhack.appfirebaseworkshop.R;
import co.devhack.appfirebaseworkshop.domain.user.CreateUser;
import co.devhack.appfirebaseworkshop.repository.user.FirebaseUserRepository;
import co.devhack.appfirebaseworkshop.repository.user.UserRepository;
import co.devhack.appfirebaseworkshop.util.Util;
import co.devhack.appfirebaseworkshop.util.UtilAnalytics;
import co.devhack.appfirebaseworkshop.view.activities.base.BaseActivity;
import co.devhack.appfirebaseworkshop.view.presenter.user.UserPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity
        implements IRegisterView, Validator.ValidationListener {

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtNames)
    EditText txtNames;

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtCellPhone)
    EditText txtCellPhone;

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtEmail)
    EditText txtEmail;

    @NotEmpty(messageResId = R.string.field_required)
    @Length(max = 30, messageResId = R.string.long_max)
    @BindView(R.id.txtPassword)
    EditText txtPassword;

    private Validator validator;
    private UserPresenter userPresenter;
    private MaterialDialog materialDialog;

    @Override
    public void initView() {
        super.initView();

        validator = new Validator(this);
        validator.setValidationListener(this);

        userPresenter = new UserPresenter(
                new CreateUser(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new UserRepository(
                                new FirebaseUserRepository()
                        )
                )
        );

        txtNames.requestFocus();

        userPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.dispose();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.btnSignUp)
    @Override
    public void registerUser() {
        validator.validate();
    }

    @Override
    public void goLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSendVerificationEmail() {
        Toast.makeText(this, getString(R.string.lbl_verification_email),
                Toast.LENGTH_SHORT).show();
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
        txtNames.setEnabled(true);
        txtCellPhone.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPassword.setEnabled(true);
    }

    @Override
    public void deshabilitarControles() {
        txtNames.setEnabled(false);
        txtCellPhone.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPassword.setEnabled(false);
    }

    @Override
    public void mostrarError(String error) {
        Snackbar.make(this.findViewById(android.R.id.content),
                error,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onValidationSucceeded() {
        String names = txtNames.getText().toString();
        String cellPhone = txtCellPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        userPresenter.createUser(names, cellPhone, email, password);

        UtilAnalytics.logEventNewUser(this, names, email);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validationFailed(errors);
    }
}
