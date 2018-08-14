package pl.app.yomen.loginscreenwithmvp;

import com.google.gson.Gson;

public class JSonConverter {
    private static final String LOG = "ConvertJSON";

    public static User parseJsonToUzytkownik(String JSonCode)
    {
        Gson gson = new Gson();
        return gson.fromJson(JSonCode, User.class);

    }




}
