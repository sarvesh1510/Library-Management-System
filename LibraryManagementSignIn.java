import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.Font;


public class LibraryManagementSignIn extends JFrame implements ActionListener {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection connection;

    public LibraryManagementSignIn() {
        setTitle("Library Management System - Sign In");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        Font garamondFont = new Font("Garamond", Font.BOLD, 14);
        

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel userIdLabel = new JLabel("Username:");
        userIdLabel.setFont(garamondFont);

        userIdField = new JTextField();
        userIdField.setFont(garamondFont); 
        userIdLabel.setForeground(new Color(0x333A73));

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setFont(garamondFont); 
        passwordLabel.setFont(garamondFont);
        passwordLabel.setForeground(new Color(0x333A73));

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Garamond", Font.BOLD, 14));
        loginButton.setForeground(new Color(0x333A73));
        loginButton.setBackground(new Color(0xFE7A36));
        loginButton.setFont(new Font("Garamond", Font.BOLD, 14)); // Set font for the button

        loginButton.addActionListener(this);

        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
        setVisible(true);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userIdField.getText();
            String password = new String(passwordField.getPassword());

            // Check if username and password match
            if (validateUser(username, password)) {
                // Open the main GUI if login successful
                new LibraryManagement();
                dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        }
    }

    private boolean validateUser(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM LibrarianUsers WHERE Username = ? AND Password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error validating user: " + ex.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        new LibraryManagementSignIn();
    }
}

class CreateUser extends JFrame implements ActionListener {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton createButton;
    private Connection connection;

    public CreateUser() {
        setTitle("Create User");
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel userIdLabel = new JLabel("User ID:");
        userIdField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        createButton = new JButton("Create");
        createButton.setFont(new Font("Garamond", Font.BOLD, 14));


        createButton.addActionListener(this);

        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(createButton);

        add(panel);
        setVisible(true);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());

            try {
                // Check if user already exists
                if (checkUserExists(userId)) {
                    JOptionPane.showMessageDialog(this, "User already exists. Please choose a different User ID.");
                    return;
                }

                // Insert user into the database
                PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS (USER_ID, PASSWORD) VALUES (?, ?)");
                statement.setString(1, userId);
                statement.setString(2, password);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "User created successfully");
                dispose(); // Close the create user window
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error creating user: " + ex.getMessage());
            }
        }
    }

    private boolean checkUserExists(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USER_ID = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count > 0;
    }
}

class IssueBook extends JFrame implements ActionListener {
    private JTextField bookIdField, bookNameField, erpIdField;
    private JButton issueButton;
    private Connection connection;

    public IssueBook() {
        setTitle("Issue Book");
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        Font garamondFont = new Font("Garamond", Font.BOLD, 14);


        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel bookIdLabel = new JLabel("Book ID:");

        bookIdField = new JTextField();
        bookIdField.setFont(garamondFont); 
        JLabel bookNameLabel = new JLabel("Book Name:");
        bookNameField = new JTextField();
        bookNameField.setFont(garamondFont); 
        JLabel erpIdLabel = new JLabel("ERP ID:");
        erpIdField = new JTextField();
        erpIdField.setFont(garamondFont); 
        issueButton = new JButton("Issue");
        issueButton.setFont(new Font("Garamond", Font.BOLD, 14));


        issueButton.addActionListener(this);

        panel.add(bookIdLabel);
        bookIdLabel.setFont(garamondFont);
        panel.add(bookIdField);
        panel.add(bookNameLabel);
        bookNameLabel.setFont(garamondFont);
        panel.add(bookNameField);
        panel.add(erpIdLabel);
        erpIdLabel.setFont(garamondFont);
        panel.add(erpIdField);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(issueButton);

        add(panel);
        setVisible(true);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            String bookId = bookIdField.getText();
            String bookName = bookNameField.getText();
            String erpId = erpIdField.getText();

            if (bookId.isEmpty() || bookName.isEmpty() || erpId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            if (!checkERPExists(erpId)) {
                JOptionPane.showMessageDialog(this, "ERP ID does not exist.");
                return;
            }

            try {
                // Check if the book is available
                PreparedStatement availabilityStatement = connection.prepareStatement("SELECT COPIES FROM BOOKS WHERE BOOK_ID = ?");
                availabilityStatement.setString(1, bookId);
                ResultSet availabilityResult = availabilityStatement.executeQuery();

                if (availabilityResult.next()) {
                    int availableCopies = Integer.parseInt(availabilityResult.getString("COPIES"));
                    if (availableCopies <= 0) {
                        JOptionPane.showMessageDialog(this, "No copies of the book are available for issue.");
                        return;
                    }

                    PreparedStatement issueStatement = connection.prepareStatement("INSERT INTO ISSUED_BOOKS (ERP_ID, BOOK_ID, TITLE) VALUES (?, ?, ?)");
                    issueStatement.setString(1, erpId);
                    issueStatement.setString(2, bookId);
                    issueStatement.setString(3, bookName);
                    issueStatement.executeUpdate();

                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE BOOKS SET COPIES = ? WHERE BOOK_ID = ?");
                    updateStatement.setString(1, String.valueOf(availableCopies - 1));
                    updateStatement.setString(2, bookId);
                    updateStatement.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Book issued successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No book found with ID: " + bookId);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error issuing book: " + ex.getMessage());
            }
        }
    }

