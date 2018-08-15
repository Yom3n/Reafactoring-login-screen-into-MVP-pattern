package pl.app.yomen.loginscreenwithmvp.Volley;

import pl.app.yomen.loginscreenwithmvp.LoginData;

public interface HttpRequests {
    void getLoginResponse(LoginData loginData, VolleyCallback callback);
}
