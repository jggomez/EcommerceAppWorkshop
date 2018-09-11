package co.devhack.appfirebaseworkshop.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setToolbar();
        bindViews();
        initView();
    }

    public void initView() {

    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    private void setToolbar() {
        /*toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }*/
    }

    public void validacionFallo(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public abstract int getLayoutId();
}
