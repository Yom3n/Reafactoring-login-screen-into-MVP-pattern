package pl.app.yomen.loginscreenwithmvp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Uzytkownik uzytkownik;


    private String LOG = "LoginActivity";
    private EditText editTextLogin;
    private EditText editTextHaslo;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextView textViewZleDane = findViewById(R.id.textViewZleDane);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextHaslo = findViewById(R.id.editTextHaslo);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //LOGOWANIE
        Button buttonZaloguj = findViewById(R.id.buttonZaloguj);
        buttonZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String login = String.valueOf(editTextLogin.getText());
                final String haslo = String.valueOf(editTextHaslo.getText());
                Log.i(LOG, "Login: " + login + ", Haslo: " + haslo);
                if (polaLogowanieNiePuste(login, haslo)) {
                    progressBar.setVisibility(View.VISIBLE);
                    String URL = DaneSerwera.url + "/zaloguj.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(LOG, response);
                            if (isJSonCode(response)) {
                                uzytkownik = JSonConverter.parseJsonToUzytkownik(response);
                                Log.i(LOG, "Zalogowano: " + uzytkownik.login);
                                if (isNickEmpty()) {
                                    startUzupelnijDaneActivity();

                                } else {
                                    Log.i(LOG, "nick: " + uzytkownik.nick);
                                    startMainActivity();
                                }
                            } else { //Błędny login lub hasło
                                showWrongLoginOrPassAnnouncment(textViewZleDane, getResources().getString(R.string.b_dny_login_lub_has_o));
                            }
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(LOG, error.getMessage());
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.nie_udalo_sie_polaczyc_z_serwerem), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String,String> daneLogowania = new HashMap<>();
                            daneLogowania.put("login", login);
                            daneLogowania.put("haslo", haslo);
                            return daneLogowania;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return super.getHeaders();
                        }
                    };

                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                } else {
                    showWrongLoginOrPassAnnouncment(textViewZleDane, getResources().getString(R.string.Pole_login_oraz_hasło_nie_mogą_być_puste));
                }
            }
        });

        Button buttonZarejestruj = findViewById(R.id.buttonZarejestruj);
        buttonZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uzytkownik = new Uzytkownik();
                startRegisterActivity();
            }
        });
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void showWrongLoginOrPassAnnouncment(TextView textViewZleDane, String s) {
        textViewZleDane.setText(s);
        textViewZleDane.setVisibility(View.VISIBLE);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void startUzupelnijDaneActivity() {
        Intent intentUzupelnijDane = new Intent(getApplicationContext(), EditUserDataActivity.class);
        startActivity(intentUzupelnijDane);
    }

    private boolean isNickEmpty() {
        return uzytkownik.nick == null;
    }

    private boolean isJSonCode(String response) {
        return response.contains("{");
    }

    private boolean polaLogowanieNiePuste(String login, String haslo) {
        return !login.equals("") && !haslo.equals("");
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Ustawienie Permissions
        int Permission_ALL = 1;
        String[] Permisions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, Permisions, Permission_ALL);
        }
    }
}
