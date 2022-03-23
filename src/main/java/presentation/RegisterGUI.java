package presentation;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterGUI extends JFrame {

    private JLabel usernameLabel = new JLabel("username: ");
    private JTextField usernameTextField = new JTextField(40);
    private JLabel emailLabel = new JLabel("email: ");
    private JTextField emailTextField = new JTextField(40);
    private JLabel passwordLabel = new JLabel("password: ");
    private JTextField passwordTextField = new JTextField(40);

    private JButton closeButton = new JButton("Close");
    private JButton registerButton = new JButton("Register");

    public RegisterGUI(ActionListener registerNewUserActionListener, ActionListener closeButtonActionListener) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(3, 3));
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameTextField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailTextField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordTextField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        buttonsPanel.add(closeButton);
        buttonsPanel.add(registerButton);

        registerButton.addActionListener(registerNewUserActionListener);
        closeButton.addActionListener(closeButtonActionListener);

        mainPanel.add(registerPanel);
        mainPanel.add(buttonsPanel);

        this.setPreferredSize(new Dimension(400, 150));
        this.setContentPane(mainPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Register");
        setLocationRelativeTo(null);
    }

    public User getUser(){
        User user = new User();
        user.setUsername(usernameTextField.getText());
        user.setEmail(emailTextField.getText());
        user.setPassword(passwordTextField.getText());
        return user;
    }

    public void showRegistered() {
        JOptionPane.showMessageDialog(registerButton,"Register successful");

    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public String getUsername(){
        return usernameTextField.getText();
    }

    public String getEmail(){ return emailTextField.getText(); }

    public String getPassword(){
        return passwordTextField.getText();
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(JTextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(JTextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }
}