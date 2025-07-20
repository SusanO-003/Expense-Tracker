package expensetracker;
import java.io.*;
import java.util.*;
import java.time.LocalDate;


public class ExpenseTracker {
    private static final String FILE_PATH = "expenses.csv";
    private List<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenseToFile(expense);
    }
    
    public void deleteExpense(int index){
        if(index >= 0 && index < expenses.size()){
            expenses.remove(index);
            overwriteFile();
        }
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public double getTotalAmount() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
    
    public List<Expense> filterByCategory(String category){
        List<Expense> filtered = new ArrayList<>();
        for(Expense e : expenses){
            if(e.getCategory().equalsIgnoreCase(category)){
                filtered.add(e);
            }
        }
        return filtered;
    }

    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expenses.add(Expense.fromCSV(line));
            }
        } catch (IOException e) {
            // File might not exist yet
        }
    }

    private void saveExpenseToFile(Expense expense) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(expense.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void overwriteFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for(Expense e : expenses){
                writer.write(e.toCSV());
                writer.newLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }        
    }
}


 