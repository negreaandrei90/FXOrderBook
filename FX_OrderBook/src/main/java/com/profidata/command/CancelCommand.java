package com.profidata.command;

import com.profidata.command.types.CommandWithArguments;
import com.profidata.utility.service.HttpOrderService;
import com.profidata.utility.validators.CancelCommandValidator;

public class CancelCommand implements CommandWithArguments {
    private static CancelCommand INSTANCE;

    private CancelCommand() {

    }
    @Override
    public String getName() {
        return "cancel <ID>";
    }

    @Override
    public String getDescription() {
        return "Cancels an existing order with the given ID";
    }

    @Override
    public void execute(String[] args) {
        CancelCommandValidator validator = new CancelCommandValidator();
        String id = args[1];

        //if all validations pass
        if(validator.validateAll(args)) {
            HttpOrderService httpService = HttpOrderService.getInstance();
            String response = httpService.cancelOrder(id);
            if(response.equals("true")) {
                System.out.println("Order has been canceled successfully!");
            } else {
                System.out.println("Order with ID = " + id + " does not exist.");
            }
        }
    }

    public static CancelCommand getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CancelCommand();
        }

        return INSTANCE;
    }
}
