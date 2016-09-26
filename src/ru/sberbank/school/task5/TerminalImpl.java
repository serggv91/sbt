/**
 * Created by Sergei on 23.09.2016.
 */
package ru.sberbank.school.task5;
public class TerminalImpl {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    public TerminalImpl(Client client){
        server = new TerminalServer(client);
        pinValidator = new PinValidator(client);
    }

    public String checkBalance(String pin) {
        boolean pinResult = pinValidator.run(pin);
        String result = null;
        if (pinResult != true)
            return "Incorrect pin";
        try {
            result = server.checkBalance();
        } catch (UnderMaintenanceException e) {
            result = e.getMessage();
        }

        return result;
    }

    public String withdraw(String pin, Double sum) {
        boolean pinResult = pinValidator.run(pin);
        String result = null;
        if (pinResult != true)
            return"Incorrect pin";
        try {
            server.withdraw(sum);
            result = "Done";
        } catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    public String deposit(String pin, Double sum) {
        boolean pinResult = pinValidator.run(pin);
        String result = null;
        if (pinResult != true)
            return "Incorrect pin";
        try {
            server.deposit(sum);
            result = "Done";
        } catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    public static void main(String[] args) {
        Client client = new Client("A", "1234");
        TerminalImpl ti = new TerminalImpl(client);
        System.out.println(ti.checkBalance("1234"));
        System.out.println(ti.deposit("1534", 100000.00));
    }

}
