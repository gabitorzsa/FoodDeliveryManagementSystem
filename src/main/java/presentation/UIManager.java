package presentation;

import business.DeliveryService;
import business.UserService;
import data.ReportGenerator;
import model.MenuItem;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UIManager {

    AdministratorGUI adminScreen;
    LoginGUI loginScreen;
    ClientGUI clientScreen;
    RegisterGUI registerScreen;
    EmployeeGUI employeeScreen;
    SearchGUI searchScreen;

    ActionListener loginListener;
    ActionListener addButtonActionListener;
    ActionListener deleteButtonActionListener;
    ActionListener logoutButtonActionListener;
    ActionListener clientOrderListener;
    ActionListener clearButtonActionListener;
    ActionListener searchButtonActionLister;
    ActionListener searchProductActionListener;
    ActionListener registerNewUserActionListener;
    ActionListener registerButtonActionListener;
    ActionListener closeButtonActionListener;
    ActionListener openRegisterScreenListener;

    ListSelectionListener adminTableItemClickListener;
    ListSelectionListener clientTableItemClickListener;

    WindowListener windowListener;

    DeliveryService deliveryService;
    UserService userService;

    User loggedInUser;

    public UIManager() {

        deliveryService = new DeliveryService();
        userService = new UserService();

        deliveryService.importProducts();
        //        deliveryService = SerializerDelivery.deserializeDelivery("serializedDataDelivery.ser");
        //        userService = SerializerUser.deserializeUser("serializedDataUser.ser");

        setupListeners();
        displayLoginScreen();

        displayEmployeeScreen();
    }

    private void setupListeners() {
        this.addButtonActionListener = e -> {
            MenuItem item = adminScreen.getEditMenuItem();
            deliveryService.addProduct(item);
            adminScreen.setItems(deliveryService.getMenuItemsList());
        };

        this.deleteButtonActionListener = e -> {
            MenuItem item = adminScreen.getEditMenuItem();
            adminScreen.clearEditArea();
            deliveryService.deleteProduct(item);
            adminScreen.setItems(deliveryService.getMenuItemsList());
        };

        this.logoutButtonActionListener = e -> {
            System.out.println("Pressed!");
            displayLoginScreen();
            employeeScreen.setVisible(true);
        };

        this.registerNewUserActionListener = e -> {
            User user = registerScreen.getUser();
            userService.addUser(user);
            registerScreen.showRegistered();
            closeRegisterScreen();
        };

        this.closeButtonActionListener = e -> {
            System.out.println("Pressed!");
            if(registerScreen != null) {
                registerScreen.setVisible(false);
            }
            if(searchScreen != null) {
                searchScreen.setVisible(false);
            }
        };

        this.registerButtonActionListener = e -> {
            System.out.println("Pressed!");
            displayRegisterScreen();
        };

        this.adminTableItemClickListener = event -> {
            if(!event.getValueIsAdjusting()){
                String selectedItemName = null;
                JTable tableComponent = adminScreen.getTableComponent();
                int selectedRow = tableComponent.getSelectedRow();
                int selectedColumns = tableComponent.getSelectedColumn();

                for (int i = 0; i <= selectedRow; i++) {
                    for (int j = 0; j <= selectedColumns; j++) {
                        selectedItemName = (String) tableComponent.getValueAt(selectedRow, selectedColumns);
                    }
                }
                deliveryService.findProduct(selectedItemName).ifPresent(item ->{
                    adminScreen.setEditMenuItem(item);
                });
            }
        };

        this.clearButtonActionListener = e -> {
            System.out.println("Pressed!");
            clientScreen.clearOrder();
        };

        this.searchButtonActionLister = e -> {
            System.out.println("Pressed!");
            displaySearchScreen();
        };

        this.searchProductActionListener = e -> {
            System.out.println("Pressed!");
            searchScreen.displaySearchResult(deliveryService.getMenuItemsList());

        };

        this.clientTableItemClickListener = event -> {
            System.out.print("Client press");
            if(!event.getValueIsAdjusting()){
                String selectedItemName = null;
                JTable tableComponent = clientScreen.getTableComponent();
                int selectedRow = tableComponent.getSelectedRow();
                int selectedColumns = tableComponent.getSelectedColumn();

                for (int i = 0; i <= selectedRow; i++) {
                    for (int j = 0; j <= selectedColumns; j++) {
                        selectedItemName = (String) tableComponent.getValueAt(selectedRow, selectedColumns);
                    }
                }
                deliveryService.findProduct(selectedItemName).ifPresent(item ->{
                    clientScreen.addToOrderArea(item);
                });
            }
        };

        this.clientOrderListener = e -> {
            List<MenuItem> orderItems = this.clientScreen.getSelectedMenuItems().stream()
                    .map(productTitle -> deliveryService.findProduct(productTitle).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Order order = new Order();
            order.setItems(orderItems);
            order.setClientID(loggedInUser.getId());

            this.deliveryService.addOrder(order);
            deliveryService.generateBill(order);
            if(employeeScreen != null) {
                employeeScreen.addOrder(order);
            }
        };

        this.openRegisterScreenListener = e -> {
            String username = registerScreen.getUsername();
            String email = registerScreen.getEmail();
            String password = registerScreen.getPassword();

            registerScreen.setVisible(true);
        };

        this.loginListener = e -> {
            String username = loginScreen.getUsername();
            String password = loginScreen.getPassword();

            if(username.equals("") && password.equals("")) {
                loginScreen.showEmptyFields();
            }
            if(username.equals("admin") && password.equals("admin")){
                loginScreen.showSuccess();
                displayAdminScreen();
            }else if(username.equals("emp") && password.equals("emp")){
                loginScreen.showSuccess();

                displayEmployeeScreen();
            }else{
                userService.findByUsername(username).ifPresent(user ->{
                    if(user.getPassword().equals(password)){
                        loginScreen.showSuccess();
                        displayClientScreen(user);
                        this.loggedInUser = user;
                    }else{
                        loginScreen.showError();
                    }
                });
            }
        };

        windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                //                System.out.println("serializare");
                //                SerializerDelivery.serializeDelivery(deliveryService);
                //                SerializerUser.serializeUser(userService);
                //                SerializerUser.getInstance().closeStream();
                try {
                    ReportGenerator.generateProductMoreThanNTimes(deliveryService.getOrders(), 1);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
    }

    private void displayClientScreen(User loggedInUser) {
        clientScreen = new ClientGUI(clientTableItemClickListener, clientOrderListener, logoutButtonActionListener, clearButtonActionListener, searchButtonActionLister, loggedInUser);
        clientScreen.addDataToTable(deliveryService.getMenuItemsList());
        clientScreen.setVisible(true);
        loginScreen.setVisible(false);
    }

    private void displayEmployeeScreen() {
        employeeScreen = new EmployeeGUI(logoutButtonActionListener);
        employeeScreen.setVisible(true);
        //        loginScreen.setVisible(false);
    }

    public void displayLoginScreen() {
        loginScreen = new LoginGUI(loginListener, registerButtonActionListener, windowListener);
        loggedInUser = null;
        loginScreen.setVisible(true);
        if(adminScreen != null) {
            adminScreen.setVisible(false);
        }
        if(registerScreen != null) {
            registerScreen.setVisible(false);
        }
        if(clientScreen != null) {
            clientScreen.setVisible(false);
        }
        //        if(employeeScreen != null) {
        //            employeeScreen.setVisible(false);
        //        }
    }

    public void displayAdminScreen(){
        adminScreen = new AdministratorGUI(adminTableItemClickListener, addButtonActionListener, deleteButtonActionListener, logoutButtonActionListener);
        adminScreen.setItems(deliveryService.getMenuItemsList());
        adminScreen.setVisible(true);
        loginScreen.setVisible(false);
    }

    private void displayRegisterScreen() {
        registerScreen = new RegisterGUI(registerNewUserActionListener, closeButtonActionListener);
        registerScreen.setVisible(true);

    }

    private void displaySearchScreen() {
        searchScreen = new SearchGUI(searchProductActionListener, closeButtonActionListener);
        searchScreen.setVisible(true);
    }

    public void closeRegisterScreen() {
        registerScreen.setVisible(false);
    }

}

