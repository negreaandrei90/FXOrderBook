package com.profidata.command;

import com.profidata.command.types.CommandWithoutArguments;
import com.profidata.interview.order.order.Order;
import com.profidata.interview.order.order.OrdersGroupKey;
import com.profidata.utility.service.HttpOrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SummaryCommand implements CommandWithoutArguments {
    private static SummaryCommand INSTANCE;

    //SINGLETON
    private SummaryCommand() {
    }

    public String getName() {
        return "summary";
    }

    public String getDescription() {
        return "Displays overview of fx orders grouped";
    }

    public void execute() {
        HttpOrderService httpService = HttpOrderService.getInstance();
        Order[] orders = httpService.retrieveOrders();

        Map<OrdersGroupKey, List<Order>> groupedOrders = new TreeMap<>();
        //grouping orders on a map key made of necessary fields, ex: "EUR/USD/buy"
        groupedOrders = Arrays.stream(orders)
                .collect(Collectors.groupingBy(order ->
                        new OrdersGroupKey(order.getInvestmentCcy(),
                        order.getCounterCcy(),
                        order.isBuy()),
                        Collectors.toList()));

        System.out.printf("%-5s %-10s %-10s %-5s %-10s\n",
                "Buy", "InvestCcy", "CounterCcy", "Count", "Avg_Limit");
        System.out.println("-----------------------------------------------------");

        for(Map.Entry<OrdersGroupKey, List<Order>> entry : groupedOrders.entrySet()) {
            System.out.printf("%-5s %-10s %-10s %-5s %-10s\n",
                    entry.getKey().buy() ? "buy" : "sell",
                    entry.getKey().investmentCcy(),
                    entry.getKey().counterCcy(),
                    entry.getValue().size(),
                    getLimitAverage(entry.getValue()));
        }
    }

    private BigDecimal getLimitAverage(List<Order> orders) {
        int numberOfOrders = orders.size();

        if(numberOfOrders == 1) {
            return orders.getFirst().getLimit();
        }

        BigDecimal limitSum = new BigDecimal(0);
        for(Order order : orders) {
            limitSum = limitSum.add(order.getLimit());
        }

        return limitSum.divideToIntegralValue(new BigDecimal(numberOfOrders));
    }

    public static SummaryCommand getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SummaryCommand();
        }

        return INSTANCE;
    }
}
