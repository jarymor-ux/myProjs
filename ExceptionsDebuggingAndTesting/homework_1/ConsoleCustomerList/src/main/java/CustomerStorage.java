import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }
    private static final Logger LOGGER =
            LogManager.getLogger(CustomerStorage.class);

    public void addCustomer(String data) throws InputFormatException, NumberException, EmailException {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");
        if (components.length != 4) {
            LOGGER.log(Level.ERROR, "Неверный формат ввода данных - " + data);
            throw new InputFormatException("Не верный формат записи.\n" +
                    "С форматом записи можно ознакомиться введя \"help\".");
        }
        Pattern patternNumber = Pattern
                .compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{10}$");
        Matcher matcherNumber = patternNumber.matcher(components[INDEX_PHONE]);
        if (!matcherNumber.find()){
            LOGGER.log(Level.ERROR, "Не верный формам ввода номера телефона - "  + components[INDEX_PHONE]);
            throw new NumberException("Не верный формат ввода номера.\n" +
                    "Введите номер согласно формату - +70123456789");
        }
        Pattern patternEmail = Pattern.compile("(?i)([A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4})");
        Matcher matcherEmail = patternEmail.matcher(components[INDEX_EMAIL]);
        if (!matcherEmail.find()){
            LOGGER.log(Level.ERROR, "Не ерный формат ввода электроной почты - " + components[INDEX_EMAIL]);
            throw new EmailException("Не верный формат ввода электронной почты.\n" +
                    "Введите электронную почту согласно формату - SomeMail@gmail.com");
        }

        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}
