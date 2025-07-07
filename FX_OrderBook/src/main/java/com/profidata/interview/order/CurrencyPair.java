package com.profidata.interview.order;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CurrencyPair implements Serializable {
    private static final long serialVersionUID = -7642266276518954396L;
    private String ccy1;
    private String ccy2;

    public String toString() {
        return "{" + this.ccy1 + this.ccy2 + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            CurrencyPair that = (CurrencyPair)o;
            return !this.ccy1.equals(that.ccy1) ? false : this.ccy2.equals(that.ccy2);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.ccy1.hashCode();
        result = 31 * result + this.ccy2.hashCode();
        return result;
    }
}
