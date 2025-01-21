import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentUsers extends JFrame implements ActionListener {
    @SuppressWarnings("unused")
    private JTextField nameField, erpIdField, courseField, usernameField, deleteField;
    @SuppressWarnings("unused")
    private JPasswordField passwordField, deletePasswordField;
    private JButton createUserButton, viewUserButton, deleteUserButton;
    private Connection connection;

    public StudentUsers() {
        setTitle("Student Users");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Garamond", Font.BOLD, 14));
        nameLabel.setForeground(new Color(0x333A73));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nameField, gbc);

        JLabel erpIdLabel = new JLabel("ERP ID:");
        erpIdLabel.setFont(new Font("Garamond", Font.BOLD, 14));
        erpIdLabel.setForeground(new Color(0x333A73));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(erpIdLabel, gbc);

        erpIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(erpIdField, gbc);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(new Font("Garamond", Font.BOLD, 14));
        courseLabel.setForeground(new Color(0x333A73));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(courseLabel, gbc);

        courseField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(courseField, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Garamond", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(0x333A73));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Garamond", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(0x333A73));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(passwordField, gbc);

        createUserButton = new JButton("Create User");
        createUserButton.setFont(new Font("Garamond", Font.BOLD, 14));
        createUserButton.setBackground(new Color(0xFE7A36));
        createUserButton.setForeground(new Color(0x333A73));
        createUserButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(createUserButton, gbc);

        viewUserButton = new JButton("View Users");
        viewUserButton.setFont(new Font("Garamond", Font.BOLD, 14));
        viewUserButton.setBackground(new Color(0xFE7A36));
        viewUserButton.setForeground(new Color(0x333A73));
        viewUserButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(viewUserButton, gbc);

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setFont(new Font("Garamond", Font.BOLD, 14));
        deleteUserButton.setBackground(new Color(0xFE7A36));
        deleteUserButton.setForeground(new Color(0x333A73));
        deleteUserButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(deleteUserButton, gbc);

        setVisible(true);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
            createTable(); // Create table if it doesn't exist
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
        String erpId = erpIdField.getText();
        String course = courseField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO StudentUsers (Name, ERP_ID, Course, Username, Password) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, erpId);
            statement.setString(3, course);
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM StudentUsers");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = metaData.getColumnName(i);
            }

            Object[][] data = new Object[100][columnCount]; // assuming a maximum of 100 users
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
        String erpIdToDelete = JOptionPane.showInputDialog(this, "Enter ERP ID to delete:");
        String passwordToDelete = JOptionPane.showInputDialog(this, "Enter password to delete:");

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM StudentUsers WHERE ERP_ID = ? AND Password = ?");
            statement.setString(1, erpIdToDelete);
            statement.setString(2, passwordToDelete);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User with ERP ID " + erpIdToDelete + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ERP ID or Password");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
        }
    }

    private void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        // Check if the table exists
        ResultSet resultSet = connection.getMetaData().getTables(null, null, "StudentUsers", null);
        if (!resultSet.next()) {
            // Create table if it doesn't exist
            String createTableQuery = "CREATE TABLE StudentUsers (Name VARCHAR(255), ERP_ID VARCHAR(10) PRIMARY KEY, Course VARCHAR(255), Username VARCHAR(255), Password VARCHAR(255))";
            statement.executeUpdate(createTableQuery);
        }
    }

    public static void main(String[] args) {
        new StudentUsers();
    }
}
