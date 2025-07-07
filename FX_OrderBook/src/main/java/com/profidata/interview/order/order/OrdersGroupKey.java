package com.profidata.interview.order.order;

public record OrdersGroupKey(String investmentCcy, String counterCcy, boolean buy)
    implements Comparable<OrdersGroupKey> {

    @Override
    public int compareTo(OrdersGroupKey o) {
        int comparison1 = this.investmentCcy.compareTo(o.investmentCcy);
        if(comparison1 != 0) {
            return comparison1;
        }

        int comparison2 = this.counterCcy.compareTo(o.counterCcy);
        if(comparison2 != 0) {
            return comparison2;
        }

        return Boolean.compare(this.buy, o.buy);
    }
}
