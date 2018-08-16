package pl.app.yomen.loginscreenwithmvp.Volley;

import pl.app.yomen.loginscreenwithmvp.User;

public interface VolleyCallback {
    void onSuccess(User user);
    void onFail(String result);
    void onError(String error);
}
