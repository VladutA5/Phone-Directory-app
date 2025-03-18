import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AgendaGUI extends JFrame {
    private final AgendaTelefonica agenda;
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 12);

    private JTextField tfNume, tfTelefon, tfEmail, tfCautare;
    private JTable tabelContacte;
    private DefaultTableModel modelTabel;

    public AgendaGUI() {
        agenda = new AgendaTelefonica();
        configureWindow();
        initializeUI();
        addInputValidation();
        actualizeazaTabel(); // Încarcă datele inițiale
    }

    private void configureWindow() {
        setTitle("Agenda Telefonică");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private void initializeUI() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMenuPanel(), BorderLayout.WEST);

        // Panou central split între formular și tabel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                createFormPanel(),
                createTabelPanel()
        );
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(BUTTON_COLOR);
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lblLogo = new JLabel("Agenda Telefonică");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        lblLogo.setForeground(Color.WHITE);
        panelHeader.add(lblLogo);

        return panelHeader;
    }

    private JPanel createMenuPanel() {
        JPanel panelMeniu = new JPanel(new GridLayout(6, 1, 10, 10));
        panelMeniu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelMeniu.setBackground(BACKGROUND_COLOR);

        panelMeniu.add(createMenuButton("Adaugă contact", e -> adaugaContact()));
        panelMeniu.add(createMenuButton("Șterge contact", e -> stergeContact()));
        panelMeniu.add(createMenuButton("Actualizează contact", e -> actualizeazaContact()));
        panelMeniu.add(createMenuButton("Afișează toate contactele", e -> actualizeazaTabel()));
        panelMeniu.add(createMenuButton("Caută contact", e -> cautaContact()));
        panelMeniu.add(createMenuButton("Ieșire", e -> System.exit(0)));

        return panelMeniu;
    }

    private JPanel createTabelPanel() {
        JPanel panelTabel = new JPanel(new BorderLayout());
        panelTabel.setBackground(BACKGROUND_COLOR);

        // Creare model tabel
        String[] coloane = {"ID", "Nume", "Telefon", "Email"};
        modelTabel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelContacte = new JTable(modelTabel);
        tabelContacte.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelContacte.getSelectedRow() != -1) {
                completareFormular(tabelContacte.getSelectedRow());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelContacte);
        panelTabel.add(scrollPane, BorderLayout.CENTER);

        return panelTabel;
    }

    private void completareFormular(int row) {
        tfNume.setText(modelTabel.getValueAt(row, 1).toString());
        tfTelefon.setText(modelTabel.getValueAt(row, 2).toString());
        tfEmail.setText(modelTabel.getValueAt(row, 3).toString());
    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createFormPanel() {
        JPanel panelFormular = new JPanel(new GridBagLayout());
        panelFormular.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelFormular.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Rând 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormular.add(createLabel("Nume:"), gbc);

        gbc.gridx = 1;
        tfNume = new JTextField(20);
        panelFormular.add(tfNume, gbc);

        // Rând 2
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormular.add(createLabel("Telefon:"), gbc);

        gbc.gridx = 1;
        tfTelefon = new JTextField(20);
        panelFormular.add(tfTelefon, gbc);

        // Rând 3
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormular.add(createLabel("Email:"), gbc);

        gbc.gridx = 1;
        tfEmail = new JTextField(20);
        panelFormular.add(tfEmail, gbc);

        // Rând 4
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormular.add(createLabel("Căutare:"), gbc);

        gbc.gridx = 1;
        tfCautare = new JTextField(20);
        panelFormular.add(tfCautare, gbc);

        return panelFormular;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
    }



    private void actualizeazaTabel() {
        modelTabel.setRowCount(0); // Șterge toate rândurile existente
        List<Contact> contacte = agenda.getToateContactele(); // Trebuie adăugată această metodă în AgendaTelefonica
        for (Contact contact : contacte) {
            modelTabel.addRow(new Object[]{
                    contact.getId(),
                    contact.getNume(),
                    contact.getNumarTelefon(),
                    contact.getAdresaEmail()
            });
        }
    }

    private void addInputValidation() {
        // Validare pentru câmpul nume (doar litere și spații)
        tfNume.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isLetter(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                    JOptionPane.showMessageDialog(null,
                            "Vă rugăm introduceți doar litere în câmpul nume!",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Validare pentru câmpul telefon (doar cifre)
        tfTelefon.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                    JOptionPane.showMessageDialog(null,
                            "Vă rugăm introduceți doar cifre în câmpul telefon!",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void adaugaContact() {
        String nume = tfNume.getText().trim();
        String telefon = tfTelefon.getText().trim();
        String email = tfEmail.getText().trim();

        // Validări suplimentare
        if (nume.isEmpty() || telefon.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Toate câmpurile sunt obligatorii!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validare format nume
        if (!nume.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this,
                    "Numele poate conține doar litere și spații!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validare format telefon
        if (!telefon.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Numărul de telefon poate conține doar cifre!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validare lungime minimă pentru nume și telefon
        if (nume.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Numele trebuie să conțină cel puțin 3 caractere!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (telefon.length() < 10) {
            JOptionPane.showMessageDialog(this,
                    "Numărul de telefon trebuie să conțină cel puțin 10 cifre!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validare format email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                    "Adresa de email nu este validă!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        agenda.adaugaContact(nume, telefon, email);
        JOptionPane.showMessageDialog(this, "Contact adăugat cu succes!");
        actualizeazaTabel();
        clearFields();
    }

    private void actualizeazaContact() {
        int row = tabelContacte.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selectați un contact pentru actualizare!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String numeVechi = modelTabel.getValueAt(row, 1).toString();
        String numeNou = tfNume.getText().trim();
        String telefonNou = tfTelefon.getText().trim();
        String emailNou = tfEmail.getText().trim();

        // Validări similare ca la adăugare
        if (numeNou.isEmpty() || telefonNou.isEmpty() || emailNou.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Toate câmpurile sunt obligatorii!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!numeNou.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this,
                    "Numele poate conține doar litere și spații!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!telefonNou.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    "Numărul de telefon poate conține doar cifre!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (numeNou.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Numele trebuie să conțină cel puțin 3 caractere!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (telefonNou.length() < 10) {
            JOptionPane.showMessageDialog(this,
                    "Numărul de telefon trebuie să conțină cel puțin 10 cifre!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!emailNou.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                    "Adresa de email nu este validă!",
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        agenda.actualizeazaContact(numeVechi, telefonNou, emailNou);
        actualizeazaTabel();
        clearFields();
        JOptionPane.showMessageDialog(this, "Contact actualizat cu succes!");
    }



    private void stergeContact() {
        int row = tabelContacte.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectați un contact pentru ștergere!", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nume = modelTabel.getValueAt(row, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Sigur doriți să ștergeți contactul " + nume + "?",
                "Confirmare ștergere",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            agenda.stergeContact(nume);
            actualizeazaTabel();
            clearFields();
            JOptionPane.showMessageDialog(this, "Contact șters cu succes!");
        }
    }


    private void cautaContact() {
        String numeCautat = tfCautare.getText();
        if (numeCautat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceți un nume pentru căutare!", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact contact = agenda.cautaContactSiReturneaza(numeCautat); // Trebuie adăugată această metodă
        if (contact != null) {
            modelTabel.setRowCount(0);
            modelTabel.addRow(new Object[]{
                    contact.getId(),
                    contact.getNume(),
                    contact.getNumarTelefon(),
                    contact.getAdresaEmail()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Contactul nu a fost găsit!", "Informație", JOptionPane.INFORMATION_MESSAGE);
            actualizeazaTabel(); // Reafișează toate contactele
        }
    }

    private void clearFields() {
        tfNume.setText("");
        tfTelefon.setText("");
        tfEmail.setText("");
        tfCautare.setText("");
    }


}