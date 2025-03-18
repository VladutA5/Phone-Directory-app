import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact {
    private int id; // Vom adăuga ID-ul pentru a lucra cu baza de date
    private String nume;
    private String numarTelefon;
    private String adresaEmail;

    public Contact(int id, String nume, String numarTelefon, String adresaEmail) {
        this.id = id;
        this.nume = nume;
        this.numarTelefon = numarTelefon;
        this.adresaEmail = adresaEmail;
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getNumarTelefon() { return numarTelefon; }
    public String getAdresaEmail() { return adresaEmail; }

    public void setNumarTelefon(String numarTelefon) { this.numarTelefon = numarTelefon; }
    public void setAdresaEmail(String adresaEmail) { this.adresaEmail = adresaEmail; }

    @Override
    public String toString() {
        return "ID: " + id + ", Nume: " + nume + ", Telefon: " + numarTelefon + ", Email: " + adresaEmail;
    }

    // Creează un obiect Contact dintr-un ResultSet
    public static Contact fromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nume = rs.getString("nume");
        String numarTelefon = rs.getString("numarTelefon");
        String adresaEmail = rs.getString("adresaEmail");
        return new Contact(id, nume, numarTelefon, adresaEmail);
    }
}
