package repository;

import model.Operation;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class OperationRepository {
    private ArrayList<Operation> operations;

    public OperationRepository() {
        this.operations = new ArrayList<>();
    }

    public void add (Operation operation){
        this.operations.add(operation);
    }

    public ArrayList<Operation> getListOperations(long id) {
        return operations.stream()
                .filter(operation -> operation.getUserId() == id)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Operation> getListOperationsByCurrency(String currency) {
        ArrayList<Operation> result = new ArrayList<>();
        for (int i = 0; i < operations.size(); i++) {
            if(operations.get(i).getOperationCurrency().equals(currency)){
                result.add(operations.get(i));
            }
        }
        return result;
    }
}
