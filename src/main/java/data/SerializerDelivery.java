package data;

import business.DeliveryService;
import business.UserService;

import java.io.*;

public class SerializerDelivery {
    /**
     * @param deliveryService
     */
    public static void serializeDelivery(DeliveryService deliveryService) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("serializedDataDelivery.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(deliveryService);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Success");
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        catch (Exception e){
//            e.printStackTrace();
        }
    }

    public static DeliveryService deserializeDelivery(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            fileInputStream.toString();
            return (DeliveryService)objectInputStream.readObject();
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
