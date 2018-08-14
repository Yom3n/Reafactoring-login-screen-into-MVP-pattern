package pl.app.yomen.loginscreenwithmvp.LoginActivity;

public interface HttpRequests {
    void getLoginResponse(LoginData loginData, VolleyCallback callback);
}
