package bd.edu.seu;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String projectId;
    private String transactionType;
    private LocalDate date;

    public Transaction(String transactionId, String projectId, String transactionType, LocalDate date) {
        this.transactionId = transactionId;
        this.projectId = projectId;
        this.transactionType = transactionType;
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
