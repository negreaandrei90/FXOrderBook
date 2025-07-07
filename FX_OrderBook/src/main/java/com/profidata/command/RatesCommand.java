package com.profidata.command;

import com.profidata.command.types.CommandWithoutArguments;
import com.profidata.interview.order.rate.FXRate;
import com.profidata.utility.service.HttpRateService;

public class RatesCommand implements CommandWithoutArguments {
    private static RatesCommand INSTANCE;

    //SINGLETON
    private RatesCommand() {
    }

    public String getName() {
        return "rates";
    }

    public String getDescription() {
        return "Displays an overview of the current fx rates";
    }

    public void execute() {
        HttpRateService httpService = HttpRateService.getInstance();
        FXRate[] rates = httpService.rateSnapshot();
        if(rates.length == 0) {
            System.out.println("No available FX Rates.");
        } else {
            System.out.printf("%-10s %-10s %-10s %-10s\n",
            "InvestCcy", "CounterCcy", "Ask", "Bid");
            System.out.println("----------------------------------------------------");
            for (FXRate rate : rates) {
                System.out.printf("%-10s %-10s %-10s %-10s\n",
                        rate.getCcyPair().getCcy1(),
                        rate.getCcyPair().getCcy2(),
                        rate.getAsk(),
                        rate.getBid());
            }
        }
    }

    public static RatesCommand getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new RatesCommand();
        }

        return INSTANCE;
    }
}
