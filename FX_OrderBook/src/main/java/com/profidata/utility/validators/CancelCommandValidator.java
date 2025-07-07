package com.profidata.utility.validators;

public class CancelCommandValidator {
    private final int validArgumentsCount;

    public CancelCommandValidator(){
        this.validArgumentsCount = 2;
    }

    public boolean validateAll(String[] args) {
        return validateArgumentsCount(args.length)
                && validateData(args[1]);
    }

    //checks if command has correct number of arguments (all arguments required)
    private boolean validateArgumentsCount(int argumentsCount) {
        return argumentsCount == this.validArgumentsCount;
    }

    //check if ID is of valid data type and > 0
    private boolean validateData(String id) {
        //id is a number
        try{
            int numericalId = Integer.parseInt(id);
            //id is positive and greater than 0
            if(numericalId < 1) {
                System.out.println("Invalid argument: <ID> must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument: <ID> must be a valid number");
            return false;
        }

        return true;
    }
}
