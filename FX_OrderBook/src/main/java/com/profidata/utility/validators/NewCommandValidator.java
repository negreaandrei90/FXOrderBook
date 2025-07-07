package com.profidata.utility.validators;

import com.profidata.interview.order.CurrencyPair;
import com.profidata.utility.service.HttpRateService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NewCommandValidator {
    private final int validArgumentsCount;

    public NewCommandValidator() {
        this.validArgumentsCount = 6;
    }

    public boolean validateAll(String[] args) {

        return validateArgumentsCount(args.length)
                && validateData(args)
                && validateCurrencyAvailable(args[2], args[3]);
    }

    //checks if command has correct number of arguments (all arguments required)
    private boolean validateArgumentsCount(int argumentsCount) {
        return argumentsCount == this.validArgumentsCount;
    }

    //checks if all data and data types match
    private boolean validateData(String[] args) {
        String ccyRegex = "[A-Z]{3}";

        //buy
        if (!args[1].equalsIgnoreCase("buy") && !args[1].equalsIgnoreCase("sell")) {
            System.out.println("Invalid argument: [buy|sell] must be 'buy' or 'sell'");
            return false;
        }

        //investmentCcy
        if(!args[2].matches(ccyRegex)) {
            System.out.println("Invalid argument: <investment ccy> must be 3 uppercase letters");
            return false;
        }

        //counterCcy
        if(!args[3].matches(ccyRegex)) {
            System.out.println("Invalid argument: <counter ccy> must be 3 uppercase letters");
            return false;
        }

        //limit
        try {
            BigDecimal limit = new BigDecimal(args[4]);
            if(limit.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument: <limit> must be a valid number");
            return false;
        }

        //validUntil
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate inputDate = LocalDate.parse(args[5], formatter); //can be cast to date
            //check if set before current date
            if(inputDate.isBefore(LocalDate.now())) {
                System.out.println("Invalid argument: <validity> must not be before current date");
                return false;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid argument: <validity> must be in format 'dd.mm.yyyy'");
            return false;
        }

        return true;
    }

    //checks to see if the currency pair is available inside "fxRates.ser" file
    private boolean validateCurrencyAvailable(String investmentCcy, String counterCcy) {
        HttpRateService service = HttpRateService.getInstance();
        CurrencyPair[] pairs = service.supportedCurrencyPairs();  //retrieve fx_rates from "fxRates.ser"

        //if any matching pair of currencies is found, condition is valid
        for(CurrencyPair pair : pairs) {
            if(pair.getCcy1().equals(investmentCcy) && pair.getCcy2().equals(counterCcy) ||
            pair.getCcy1().equals(counterCcy) && pair.getCcy2().equals(investmentCcy)) {
                return true;
            }
        }

        System.out.println("Invalid arguments: provided <investmentCcy> & <counterCcy> are not available");
        return false;
    }
}
