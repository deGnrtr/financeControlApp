package by.degen.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private Integer itemId;
    private String title;
    private BigDecimal amount;
    private final Currency currency;
    private TimePeriod period;
    private String icon;

    public Item(String title, BigDecimal amount, Currency currency, TimePeriod period, String icon) {
        this.title = title;
        this.amount = amount;
        this.currency = currency;
        this.period = period;
        this.icon = icon;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public TimePeriod getPeriod() {
        return period;
    }

    public void setPeriod(TimePeriod period) {
        this.period = period;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return itemId.equals(item.itemId) && Objects.equals(title, item.title) && Objects.equals(amount, item.amount) && currency == item.currency && period == item.period && Objects.equals(icon, item.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, title, amount, currency, period, icon);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", period=" + period +
                ", icon='" + icon + '\'' +
                '}';
    }
}
