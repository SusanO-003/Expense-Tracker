package model;

import java.time.LocalDate;

public class Income {
    private int id;
    private int userId;
    private LocalDate date;
    private String source;
    private String category;
    private double amount;
    private String remarks;

    public Income() {}

    public Income(int id, int userId, LocalDate date, String source, String category, double amount, String remarks) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
