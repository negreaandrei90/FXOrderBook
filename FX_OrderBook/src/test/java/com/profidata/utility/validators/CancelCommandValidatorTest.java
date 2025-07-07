package com.profidata.utility.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CancelCommandValidatorTest {
    private CancelCommandValidator validator;

    @BeforeEach
    void setup() {
        validator = new CancelCommandValidator();
    }

    @Test
    public void testNumberOfArguments(){
        String[] args = new String[]{"cancel", "3"};
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
    public void tetsNumberOfArgumentsTooMany(){
        String[] args = new String[]{"cancel", "3", "5"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testValidateDataDifferentDataType(){
        String[] args = new String[]{"cancel", "three"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testValidateDataZeroValue() {
        String[] args = new String[]{"cancel", "0"};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }

    @Test
    public void testValidateDataNull() {
        String[] args = new String[]{"cancel", null};
        boolean validation = validator.validateAll(args);

        assertFalse(validation);
    }


}
