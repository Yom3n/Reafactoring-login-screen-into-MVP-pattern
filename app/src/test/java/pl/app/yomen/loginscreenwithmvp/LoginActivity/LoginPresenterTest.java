package pl.app.yomen.loginscreenwithmvp.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class LoginPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    Presenter.LoginView view;
    @Mock
    HttpRequests model;
    @Mock
    VolleyCallback callback;


  /*  @Test
    public void shouldPassLoginDataToModel()
    {
        LoginData userData = new LoginData("someLogin", "sinePassword");
        Mockito.when(view.getLoginDataFromUser()).thenReturn(userData);
        Presenter presenter = new Presenter(view, model);
        presenter.sendLoginData();
        Mockito.verify(model).getLoginResponse(userData, callback);

    } */

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