import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LibrarianUsers extends JFrame implements ActionListener {
    @SuppressWarnings("unused")
    private JTextField nameField, librarianIdField, phoneNumberField, usernameField, deleteField;
    @SuppressWarnings("unused")
    private JPasswordField passwordField, deletePasswordField;
    private JButton createUserButton, viewUserButton, deleteUserButton;
    private Connection connection;

    public LibrarianUsers() {
        setTitle("Librarian Users");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new GridBagLayout());

        // Set Garamond font for all components
        Font garamondFont = new Font("Garamond", Font.BOLD, 14);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(garamondFont);
        nameLabel.setForeground(new Color(0x333A73));        

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(garamondFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nameField, gbc);

        JLabel librarianIdLabel = new JLabel("Librarian ID:");
        librarianIdLabel.setFont(garamondFont);
        librarianIdLabel.setForeground(new Color(0x333A73));        

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(librarianIdLabel, gbc);

        librarianIdField = new JTextField(20);
        librarianIdField.setFont(garamondFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(librarianIdField, gbc);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setFont(garamondFont);
        phoneNumberLabel.setForeground(new Color(0x333A73));        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(phoneNumberLabel, gbc);

        phoneNumberField = new JTextField(20);
        phoneNumberField.setFont(garamondFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(phoneNumberField, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(garamondFont);
        usernameLabel.setForeground(new Color(0x333A73));        

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(garamondFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(garamondFont);
        passwordLabel.setForeground(new Color(0x333A73));        

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(garamondFont);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(passwordField, gbc);

        createUserButton = new JButton("Create User");
        createUserButton.addActionListener(this);
        createUserButton.setBackground(new Color(0xFE7A36)); 
        createUserButton.setForeground(new Color(0x333A73)); 
        createUserButton.setFont(garamondFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(createUserButton, gbc);

        viewUserButton = new JButton("View Users");
        viewUserButton.addActionListener(this);
        viewUserButton.setBackground(new Color(0xFE7A36)); 
        viewUserButton.setForeground(new Color(0x333A73));
        viewUserButton.setFont(garamondFont);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(viewUserButton, gbc);

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(this);
        deleteUserButton.setBackground(new Color(0xFE7A36)); 
        deleteUserButton.setForeground(new Color(0x333A73));
        deleteUserButton.setFont(garamondFont);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(deleteUserButton, gbc);

        setVisible(true);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
            createTable(); 
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createUserButton) {
            createUser();
        } else if (e.getSource() == viewUserButton) {
            viewUsers();
        } else if (e.getSource() == deleteUserButton) {
            deleteUser();
        }
    }

    private void createUser() {
        String name = nameField.getText();
        String librarianId = librarianIdField.getText();
        String phoneNumber = phoneNumberField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO LibrarianUsers (Name, Librarian_ID, Phone_Number, Username, Password) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, librarianId);
            statement.setString(3, phoneNumber);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User created successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating user: " + ex.getMessage());
        }
    }

    private void viewUsers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM LibrarianUsers");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = metaData.getColumnName(i);
            }

            Object[][] data = new Object[100][columnCount]; 
            int rowCount = 0;
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[rowCount][i - 1] = resultSet.getObject(i);
                }
                rowCount++;
            }

            Object[][] dataArray = new Object[rowCount][columnCount];
            System.arraycopy(data, 0, dataArray, 0, rowCount);

            JTable table = new JTable(dataArray, columns);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("View Users");
            frame.add(scrollPane);
            frame.setSize(800, 400);
            frame.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error viewing users: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        String librarianIdToDelete = JOptionPane.showInputDialog(this, "Enter Librarian ID to delete:");
        String passwordToDelete = JOptionPane.showInputDialog(this, "Enter password to delete:");

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM LibrarianUsers WHERE Librarian_ID = ? AND Password = ?");
            statement.setString(1, librarianIdToDelete);
            statement.setString(2, passwordToDelete);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User with Librarian ID " + librarianIdToDelete + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Librarian ID or Password");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
        }
    }

    private void createTable() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = connection.getMetaData().getTables(null, null, "LibrarianUsers", null);
        if (!resultSet.next()) {
            String createTableQuery = "CREATE TABLE LibrarianUsers (Name VARCHAR(255), Librarian_ID VARCHAR(10) PRIMARY KEY, Phone_Number VARCHAR(20), Username VARCHAR(255), Password VARCHAR(255))";
            statement.executeUpdate(createTableQuery);
        }
    }

    public static void main(String[] args) {
        new LibrarianUsers();
    }
}
