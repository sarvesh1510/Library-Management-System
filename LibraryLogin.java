import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryLogin extends JFrame implements ActionListener {
    private JButton librarianButton;
    private JButton studentButton;
    private JButton createUserButton;

    public LibraryLogin() {
        setTitle("Library System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
            
        // Create components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0x39393A), 20)); // Add border to main panel

        JPanel leftPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JLabel imageLabel = new JLabel(new ImageIcon("C:\\Programming\\Library Management System\\Images\\bvimr.jpg"));

        librarianButton = new JButton("Librarian");
        studentButton = new JButton("Student");
        createUserButton = new JButton("Create User");

        // Set Garamond Bold font for buttons
        Font garamondBold = new Font("Garamond", Font.BOLD, 14);
        librarianButton.setFont(garamondBold);
        studentButton.setFont(garamondBold);
        createUserButton.setFont(garamondBold);

        // Set colors for buttons
        Color buttonBgColor = new Color(0xFE7A36); // Background color
        Color buttonTextcolor = new Color(0x333A73); // Text color
        librarianButton.setBackground(buttonBgColor);
        studentButton.setBackground(buttonBgColor);
        createUserButton.setBackground(buttonBgColor);
        librarianButton.setForeground(buttonTextcolor);
        studentButton.setForeground(buttonTextcolor);
        createUserButton.setForeground(buttonTextcolor);

        // Increase button size
        Dimension buttonSize = new Dimension(200, 50); // Adjust as needed
        librarianButton.setPreferredSize(buttonSize);
        studentButton.setPreferredSize(buttonSize);
        createUserButton.setPreferredSize(buttonSize);

        // Add borders to buttons
        Border buttonBorder = BorderFactory.createLineBorder(Color.WHITE, 2);
        librarianButton.setBorder(buttonBorder);
        studentButton.setBorder(buttonBorder);
        createUserButton.setBorder(buttonBorder);

        // Add components to button panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(librarianButton, gbc);

        gbc.gridy++;
        buttonPanel.add(studentButton, gbc);

        gbc.gridy++;
        buttonPanel.add(createUserButton, gbc);

        // Add components to panels
        GridBagConstraints mainPanelGbc = new GridBagConstraints();
        mainPanelGbc.gridx = 0;
        mainPanelGbc.gridy = 0;
        mainPanelGbc.weightx = 0.7; // 70% width
        mainPanelGbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(leftPanel, mainPanelGbc);

        mainPanelGbc.gridx = 1;
        mainPanelGbc.weightx = 0.3; // 30% width
        mainPanel.add(buttonPanel, mainPanelGbc);

        leftPanel.add(imageLabel);

        // Add action listeners to buttons
        librarianButton.addActionListener(this);
        studentButton.addActionListener(this);
        createUserButton.addActionListener(this);

        // Add main panel to frame
        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == librarianButton) {
            LibraryManagementSignIn librarianSignIn = new LibraryManagementSignIn();
            librarianSignIn.setVisible(true);
        } else if (e.getSource() == studentButton) {
            StudentPortal studentPortal = new StudentPortal();
            studentPortal.setVisible(true);
        } else if (e.getSource() == createUserButton) {
            CreateUsersGUI createUsersGUI = new CreateUsersGUI();
            createUsersGUI.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryLogin loginPage = new LibraryLogin();
            loginPage.setVisible(true);
        });
    }
}
