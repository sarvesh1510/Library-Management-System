import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CreateUsersGUI extends JFrame implements ActionListener {
    private JButton librarianUsersButton, studentUsersButton;
    private JPanel mainPanel;

    public CreateUsersGUI() {
        setTitle("Create Users");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Create main panel with background image
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        // Buttons
        librarianUsersButton = createButton("Librarian Users");
        librarianUsersButton.addActionListener(this);
        librarianUsersButton.setForeground(new Color(0x333A73));  
        librarianUsersButton.setBackground(new Color(0xFE7A36));

        GridBagConstraints librarianUsersConstraints = new GridBagConstraints();
        librarianUsersConstraints.gridx = 0;
        librarianUsersConstraints.gridy = 0;
        librarianUsersConstraints.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(librarianUsersButton, librarianUsersConstraints);

        studentUsersButton = createButton("Student Users");
        studentUsersButton.addActionListener(this);
        studentUsersButton.setForeground(new Color(0x333A73));  
        studentUsersButton.setBackground(new Color(0xFE7A36));
        GridBagConstraints studentUsersConstraints = new GridBagConstraints();
        studentUsersConstraints.gridx = 0;
        studentUsersConstraints.gridy = 1;
        studentUsersConstraints.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(studentUsersButton, studentUsersConstraints);

        setVisible(true);
    }

    // Method to create styled buttons
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Garamond", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == librarianUsersButton) {
            new LibrarianUsers();
        } else if (e.getSource() == studentUsersButton) {
            new StudentUsers();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CreateUsersGUI();
    }
}