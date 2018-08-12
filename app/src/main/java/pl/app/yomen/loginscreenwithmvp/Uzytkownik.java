package pl.app.yomen.loginscreenwithmvp;
import com.google.gson.annotations.SerializedName;
public class Uzytkownik {
    @SerializedName("id_uzytkownika")
    public int id_uzytkownika;
    @SerializedName("login")
    public String login;
    @SerializedName("haslo")
    public String haslo;
    @SerializedName("nick")
    public String nick;
    @SerializedName("marka_auta")
    public String marka_auta;
    @SerializedName("model_auta")
    public String model_auta;
    @SerializedName("pojemnosc")
    public int pojemnosc;
    @SerializedName("czyDoladowany")
    public boolean czyDoladowany;

    public Uzytkownik() {
    }

    public Uzytkownik(int idUzytkownika, String login, String haslo, String nick, String markaAuta, String modelAuta, int pojemnosc , boolean czyDoladowany) {
        this.id_uzytkownika = idUzytkownika;
        this.login = login;
        this.haslo = haslo;
        this.nick = nick;
        this.marka_auta = markaAuta;
        this.model_auta = modelAuta;
        this.pojemnosc = pojemnosc;
        this.czyDoladowany = czyDoladowany;
    }
}