    private boolean checkERPExists(String erpId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM StudentUsers WHERE ERP_ID = ?");
            statement.setString(1, erpId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error checking ERP ID: " + ex.getMessage());
            return false;
        }
    }
}

class LibraryManagement extends JFrame implements ActionListener {
    @SuppressWarnings("unused")
    private JLabel label1, label2, label3, label7, deleteLabel;
    private JTextField textField1, textField2, textField3, textField7, deleteTextField, searchField;
    private JButton addButton, viewButton, editButton, deleteRecordButton, clearButton, exitButton, saveButton, searchButton, issueBookButton, viewIssuedBooksButton, collectBookButton;
    private JPanel panel;
    private Connection connection;

    public LibraryManagement() {
        Font garamondFont = new Font("Garamond", Font.BOLD, 14);
        setTitle("Library Management System");
        setSize(600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color backgroundColor = new Color(0xFE7A36); // Background color
        Color textColor = new Color(0x333A73); // Text color

        UIManager.put("Button.background", backgroundColor);
        UIManager.put("Button.foreground", textColor);
        Color orangetext = new Color(0x333A73); // Text color
        
        JLabel label1 = new JLabel("Book ID*");
        label1.setFont(garamondFont);
        JLabel label2 = new JLabel("Book Title*");
        label2.setFont(garamondFont);
        JLabel label3 = new JLabel("Author*");
        label3.setFont(garamondFont);
        JLabel label7 = new JLabel("Number of Copies*");
        label7.setFont(garamondFont);
        JLabel deleteLabel = new JLabel("Enter Book ID to Edit or Delete*");
        deleteLabel.setFont(garamondFont);

        // Set the text color for each label
        label1.setForeground(orangetext);
        label2.setForeground(orangetext);
        label3.setForeground(orangetext);
        label7.setForeground(orangetext);
        deleteLabel.setForeground(orangetext);

        textField1 = new JTextField(10);
        textField1.setFont(garamondFont); 
        textField2 = new JTextField(20);
        textField2.setFont(garamondFont); 
        textField3 = new JTextField(20);
        textField3.setFont(garamondFont); 
        textField7 = new JTextField(10);
        textField7.setFont(garamondFont); 
        deleteTextField = new JTextField(10);
        deleteTextField.setFont(garamondFont); 
        searchField = new JTextField(20);
        searchField.setFont(garamondFont); 

        addButton = new JButton("Add");
        addButton.setFont(new Font("Garamond", Font.BOLD, 14));
        viewButton = new JButton("View");
        viewButton.setFont(new Font("Garamond", Font.BOLD, 14));
        editButton = new JButton("Edit");
        editButton.setFont(new Font("Garamond", Font.BOLD, 14));
        deleteRecordButton = new JButton("Delete Record");
        deleteRecordButton.setFont(new Font("Garamond", Font.BOLD, 14));
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Garamond", Font.BOLD, 14));
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Garamond", Font.BOLD, 14));
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Garamond", Font.BOLD, 14));
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Garamond", Font.BOLD, 14));
        issueBookButton = new JButton("Issue a Book");
        issueBookButton.setFont(new Font("Garamond", Font.BOLD, 14));
        viewIssuedBooksButton = new JButton("View Issued Books");
        viewIssuedBooksButton.setFont(new Font("Garamond", Font.BOLD, 14));
        collectBookButton = new JButton("Collect Book");
        collectBookButton.setFont(new Font("Garamond", Font.BOLD, 14));

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteRecordButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);
        saveButton.addActionListener(this);
        searchButton.addActionListener(this);
        issueBookButton.addActionListener(this);
        viewIssuedBooksButton.addActionListener(this);
        collectBookButton.addActionListener(this);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        

        panel.add(label1, gbc);
        gbc.gridx++;
        panel.add(textField1, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label2, gbc);
        gbc.gridx++;
        panel.add(textField2, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label3, gbc);
        gbc.gridx++;
        panel.add(textField3, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label7, gbc);
        gbc.gridx++;
        panel.add(textField7, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(deleteLabel, gbc);
        gbc.gridx++;
        panel.add(deleteTextField, gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(new JLabel(), gbc); // Empty label for spacing
        gbc.gridy++;
        panel.add(addButton, gbc);
        gbc.gridy++;
        panel.add(viewButton, gbc);
        gbc.gridy++;
        panel.add(editButton, gbc);
        gbc.gridy++;
        panel.add(deleteRecordButton, gbc);
        gbc.gridy++;
        panel.add(clearButton, gbc);
        gbc.gridy++;
        panel.add(exitButton, gbc);
        gbc.gridy++;
        panel.add(saveButton, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchBookTitleLabel = new JLabel("Search Book Title:");
        searchBookTitleLabel.setFont(garamondFont);
        searchBookTitleLabel.setForeground(new Color(0x333A73)); 
        panel.add(searchBookTitleLabel, gbc);        
        gbc.gridx++;
        panel.add(searchField, gbc);
        gbc.gridx++;
        panel.add(searchButton, gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(new JLabel(), gbc); // Empty label for spacing
        gbc.gridy++;
        panel.add(issueBookButton, gbc);
        gbc.gridy++;
        panel.add(viewIssuedBooksButton, gbc);
        gbc.gridy++;
        panel.add(collectBookButton, gbc);

        saveButton.setVisible(false); 
        saveButton.setFont(new Font("Garamond", Font.BOLD, 14));
        // Initially hide the save button

        add(panel);
        setVisible(true);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String bookId = textField1.getText();
            String bookTitle = textField2.getText();
            String author = textField3.getText();
            String copies = textField7.getText();
    
            // Check if any field is empty
            if (bookId.isEmpty() || bookTitle.isEmpty() || author.isEmpty() || copies.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
    
            try {
                // Check if the Book_ID already exists
                PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM BOOKS WHERE BOOK_ID = ?");
                checkStatement.setString(1, bookId);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(this, "Book ID already exists. Please choose a different Book ID.");
                    return;
                }
    
                // Insert the book into the database
                PreparedStatement statement = connection.prepareStatement("INSERT INTO BOOKS VALUES (?, ?, ?, ?)");
                statement.setString(1, bookId);
                statement.setString(2, bookTitle);
                statement.setString(3, author);
                statement.setString(4, copies);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record Added");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage());
            }
        }

        if (e.getSource() == viewButton) {
            new ViewBooks();
        }

        if (e.getSource() == editButton) {
            saveButton.setVisible(true);
            addButton.setVisible(false);
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE BOOK_ID = ?");
                statement.setString(1, deleteTextField.getText());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    textField1.setText(rs.getString(1));
                    textField2.setText(rs.getString(2));
                    textField3.setText(rs.getString(3));
                    textField7.setText(rs.getString(4));
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == deleteRecordButton) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM BOOKS WHERE BOOK_ID = ?");
                statement.setString(1, deleteTextField.getText());
                int x = statement.executeUpdate();
                if (x > 0) {
                    JOptionPane.showMessageDialog(this, "Record Deleted");
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == clearButton) {
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField7.setText("");
            deleteTextField.setText("");
        }

        if (e.getSource() == exitButton) {
            dispose();
        }

        if (e.getSource() == saveButton) {
            try {
                PreparedStatement statement = connection.prepareStatement("UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, COPIES = ? WHERE BOOK_ID = ?");
                statement.setString(1, textField2.getText());
                statement.setString(2, textField3.getText());
                statement.setString(3, textField7.getText());
                statement.setString(4, textField1.getText());
                int x = statement.executeUpdate();
                if (x > 0) {
                    JOptionPane.showMessageDialog(this, "Record Updated");
                } else {
                    JOptionPane.showMessageDialog(this, "Record not found");
                }
                saveButton.setVisible(false);
                addButton.setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == searchButton) {
            new SearchBook(searchField.getText());
        }

        if (e.getSource() == issueBookButton) {
            new IssueBook();
        }

        if (e.getSource() == viewIssuedBooksButton) {
            new ViewIssuedBooks();
        }

        if (e.getSource() == collectBookButton) {
            new CollectBook();
        }
    }

    public static void main(String[] args) {
        new LibraryManagement();
    }
}

class ViewBooks extends JFrame {
    private JTable table;

    public ViewBooks() {
        setTitle("View Books");
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM BOOKS");
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            // Get column names
            int columnCount = metaData.getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = metaData.getColumnName(i);
            }

            ArrayList<String[]> data = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            table = new JTable(data.toArray(new String[0][]), columns);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
            setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class SearchBook extends JFrame {
    private JTable table;

    public SearchBook(String title) {
        setTitle("Search Results for: " + title);
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE UPPER(TITLE) LIKE ?");
            statement.setString(1, "%" + title.toUpperCase() + "%");
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            // Get column names
            int columnCount = metaData.getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = metaData.getColumnName(i);
            }

            // Get data
            ArrayList<String[]> data = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            // Create table
            table = new JTable(data.toArray(new String[0][]), columns);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
            setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class ViewIssuedBooks extends JFrame {
    private JTable table;

    public ViewIssuedBooks() {
        setTitle("View Issued Books");
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ISSUED_BOOKS");
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            // Get column names
            int columnCount = metaData.getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = metaData.getColumnName(i);
            }

            // Get data
            ArrayList<String[]> data = new ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                data.add(row);
            }

            // Create table
            table = new JTable(data.toArray(new String[0][]), columns);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);
            setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class CollectBook extends JFrame implements ActionListener {
    private JTextField bookIdField, erpIdField;
    private JButton collectButton;
    private Connection connection;

    public CollectBook() {
        setTitle("Collect Book");
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdField = new JTextField();
        JLabel erpIdLabel = new JLabel("ERP ID:");
        erpIdField = new JTextField();
        collectButton = new JButton("Collect");
        collectButton.setFont(new Font("Garamond", Font.BOLD, 14));

        collectButton.addActionListener(this);

        panel.add(bookIdLabel);
        panel.add(bookIdField);
        panel.add(erpIdLabel);
        panel.add(erpIdField);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(collectButton);

        add(panel);
        setVisible(true);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == collectButton) {
            String bookId = bookIdField.getText();
            String erpId = erpIdField.getText();

            try {
                // Check if the book is issued to the given ERP ID
                PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM ISSUED_BOOKS WHERE BOOK_ID = ? AND ERP_ID = ?");
                checkStatement.setString(1, bookId);
                checkStatement.setString(2, erpId);
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // Book is issued to the given ERP ID, delete the record from issued books
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM ISSUED_BOOKS WHERE BOOK_ID = ? AND ERP_ID = ?");
                    deleteStatement.setString(1, bookId);
                    deleteStatement.setString(2, erpId);
                    int deletedRows = deleteStatement.executeUpdate();

                    if (deletedRows > 0) {
                        // Increment the number of copies for the collected book
                        PreparedStatement incrementStatement = connection.prepareStatement("UPDATE BOOKS SET COPIES = COPIES + 1 WHERE BOOK_ID = ?");
                        incrementStatement.setString(1, bookId);
                        incrementStatement.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Book collected successfully.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error collecting book. Please check the details.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Book not issued to this ERP ID.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error collecting book: " + ex.getMessage());
            }
        }
    }
}