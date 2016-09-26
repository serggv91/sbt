/**
 * Created by Sergei on 23.09.2016.
 */
package ru.sberbank.school.task5;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinValidator {
    private final String pin;
    public PinValidator(Client client) {
        this.pin = client.pin;
    }

    public boolean run(String pin) {
        Pattern p = Pattern.compile(this.pin);
        Matcher m = p.matcher(pin);
        return m.matches();
    }
}
