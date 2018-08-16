package pl.app.yomen.loginscreenwithmvp.LoginActivity;

import android.util.Log;

import pl.app.yomen.loginscreenwithmvp.LoginData;
import pl.app.yomen.loginscreenwithmvp.User;
import pl.app.yomen.loginscreenwithmvp.Volley.HttpRequests;
import pl.app.yomen.loginscreenwithmvp.Volley.VolleyCallback;

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
        
        void startMainActivity(User user);

        void showLoginOrPasswordError();

        void startEditUserDataActivity(User user);

        void showServerError();
    }

    public Presenter(LoginView view, HttpRequests httpRequests) {
        this.view = view;
        this.model = httpRequests;
    }
    public void sendLoginData() {
        LoginData loginData = view.getLoginDataFromUser();
        if (userDataNotEmpty(loginData)) {
            view.showProgressBar();
            model.getLoginResponse(loginData, new VolleyCallback() {
                @Override
                public void onSuccess(User user) {
                    if (user.isNickEmpty()) {
                        view.startEditUserDataActivity(user);
                    } else {
                        Log.i(TAG, "nick: " + user.nick);
                        view.startMainActivity(user);
                    }
                    view.hideProgressBar();
                }
                @Override
                public void onFail(String result) {
                    view.showLoginOrPasswordError();
                    view.hideProgressBar();
                }

                @Override
                public void onError(String error) {
                    view.showServerError();
                    view.hideProgressBar();
                }
            });
        }else view.showEmptyUserDataError(); 
    }
    
    private boolean userDataNotEmpty(LoginData loginData) {
        return !loginData.login.equals("") && !loginData.password.equals("");
    }

}
