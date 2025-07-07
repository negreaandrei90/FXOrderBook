package com.profidata.utility.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewCommandValidatorTest {
    private NewCommandValidator validator;

    @BeforeEach
    void setup() {
        validator = new NewCommandValidator();
    }

    @Test
    public void testValidAll(){
        String[] args = new String[]{"new", "buy", "USD", "EUR", "1.14", "20.12.2040"};
        boolean validation = validator.validateAll(args);

        assertTrue(validation);
    }

    @Test
    public void testNumberOfArgumentsNone(){
        String[] args = new String[0];
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testNumberOfArgumentsTooMany(){
        String[] args = new String[]{"new", "buy", "USD", "EUR", "1.14", "20.12.2040", "extra", "super extra"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testNumberOfArgumentsTooFew(){
        String[] args = new String[]{"new", "EUR", "1.14", "20.12.2040"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testDataWrongCurrenciesDataType() {
        String[] args = new String[]{"new", "3", "12", "1.14", "20.12.2040"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testDataWrongCurrenciesNotThreeLetters() {
        String[] args = new String[]{"new", "US", "EU", "1.14", "20.12.2040"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testDataWrongBuyType() {
        String[] args = new String[]{"new", "purchase", "USD", "EUR", "1.14", "20.12.2040"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testDataNegativeLimit() {
        String[] args = new String[]{"new", "buy", "USD", "EUR", "-1.14", "20.12.2040"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testDataWrongDateFormat() {
        String[] args = new String[]{"new", "buy", "USD", "EUR", "1.14", "12.20.2040"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testDataPastDate() {
        String[] args = new String[]{"new", "buy", "USD", "EUR", "1.14", "20.12.2004"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

}
