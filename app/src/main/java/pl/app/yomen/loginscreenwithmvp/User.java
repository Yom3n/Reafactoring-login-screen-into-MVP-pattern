package pl.app.yomen.loginscreenwithmvp;
public class User {
    public int id_uzytkownika;
    public String login;
    public String haslo;
    public String nick;
    public String marka_auta;
    public String model_auta;
    public int pojemnosc;
    public boolean czyDoladowany;

    public User() {}

    public User(int idUzytkownika, String login, String haslo, String nick, String markaAuta, String modelAuta, int pojemnosc , boolean czyDoladowany) {
        this.id_uzytkownika = idUzytkownika;
        this.login = login;
        this.haslo = haslo;
        this.nick = nick;
        this.marka_auta = markaAuta;
        this.model_auta = modelAuta;
        this.pojemnosc = pojemnosc;
        this.czyDoladowany = czyDoladowany;
    }
    public User(int idUzytkownika, String login, String haslo, String nick) {
        this.id_uzytkownika = idUzytkownika;
        this.login = login;
        this.haslo = haslo;
        this.nick = nick;
    }



    public boolean isNickEmpty() {
        return nick == null;
    }
}
