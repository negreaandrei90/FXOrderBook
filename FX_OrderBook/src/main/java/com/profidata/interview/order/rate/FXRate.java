package com.profidata.interview.order.rate;

import com.profidata.interview.order.CurrencyPair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FXRate implements Serializable {
    private static final long serialVersionUID = -8902083914558579382L;
    private CurrencyPair ccyPair;
    private BigDecimal bid;
    private BigDecimal ask;

    public int hashCode() {
        int result = this.ccyPair.hashCode();
        result = 31 * result + this.bid.hashCode();
        result = 31 * result + this.ask.hashCode();
        return result;
    }

    public String toString() {
        String var10000 = String.valueOf(this.ccyPair);
        return "FXRate{" + var10000 + " " + String.valueOf(this.bid) + " / " + String.valueOf(this.ask) + "}";
    }
}
