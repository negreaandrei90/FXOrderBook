package com.profidata.interview.order.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

//FX Order
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -601508074670684575L;
    private String id;
    private String investmentCcy;
    private boolean buy;    //true - buy | false - sell
    private String counterCcy;
    private BigDecimal limit;
    private String validUntil;

    public Order(String investmentCcy, boolean buy, String counterCcy, BigDecimal limit, String validUntil) {
        this.investmentCcy = investmentCcy;
        this.buy = buy;
        this.counterCcy = counterCcy;
        this.limit = limit;
        this.validUntil = validUntil;
    }

    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.investmentCcy != null ? this.investmentCcy.hashCode() : 0);
        result = 31 * result + (this.buy ? 1 : 0);
        result = 31 * result + (this.counterCcy != null ? this.counterCcy.hashCode() : 0);
        result = 31 * result + (this.limit != null ? this.limit.hashCode() : 0);
        result = 31 * result + (this.validUntil != null ? this.validUntil.hashCode() : 0);
        return result;
    }

    public String toString() {
        String var10000 = this.id;
        return "Order{id='" + var10000 + "', investmentCcy='" + this.investmentCcy + "', buy=" + this.buy + ", counterCcy='" + this.counterCcy + "', limit=" + String.valueOf(this.limit) + ", validUntil='" + this.validUntil + "'}";
    }
}
