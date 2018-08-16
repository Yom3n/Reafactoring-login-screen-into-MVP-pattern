package pl.app.yomen.loginscreenwithmvp.LoginActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import pl.app.yomen.loginscreenwithmvp.EditUserDataActivity;
import pl.app.yomen.loginscreenwithmvp.LoginData;
import pl.app.yomen.loginscreenwithmvp.MainActivity;
import pl.app.yomen.loginscreenwithmvp.R;
import pl.app.yomen.loginscreenwithmvp.RegisterActivity;
import pl.app.yomen.loginscreenwithmvp.User;
import pl.app.yomen.loginscreenwithmvp.Volley.VolleyHttpRequests;

public class LoginActivityView extends AppCompatActivity implements Presenter.LoginView {

    private String LOG = "LoginActivity";
    private EditText editTextLogin;
    private EditText editTextHaslo;
    private ProgressBar progressBar;
    private TextView textViewZleDane;

    Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewZleDane = findViewById(R.id.textViewZleDane);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextHaslo = findViewById(R.id.editTextHaslo);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //LOGOWANIE
        Button buttonZaloguj = findViewById(R.id.buttonZaloguj);
        buttonZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendLoginData();
                }
            });

        Button buttonZarejestruj = findViewById(R.id.buttonZarejestruj);
        buttonZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Ustawienie Permissions
        int Permission_ALL = 1;
        String[] Permisions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivityView.this, Permisions, Permission_ALL);
        }

        presenter = new Presenter(this, new VolleyHttpRequests(getApplicationContext()));
    }

    @Override
    public LoginData getLoginDataFromUser() {
        String login = String.valueOf(editTextLogin.getText());
        String password = String.valueOf(editTextHaslo.getText());
        Log.i(LOG, "Login: " + login + ", Password: " + password);
        return new LoginData(login,password);
    }

    @Override
    public Void showEmptyUserDataError() {
        showWrongLoginOrPassAnnouncment(textViewZleDane, getResources().getString(R.string.Pole_login_oraz_hasło_nie_mogą_być_puste));
        return null;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void startMainActivity(User user) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginOrPasswordError() {
        showWrongLoginOrPassAnnouncment(textViewZleDane, getResources().getString(R.string.b_dny_login_lub_has_o));
    }

    @Override
    public void startEditUserDataActivity(User user) {
        //@TODO user should be stored in shared pref
        Intent intent = new Intent(getApplicationContext(), EditUserDataActivity.class);
        startActivity(intent);
    }

    @Override
    public void showServerError() {
        Toast.makeText(this, getResources().getString(R.string.nie_udalo_sie_polaczyc_z_serwerem), Toast.LENGTH_SHORT).show();
    }

    private void showWrongLoginOrPassAnnouncment(TextView textViewZleDane, String s) {
        textViewZleDane.setText(s);
        textViewZleDane.setVisibility(View.VISIBLE);
    }
}
