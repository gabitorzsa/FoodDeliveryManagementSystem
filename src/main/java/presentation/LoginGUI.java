package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class LoginGUI extends JFrame {

    private JLabel usernameLabel = new JLabel("username: ");
    private JTextField usernameTextField = new JTextField( 40);
    private JLabel passwordLabel = new JLabel("password: ");
    private JTextField passwordTextField = new JTextField( 40);

    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");

    public LoginGUI(ActionListener loginListener, ActionListener registerButtonActionListener, WindowListener windowListener) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.addWindowListener(windowListener);
        JPanel usernamePassPanel = new JPanel();
        usernamePassPanel.setLayout(new GridLayout(2,2));
        usernamePassPanel.add(usernameLabel);
        usernamePassPanel.add(usernameTextField);
        usernamePassPanel.add(passwordLabel);
        usernamePassPanel.add(passwordTextField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,2));
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

        loginButton.addActionListener(loginListener);
        registerButton.addActionListener(registerButtonActionListener);

        mainPanel.add(usernamePassPanel);
        mainPanel.add(buttonsPanel);

        this.setPreferredSize(new Dimension(400,100));
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Log In");
        setLocationRelativeTo(null);
    }

    @Override
    public synchronized void addWindowListener(WindowListener l) {
        super.addWindowListener(l);
    }

    public String getUsername(){
        return usernameTextField.getText();
    }

    public String getPassword(){
        return passwordTextField.getText();
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public void showError() {
        JOptionPane.showMessageDialog(loginButton,"Wrong password");
    }

    public void showEmptyFields() {
        JOptionPane.showMessageDialog(loginButton,"Please introduce username and password");
    }

    public void showSuccess() {
        JOptionPane.showMessageDialog(loginButton,"Success logging in");
    }
}
