import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        configureWindow();
        initializeUI();
    }

    private void configureWindow() {
        setTitle("Autentificare");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Titlu
        JLabel titleLabel = new JLabel("Agenda Telefonică", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> attemptLogin());

        // Enter key pentru login
        getRootPane().setDefaultButton(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            // Succes - închide fereastra de login și deschide aplicația principală
            dispose();
            SwingUtilities.invokeLater(() -> new AgendaGUI());
        } else {
            // Eșec - afișează mesaj de eroare
            JOptionPane.showMessageDialog(
                    this,
                    "Username sau parolă incorectă!",
                    "Eroare de autentificare",
                    JOptionPane.ERROR_MESSAGE
            );
            // Resetează câmpurile
            passwordField.setText("");
            usernameField.setText("");
            usernameField.requestFocus();
        }
    }

    public static void main(String[] args) {
        // Asigură că interfața este creată în Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }
}