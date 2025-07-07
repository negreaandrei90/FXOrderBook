package com.profidata.command;

import com.profidata.command.types.CommandWithoutArguments;
import com.profidata.interview.order.order.Order;
import com.profidata.interview.order.rate.FXRate;
import com.profidata.utility.service.HttpOrderService;
import com.profidata.utility.service.HttpRateService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class OrdersCommand implements CommandWithoutArguments {
    private static OrdersCommand INSTANCE;

    //SINGLETON
    private OrdersCommand() {
    }

    public String getName() {
        return "orders";
    }

    public String getDescription() {
        return "Displays currently existing fx orders";
    }

    public void execute() {
        HttpOrderService httpOrderService = HttpOrderService.getInstance();
        HttpRateService httpRateService = HttpRateService.getInstance();

        //retrieve fx orders and rates
        Order[] orders = httpOrderService.retrieveOrders();
        FXRate[] fxRates = httpRateService.rateSnapshot();

        /*
        Converting orders to a list for accessing the sort method
        Converting fxRates to a map so one can find the matching pair of currencies with the order
         */
        List<Order> ordersList = Arrays.asList(orders);
        Map<String, FXRate> fxRateMap = Arrays.stream(fxRates)
                .collect(Collectors.toMap(
                        rate -> rate.getCcyPair().getCcy1() + "/" + rate.getCcyPair().getCcy2(),
                        rate -> rate
                )); //EUR/USD - key | 1.5 - value

        /*
        Sorting orders list based on:
        1. Investment Currency
        2. Counter Currency
        3. Distance between relevant rate (ask/bid) and limit
         */
        ordersList.sort(Comparator.comparing(Order::getInvestmentCcy).
                thenComparing(Order::getCounterCcy).
                thenComparing(order -> {
                    return getDifference(order, fxRateMap);
                }));

        System.out.printf("%-5s %-10s %-10s %-10s %-15s %-10s\n",
        "Buy", "InvestCcy", "CounterCcy", "Limit", "Validity Date", "Distance");
        System.out.println("-----------------------------------------------------------------");


        for (Order order : ordersList) {
            System.out.printf("%-5s %-10s %-10s %-10s %-15s %-10s\n",
                    order.isBuy() ? "buy" : "sell",
                    order.getInvestmentCcy(),
                    order.getCounterCcy(),
                    order.getLimit(),
                    order.getValidUntil(),
                    getDifference(order, fxRateMap));
        }
    }

    /*
    Form the key out of the order's investment and counter currency and find the key inside the map
    Identify relevant rate, return the difference between relevant rate and the order's limit
     */
    private BigDecimal getDifference(Order order, Map<String, FXRate> fxRateMap) {
        String pairKey = order.getInvestmentCcy() + "/" + order.getCounterCcy();
        FXRate rate = fxRateMap.get(pairKey);
        BigDecimal relevantRate;
        if(rate != null) {
            relevantRate = order.isBuy() ? rate.getAsk() : rate.getBid();    //select relevant rate: buy -> ask | sell -> bid
        } else {
            pairKey = order.getCounterCcy() + "/" + order.getInvestmentCcy();
            rate = fxRateMap.get(pairKey);
            relevantRate = order.isBuy() ? rate.getBid() : rate.getAsk();    //opposite since we swapped the values
        }
        return order.getLimit().subtract(relevantRate).abs();
    }

    public static OrdersCommand getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new OrdersCommand();
        }

        return INSTANCE;
    }
}
