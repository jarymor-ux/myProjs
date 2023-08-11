import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private int transactionAttempts;
    private int successfulTrans;
    private int notEnoughMoneyTrans;
    private int blockedSusTrans;
    private int accsWasAlreadyBlockedTrans;
    private Map<String, Account> accounts;
    private final Random random = new Random();

    public int getTransactionAttempts() {
        return transactionAttempts;
    }

    public int getSuccessfulTrans() {
        return successfulTrans;
    }

    public int getNotEnoughMoneyTrans() {
        return notEnoughMoneyTrans;
    }

    public int getBlockedSusTrans() {
        return blockedSusTrans;
    }

    public int getAccsWasAlreadyBlockedTrans() {
        return accsWasAlreadyBlockedTrans;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Thread.sleep(300);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        synchronized ((fromAccountNum.compareTo(toAccountNum) > 0) ?
                accounts.get(fromAccountNum) : accounts.get(toAccountNum)) {

            synchronized ((fromAccountNum.compareTo(toAccountNum) < 0) ?
                    accounts.get(fromAccountNum) : accounts.get(toAccountNum)) {

                transactionAttempts++;

                Account accFrom = accounts.get(fromAccountNum);
                Account accTo = accounts.get(toAccountNum);

                StringBuilder report = new StringBuilder();

                if (!accountsAreAvailable(accFrom, accTo)) {
                    report.append("\ntransfer denied, because one or both accounts are blocked");
                    System.out.println(report);
                    accsWasAlreadyBlockedTrans++;
                    return;
                }

                if (!enoughMoney(accFrom, amount)) {
                    report.append("\ntransfer denied,\n" + "not enough money on account number ")
                            .append(fromAccountNum).append(", balance: ").append(accFrom.getMoney())
                            .append(", amount to transfer: ").append(amount);
                    System.out.println(report);
                    notEnoughMoneyTrans++;
                    return;
                }

                if (amount > 50_000) {
                    try {
                        if (isFraud(fromAccountNum, toAccountNum, amount)) {
                            report.append("\ntransfer denied, reason - suspicious transfer. accounts ")
                                    .append(fromAccountNum).append(" and ")
                                    .append(toAccountNum).append(" are blocked");
                            System.out.println(report);
                            blockAccounts(accFrom, accTo);
                            blockedSusTrans++;
                            return;
                        }
                        report.append("\nsuspicious transfer checked, transfer permitted");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                long bankTotal = this.getSumAllAccounts();
                report.append("\nbank balance before transaction = ").append(bankTotal).append("\naccFrom number: ")
                        .append(accFrom.getAccNumber()).append(", accTo number: ").append(accTo.getAccNumber())
                        .append("\naccFrom balance: ").append(accFrom.getMoney()).append(", accTo balance: ")
                        .append(accTo.getMoney()).append(", total: ").append(accFrom.getMoney() + accTo.getMoney());

                successfulTrans += 1;
                accFrom.withdraw(amount);
                accTo.topUp(amount);

                report.append("\ntransaction completed successfully, amount = ").append(amount)
                        .append("\naccFrom balance: ").append(accFrom.getMoney()).append(", accTo balance: ")
                        .append(accTo.getMoney()).append(", total: ").append(accFrom.getMoney() + accTo.getMoney());
                long bankTotal2 = this.getSumAllAccounts();
                report.append("\nbank balance after transaction = ").append(bankTotal2);
                System.out.println(report);
            }
        }
    }

    public long getSumAllAccounts() {
        AtomicLong sum = new AtomicLong();
        accounts.forEach((s, account) -> sum.set(sum.get() + account.getMoney()));
        return sum.get();
    }

    public String generateNumber() {
        String number = "";
        for (int i = 0; i < 10; i++) {
            number = number.concat(Integer.toString(random.nextInt(0, 9)));
        }
        if (accounts.containsKey(number)) {
            number = generateNumber();
        }
        return number;
    }

    private boolean enoughMoney(Account account, long amount) {
        return account.getMoney() >= amount;
    }

    public List<String> fillMapWithRandAcc(int amount, long minStartBalance, long maxStartBalance) {
        transactionAttempts = 0;
        successfulTrans = 0;
        if (accounts == null) {
            accounts = new HashMap<>();
        } else {
            accounts.clear();
        }
        for (int i = 0; i < amount; i++) {
            String number = generateNumber();
            long accBalance = random.nextLong(minStartBalance, maxStartBalance);
            accounts.put(number, new Account(accBalance, number));
        }
        return new ArrayList<>(accounts.keySet());
    }

    private void blockAccounts(Account accFrom, Account accTo) {
        accFrom.blockAccount();
        accTo.blockAccount();
    }

    private boolean accountsAreAvailable(Account accFrom, Account accTo) {
        return accFrom.isAvailable() && accTo.isAvailable();
    }
}
