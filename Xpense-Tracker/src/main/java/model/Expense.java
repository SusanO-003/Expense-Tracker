package model;

import java.time.LocalDate;

public class Expense {
    private int id;
    private int userId;
    private LocalDate date;
    private String description;
    private String category;
    private double amount;
    private String remarks;

    public Expense() {}

    public Expense(int id, int userId, LocalDate date, String description, String category, double amount, String remarks) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
