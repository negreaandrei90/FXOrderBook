package com.profidata.utility;

import com.profidata.interview.order.order.Order;

import java.io.*;

//was used to work directly on the "orders.ser", but is not necessary
public class OrderDeserializer {
    private static String PATH_NAME = "";   //place the complete path to "orders.ser"

    public static Order[] deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH_NAME))) {

            // Skip the first object (assumed to be LocalDate)
            Object first = ois.readObject();

            // Read the next object, expecting Order[]
            Object second = ois.readObject();
            if (second instanceof Order[]) {
                return (Order[]) second;
            } else {
                System.out.println("Expected Order[] but found: " + second.getClass());
                return new Order[0];
            }

        } catch (Exception e) {
            return new Order[0];
        }
    }
}
