import java.util.List;

class AgendaTelefonica {
    private ContactDAO contactDAO;

    public AgendaTelefonica() {
        this.contactDAO = new ContactDAO();
    }

    // 1. Adaugă un contact nou
    public void adaugaContact(String nume, String numar, String email) {
        contactDAO.adaugaContact(nume, numar, email);
    }

    // 2. Șterge un contact
    public void stergeContact(String nume) {
        Contact contact = contactDAO.cautaContact(nume);
        if (contact != null) {
            contactDAO.stergeContact(contact.getId());
        }
    }

    // 3. Actualizează un contact
    public void actualizeazaContact(String nume, String numarNou, String emailNou) {
        Contact contact = contactDAO.cautaContact(nume);
        if (contact != null) {
            contactDAO.actualizeazaContact(contact.getId(), nume, numarNou, emailNou);
        }
    }

    // 4. Returnează toate contactele pentru afișare în GUI
    public List<Contact> getToateContactele() {
        return contactDAO.afiseazaContacte();
    }

    // 5. Caută și returnează un contact după nume pentru GUI
    public Contact cautaContactSiReturneaza(String nume) {
        return contactDAO.cautaContact(nume);
    }

    // Metodă pentru verificarea existenței unui contact
    public boolean existaContact(String nume) {
        return contactDAO.cautaContact(nume) != null;
    }
}