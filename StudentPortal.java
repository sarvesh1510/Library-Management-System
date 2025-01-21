import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;

public class StudentPortal extends JFrame implements ActionListener {
    @SuppressWarnings("unused")
    private JTextField erpIdField, searchField;
    private JPasswordField passwordField;
    private JButton loginButton, viewIssuedBooksButton, searchButton;
    private JLabel welcomeLabel, nameLabel, erpLabel, courseLabel, studentImageLabel;
    private JPanel studentInfoPanel;
    private Connection connection;

    private JFrame loginFrame;

    public StudentPortal() {
        setTitle("Student Portal");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());
        Font garamondFont = new Font("Garamond", Font.BOLD, 14);

        welcomeLabel = new JLabel("WELCOME TO BVIMR LIBRARY");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Garamond", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0x333A73));
        welcomeLabel.setBackground(new Color(0xFE7A36));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel, BorderLayout.CENTER);

        // Left Column - Student Info Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(20, getHeight())); // Set preferred size
        studentInfoPanel = new JPanel(new GridLayout(3, 1));
        studentInfoPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));

        nameLabel = new JLabel("Name: ");
        erpLabel = new JLabel("ERP ID: ");
        courseLabel = new JLabel("Course: ");
        studentImageLabel = new JLabel();

        nameLabel.setFont(garamondFont);
        erpLabel.setFont(garamondFont);
        courseLabel.setFont(garamondFont);

        nameLabel.setForeground(new Color(0x333A73));
        erpLabel.setForeground(new Color(0x333A73));
        courseLabel.setForeground(new Color(0x333A73));

        studentInfoPanel.add(nameLabel);
        studentInfoPanel.add(erpLabel);
        studentInfoPanel.add(courseLabel);

        leftPanel.add(studentInfoPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel);

        // Right Column - Image Display
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(80, getHeight())); // Set preferred size
        studentImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(studentImageLabel, BorderLayout.CENTER);
        mainPanel.add(rightPanel);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        viewIssuedBooksButton = new JButton("View Issued Books");
        viewIssuedBooksButton.addActionListener(this);
        viewIssuedBooksButton.setEnabled(false); // Disabled until login
        viewIssuedBooksButton.setFont(garamondFont);
        viewIssuedBooksButton.setBackground(new Color(0xFE7A36));
        viewIssuedBooksButton.setForeground(new Color(0x333A73));

        searchButton = new JButton("Search Books in Library");
        searchButton.addActionListener(this);
        searchButton.setFont(garamondFont);
        searchButton.setBackground(new Color(0xFE7A36));
        searchButton.setForeground(new Color(0x333A73));

        bottomPanel.add(viewIssuedBooksButton);
        bottomPanel.add(searchButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 150);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null); // Center the window
        loginFrame.setLayout(new GridLayout(3, 2));

        JLabel erpIdLabel = new JLabel("ERP ID:");
        erpIdLabel.setFont(garamondFont);
        erpIdLabel.setForeground(new Color(0x333A73));
        erpIdField = new JTextField();
        erpIdField.setFont(garamondFont);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(garamondFont);
        passwordLabel.setForeground(new Color(0x333A73));
        passwordField = new JPasswordField();
        passwordField.setFont(garamondFont);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(garamondFont);
        loginButton.setBackground(new Color(0xFE7A36));
        loginButton.setForeground(new Color(0x333A73));

        loginFrame.add(erpIdLabel);
        loginFrame.add(erpIdField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel()); // Placeholder for alignment
        loginFrame.add(loginButton);

        // Connect to the database
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@//SHEFALI:1521/xepdb1", "system", "tiger");
            System.out.println("Connection successful");
        } catch (Exception ee) {
            System.out.println("Connection failed: " + ee);
        }

        loginFrame.setVisible(true); // Display the login frame
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginUser();
        } else if (e.getSource() == viewIssuedBooksButton) {
            displayIssuedBooks();
        } else if (e.getSource() == searchButton) {
            searchBook();
        }
    }

    private void loginUser() {
        String erpId = erpIdField.getText();
        String password = new String(passwordField.getPassword());

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM StudentUsers WHERE ERP_ID = ? AND Password = ?");
            statement.setString(1, erpId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String name = resultSet.getString("Name");
                String course = resultSet.getString("Course");

                nameLabel.setText("Name: " + name);
                erpLabel.setText("ERP ID: " + erpId);
                courseLabel.setText("Course: " + course);

                File imageFile = new File("C:\\Programming\\Library Management System\\Images\\student.png");
                if (imageFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(ImageIO.read(imageFile));
                    studentImageLabel.setIcon(imageIcon);
                }

                loginFrame.dispose(); // Close the login frame
                viewIssuedBooksButton.setEnabled(true);
                studentInfoPanel.setVisible(true);
                JOptionPane.showMessageDialog(this, "Login successful");

                setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ERP ID or Password");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error logging in: " + ex.getMessage());
        }
    }

    private void displayIssuedBooks() {
        String erpId = erpIdField.getText();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ISSUED_BOOKS WHERE ERP_ID = ?");
            statement.setString(1, erpId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Display issued books
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] columns = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    columns[i - 1] = metaData.getColumnName(i);
                }

                ArrayList<Object[]> data = new ArrayList<>();
                do {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getObject(i);
                    }
                    data.add(row);
                } while (resultSet.next());

                Object[][] dataArray = new Object[data.size()][columnCount];
                for (int i = 0; i < data.size(); i++) {
                    dataArray[i] = data.get(i);
                }

                JTable table = new JTable(dataArray, columns);
                JScrollPane scrollPane = new JScrollPane(table);
                JFrame frame = new JFrame("Issued Books for ERP ID: " + erpId);
                frame.add(scrollPane);
                frame.setSize(800, 400);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No books issued for ERP ID: " + erpId);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching issued books: " + ex.getMessage());
        }
    }

    private void searchBook() {
        // Create a dialog for entering the search keyword
        String keyword = JOptionPane.showInputDialog(this, "Enter keyword to search books:");
        if (keyword != null && !keyword.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM BOOKS WHERE UPPER(TITLE) LIKE ?");
                statement.setString(1, "%" + keyword.toUpperCase() + "%");
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    // Display search results
                    ResultSetMetaData metaData = resultSet.getMetaData();
    
                    int columnCount = metaData.getColumnCount();
                    String[] columns = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        columns[i - 1] = metaData.getColumnName(i);
                    }
    
                    ArrayList<Object[]> data = new ArrayList<>();
                    do {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = resultSet.getObject(i);
                        }
                        data.add(row);
                    } while (resultSet.next());
    
                    Object[][] dataArray = new Object[data.size()][columnCount];
                    for (int i = 0; i < data.size(); i++) {
                        dataArray[i] = data.get(i);
                    }
    
                    JTable table = new JTable(dataArray, columns);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("Search Results for: " + keyword);
                    frame.add(scrollPane);
                    frame.setSize(800, 400);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "No books found matching: " + keyword);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error searching for books: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search.");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentPortal();
        });
    }
}
