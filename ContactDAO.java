import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/varut/Desktop/BazeDeDatePI";

    // Adaugă un contact în baza de date
    public void adaugaContact(String nume, String numarTelefon, String adresaEmail) {
        String sql = "INSERT INTO contacte (nume, numarTelefon, adresaEmail) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nume);
            pstmt.setString(2, numarTelefon);
            pstmt.setString(3, adresaEmail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Șterge un contact din baza de date
    public void stergeContact(int id) {
        String sql = "DELETE FROM contacte WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Actualizează un contact în baza de date
    public void actualizeazaContact(int id, String numeNou, String numarTelefonNou, String adresaEmailNoua) {
        String sql = "UPDATE contacte SET nume = ?, numarTelefon = ?, adresaEmail = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeNou);
            pstmt.setString(2, numarTelefonNou);
            pstmt.setString(3, adresaEmailNoua);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Afișează toate contactele din baza de date
    public List<Contact> afiseazaContacte() {
        String sql = "SELECT * FROM contacte";
        List<Contact> contacte = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                contacte.add(Contact.fromResultSet(rs));  // Creăm un obiect Contact din ResultSet
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contacte;
    }

    // Caută un contact după nume
    public Contact cautaContact(String nume) {
        String sql = "SELECT * FROM contacte WHERE nume = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nume);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Contact.fromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;  // Returnează null dacă nu a fost găsit niciun contact
    }
}
