package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Operation {

    private long operationId;
    private static long nextId=1;
    private long userId;
    private double operationSum;
    private String operationCurrency;
    private Map<LocalDate, LocalTime> operationDate;
    private OperationType type;// Возможно Enum
    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public static long getNextId() {
        return nextId;
    }

    public static void setNextId(long nextId) {
        Operation.nextId = nextId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getOperationSum() {
        return operationSum;
    }

    public void setOperationSum(double operationSum) {
        this.operationSum = operationSum;
    }

    public String getOperationCurrency() {
        return operationCurrency;
    }

    public void setOperationCurrency(String operationCurrency) {
        this.operationCurrency = operationCurrency;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }


    public Operation(long userId, String operationCurrency,double operationSum, OperationType type) {
        operationId = nextId++;
        this.userId = userId;
        this.operationSum = operationSum;
        this.operationCurrency = operationCurrency;
        operationDate = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        operationDate.put(localDate,localTime);
        this.type = type;
    }

    @Override
    public String toString() {
        return  type +
                "  " + operationSum +
                " " + operationCurrency + operationDate;
    }
}
