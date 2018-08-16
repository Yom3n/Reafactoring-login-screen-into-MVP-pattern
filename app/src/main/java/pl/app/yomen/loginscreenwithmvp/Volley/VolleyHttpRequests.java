package pl.app.yomen.loginscreenwithmvp.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import pl.app.yomen.loginscreenwithmvp.DaneSerwera;
import pl.app.yomen.loginscreenwithmvp.JSonConverter;
import pl.app.yomen.loginscreenwithmvp.LoginData;
import pl.app.yomen.loginscreenwithmvp.User;

public class VolleyHttpRequests implements HttpRequests {
    private static final String TAG = VolleyHttpRequests.class.getSimpleName();
    private Context ctx;

    public VolleyHttpRequests(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void getLoginResponse(final LoginData loginData, final VolleyCallback callback) {
        String URL = DaneSerwera.url + "/zaloguj.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Response: "+response);
                if (isJSonCode(response)) {
                    User user = JSonConverter.parseJsonToUzytkownik(response);
                    callback.onSuccess(user);
                }
                else callback.onFail(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                Log.d(TAG, error.getMessage());
                callback.onError(errorMessage);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> daneLogowania = new HashMap<>();
                daneLogowania.put("login", loginData.login);
                daneLogowania.put("haslo", loginData.password);
                Log.d(TAG, "Login: "+ loginData.login+"   "+ "Password: "+loginData.password);
                return daneLogowania;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);
    }

        private boolean isJSonCode(String response) {
            return response.contains("{");
        }


}
