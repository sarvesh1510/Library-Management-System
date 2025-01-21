import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryGUI extends JFrame implements ActionListener {
    private JButton librarianLoginBtn, studentLoginBtn, createUserBtn;

    public LibraryGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Background Panel
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgIcon = new ImageIcon("C:\\Programming\\Library Management System\\Images\\books.jpg");
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null); // Set layout to null to position components manually
        add(bgPanel, BorderLayout.CENTER);

        // Logo
        ImageIcon logoIcon = new ImageIcon("C:\\Programming\\Library Management System\\Images\\bvimr.jpg");
        int logoWidth = (int) (logoIcon.getIconWidth() * 0.8);
        int logoHeight = (int) (logoIcon.getIconHeight() * 0.8);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds((screenWidth - logoWidth) / 2, (screenHeight - logoHeight) / 2 - 100, logoWidth, logoHeight);
        bgPanel.add(logoLabel);

        // Buttons
        librarianLoginBtn = new JButton("Librarian Login");
        studentLoginBtn = new JButton("Student Login");
        createUserBtn = new JButton("Create Users");

        // Set Garamond Bold font for buttons
        Font garamondBold = new Font("Garamond", Font.BOLD, 14);
        librarianLoginBtn.setFont(garamondBold);
        studentLoginBtn.setFont(garamondBold);
        createUserBtn.setFont(garamondBold);

        librarianLoginBtn.addActionListener(this);
        studentLoginBtn.addActionListener(this);
        createUserBtn.addActionListener(this);

        // Set button colors
        Color buttonColor = new Color(59, 89, 182);
        Color textColor = new Color(245, 245, 220);
        librarianLoginBtn.setBackground(buttonColor);
        librarianLoginBtn.setForeground(textColor);
        studentLoginBtn.setBackground(buttonColor);
        studentLoginBtn.setForeground(textColor);
        createUserBtn.setBackground(buttonColor);
        createUserBtn.setForeground(textColor);

        int buttonWidth = 200;
        int buttonHeight = 40;
        int buttonSpacing = 20;

        int startX = (screenWidth - (buttonWidth * 3 + buttonSpacing * 2)) / 2;
        int startY = (screenHeight + logoHeight) / 2 + 50; // Adjust the Y position to be below the logo

        librarianLoginBtn.setBounds(startX, startY, buttonWidth, buttonHeight);
        studentLoginBtn.setBounds(startX + buttonWidth + buttonSpacing, startY, buttonWidth, buttonHeight);
        createUserBtn.setBounds(startX + 2 * (buttonWidth + buttonSpacing), startY, buttonWidth, buttonHeight);

        bgPanel.add(librarianLoginBtn);
        bgPanel.add(studentLoginBtn);
        bgPanel.add(createUserBtn);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == librarianLoginBtn) {
            // Open Librarian Login Window
            LibraryManagementSignIn librarianSignIn = new LibraryManagementSignIn();
            librarianSignIn.setVisible(true);
        } else if (e.getSource() == studentLoginBtn) {
            // Open Student Login Window
            StudentPortal studentPortal = new StudentPortal();
            studentPortal.setVisible(true);
        } else if (e.getSource() == createUserBtn) {
            // Open Create Users Window
            CreateUsersGUI createUsersGUI = new CreateUsersGUI();
            createUsersGUI.setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            LibraryGUI libraryGUI = new LibraryGUI();
            libraryGUI.setVisible(true);
        });
    }
}