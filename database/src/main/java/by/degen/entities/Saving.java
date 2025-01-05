package by.degen.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Saving {
    private Integer savingId;
    private BigDecimal amount;
    private final Currency currency;
    private BigDecimal interest;
    private Boolean deposit;
    private Boolean capitalization;

    public Saving(BigDecimal amount, Currency currency, BigDecimal interest, Boolean deposit, Boolean capitalization) {
        this.amount = amount;
        this.currency = currency;
        this.interest = interest;
        this.deposit = deposit;
        this.capitalization = capitalization;
    }

    public Integer getSavingId() {
        return savingId;
    }

    public void setSavingId(int savingId) {
        this.savingId = savingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public Boolean isCapitalization() {
        return capitalization;
    }

    public Boolean isDeposit() {
        return deposit;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public void setDeposit(Boolean deposit) {
        this.deposit = deposit;
    }

    public void setCapitalization(Boolean capitalization) {
        this.capitalization = capitalization;
    }

    public Currency getCurrency() {
        return currency;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Saving saving)) return false;
        return savingId == saving.savingId && Objects.equals(amount, saving.amount) && currency == saving.currency && Objects.equals(interest, saving.interest) && Objects.equals(deposit, saving.deposit) && Objects.equals(capitalization, saving.capitalization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(savingId, amount, currency, interest, deposit, capitalization);
    }

    @Override
    public String toString() {
        return "Saving{" +
                "savingId=" + savingId +
                ", amount=" + amount +
                ", currency=" + currency +
                ", interest=" + interest +
                ", deposit=" + deposit +
                ", capitalization=" + capitalization +
                '}';
    }
}
