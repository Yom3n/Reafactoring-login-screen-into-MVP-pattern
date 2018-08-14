package pl.app.yomen.loginscreenwithmvp.LoginActivity;

import android.util.Log;

import pl.app.yomen.loginscreenwithmvp.JSonConverter;
import pl.app.yomen.loginscreenwithmvp.User;

public class Presenter {


    private static final String TAG = Presenter.class.getSimpleName();
    private LoginView view;
    private HttpRequests model;

    public interface LoginView
    {
        LoginData getLoginDataFromUser();

        Void showEmptyUserDataError();

        void showProgressBar();
        void hideProgressBar();
        void LogIn(User user);

        void showLoginOrPasswordError();

        void startEditUserDataActivity(User user);
    }

    public Presenter(LoginView view, HttpRequests httpRequests) {
        this.view = view;
        this.model = httpRequests;
    }
    public void sendLoginData() {
        LoginData loginData = view.getLoginDataFromUser();
        if (userDataNotEmpty(loginData))
        {
            view.showProgressBar();
            model.getLoginResponse(loginData, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d(TAG, result);
                    if (isJSonCode(result)) {
                        User user = JSonConverter.parseJsonToUzytkownik(result);
                        Log.i(TAG, "Zalogowano: " + user.login);
                        if (user.isNickEmpty()) {
                            view.startEditUserDataActivity(user);

                        } else {
                            Log.i(TAG, "nick: " + user.nick);
                            view.LogIn(user);
                        }
                    } else { //Błędny getLoginResponse lub hasło
                        view.showLoginOrPasswordError();
                    }
                   view.hideProgressBar();
                }
            });
        }
        else view.showEmptyUserDataError();
    }


    private boolean userDataNotEmpty(LoginData loginData) {
        return !loginData.login.equals("") && !loginData.password.equals("");
    }

    private boolean isJSonCode(String response) {
        return response.contains("{");
    }

}
