package com.profidata.utility;

import com.profidata.interview.order.rate.FXRate;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

//was used to work directly on the "fxRates.ser", but is not necessary

public class RatesDeserializer {
    private static String PATH_NAME = "";   //place the complete path to "fxRates.ser"

    public static FXRate[] deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH_NAME))) {

            // Skip the first object (assumed to be LocalDate)
            Object first = ois.readObject();

            // Read the next object, expecting FXRate[]
            Object second = ois.readObject();
            if (second instanceof FXRate[]) {
                return (FXRate[]) second;
            } else {
                System.out.println("Expected FXRate[] but found: " + second.getClass());
                return new FXRate[0];
            }

        } catch (Exception e) {
            return new FXRate[0];
        }
    }
}
