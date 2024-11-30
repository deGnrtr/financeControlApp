package by.degen.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Account {
    private Integer accountId;
    private String accountName;
    private User user;
    private Date lastSeen;
    private List<Item> incomes = new ArrayList<>();
    private List<Item> outcomes = new ArrayList<>();
    private Saving saving;
    private String note;

    public Account(String accountName, User user, Date lastSeen) {
        this.accountName = accountName;
        this.user = user;
        this.note = note;
    }

    public Account(String accountName, User user, Date lastSeen, List<Item> incomes, List<Item> outcomes, Saving saving, String note) {
        this.accountName = accountName;
        this.user = user;
        this.lastSeen = lastSeen;
        this.incomes = incomes;
        this.outcomes = outcomes;
        this.saving = saving;
        this.note = note;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return accountName;
    }

    public void setName(String account_name) {
        this.accountName = account_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public List<Item> getIncomes() {
        return incomes;
    }

    public void setIncomes(Item income) {
        this.incomes.add(income);
    }

    public List<Item> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Item outcomes) {
        this.outcomes.add(outcomes);
    }

    public Saving getSaving() {
        return saving;
    }

    public void setSaving(Saving saving) {
        this.saving = saving;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(accountName, account.accountName) && Objects.equals(lastSeen, account.lastSeen) && Objects.equals(incomes, account.incomes) && Objects.equals(outcomes, account.outcomes) && Objects.equals(saving, account.saving) && Objects.equals(note, account.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, lastSeen, incomes, outcomes, saving, note);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", user=" + user +
                ", lastSeen=" + lastSeen +
                ", incomes=" + incomes +
                ", outcomes=" + outcomes +
                ", saving=" + saving +
                ", note='" + note + '\'' +
                '}';
    }
}
