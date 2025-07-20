package expensetracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private String category;
    private double amount;
    private LocalDate date;
    

    public Expense(String category, double amount, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public String toCSV(){
        return category + "," + amount + "," + date.toString();
    }

    public static Expense fromCSV(String line) {
        String[] parts = line.split(",");
        return new Expense(parts[0], Double.parseDouble(parts[1]), LocalDate.parse(parts[2]));
    }
}
