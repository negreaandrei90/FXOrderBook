package com.profidata.command;

import com.profidata.command.types.CommandWithArguments;
import com.profidata.interview.order.order.Order;
import com.profidata.utility.service.HttpOrderService;
import com.profidata.utility.validators.NewCommandValidator;

import java.math.BigDecimal;

public class NewCommand implements CommandWithArguments {
    private static NewCommand INSTANCE;

    //SINGLETON
    private NewCommand() {}

    @Override
    public String getName() {
        return "new [buy|sell] <investment ccy> <counter ccy> <limit> <validity>";
    }

    @Override
    public String getDescription() {
        return "Creates new order in the order book and displays its ID";
    }

    @Override
    public void execute(String[] args) {
        NewCommandValidator validator = new NewCommandValidator();

        //if all validations pass
        if(validator.validateAll(args)) {
            HttpOrderService httpService = HttpOrderService.getInstance();
            Order newOrder = new Order(args[2], args[1].equals("buy"), args[3], new BigDecimal(args[4]), args[5]);
            newOrder = httpService.newOrder(newOrder);
            if(newOrder != null) {
                System.out.println("New order created with ID: " + newOrder.getId());
            } else {
                System.out.println("Order creation failed");
            }
        }
    }

    public static NewCommand getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NewCommand();
        }

        return INSTANCE;
    }


}
