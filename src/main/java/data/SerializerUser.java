package data;

import business.UserService;

import java.io.*;

public class SerializerUser {
    static FileInputStream fileInputStream;
    static FileOutputStream fileOutputStream;

    private static final SerializerUser instance = new SerializerUser();

    private SerializerUser() {
        try {
            fileInputStream = new FileInputStream("serializedDataUser.ser");
            fileOutputStream = new FileOutputStream("serializedDataUser.ser");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static SerializerUser getInstance() {
        return instance;
    }
    public static void serializeUser(UserService userService) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("serializedDataUser.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userService);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Success");
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public static UserService deserializeUser(String filePath) {
        try {
            fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            boolean running = true;
            while (running) {
                try {
                    return(UserService) objectInputStream.readObject();
                }
                catch (EOFException exc) {
                    running = false;
                }
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public void closeStream() {
        try {
            fileOutputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}
