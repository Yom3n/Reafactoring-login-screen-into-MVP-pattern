package pl.app.yomen.loginscreenwithmvp.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import pl.app.yomen.loginscreenwithmvp.LoginData;
import pl.app.yomen.loginscreenwithmvp.Volley.HttpRequests;

public class LoginPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    Presenter.LoginView view;
    @Mock
    HttpRequests model;



    @Test
    public void shoulShowEmptyLoginDataError()
    {
        LoginData emptyUserData = new LoginData("", "");
        Mockito.when(view.getLoginDataFromUser()).thenReturn(emptyUserData);
        Presenter presenter = new Presenter(view, model);
        presenter.sendLoginData();
        Mockito.verify(view).showEmptyUserDataError();
    }

}