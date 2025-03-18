import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new LoginWindow());

        Scanner scanner = new Scanner(System.in);
        AgendaTelefonica agenda = new AgendaTelefonica();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Eroare la încărcarea driver-ului JDBC pentru SQLite: " + e.getMessage());
        }

        boolean ruleaza = true;
        while (ruleaza) {


            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();
                    System.out.print("Telefon: ");
                    String telefon = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    agenda.adaugaContact(nume, telefon, email);
                    break;

                case 2:
                    System.out.print("Nume contact de șters: ");
                    String numeSterge = scanner.nextLine();
                    agenda.stergeContact(numeSterge);
                    break;

                case 3:
                    System.out.print("Nume contact de actualizat: ");
                    String numeActualizeaza = scanner.nextLine();
                    System.out.print("Telefon nou: ");
                    String telefonNou = scanner.nextLine();
                    System.out.print("Email nou: ");
                    String emailNou = scanner.nextLine();
                    agenda.actualizeazaContact(numeActualizeaza, telefonNou, emailNou);
                    break;

                case 4:
                    agenda.getToateContactele();
                    break;

                case 5:
                    System.out.print("Nume de căutat: ");
                    String numeCautat = scanner.nextLine();
                    agenda.cautaContactSiReturneaza(numeCautat);
                    break;

                case 6:
                    ruleaza = false;
                    System.out.println("La revedere!");
                    break;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
        scanner.close();
    }
}
