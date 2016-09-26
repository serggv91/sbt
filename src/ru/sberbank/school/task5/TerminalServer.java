package ru.sberbank.school.task5;
import java.util.Random;

/**
 * Created by Sergei on 23.09.2016.
 */
public class TerminalServer {
    private final Client client;
    private final boolean condition;
    private Double withdrawCounter;
    private final static Double LIMIT = 60000.00;

    public TerminalServer(Client client) {
        this.withdrawCounter = 0.00;
        this.client = client;
        Random r = new Random();
        int temp = r.nextInt(100);
        if (temp < 10)
            this.condition = false;
        else
            this.condition = true;
    }

    String checkBalance() throws UnderMaintenanceException {
        if (condition != true)
            throw new UnderMaintenanceException("Server is under maintenance");
        return this.client.balance.toString();
    }

    public void withdraw(double sum) throws UnderMaintenanceException, LowBalanceException, ExceedLimitException{
        if (condition != true)
            throw new UnderMaintenanceException("Server is under maintenance");
        if (this.withdrawCounter + sum > LIMIT)
            throw new ExceedLimitException("Withdraw limit would be exceeded");
        if (this.client.balance - sum < 0)
            throw new LowBalanceException(this.client.balance.toString(), "Too low balance");
        this.client.balance -= sum;
    }

    public void deposit(double sum) throws UnderMaintenanceException{
        if (condition != true)
            throw new UnderMaintenanceException("Server is under maintenance");
        this.client.balance += sum;
    }

}

class LowBalanceException extends Exception{

    private String balance;
    public String getBalance(){return balance;}
    public LowBalanceException(String message, String balance){

        super(message);
        this.balance = balance;
    }
}

class UnderMaintenanceException extends Exception{

    public UnderMaintenanceException(String message){
        super(message);
    }
}

class ExceedLimitException extends Exception{

    public ExceedLimitException(String message){
        super(message);
    }
}