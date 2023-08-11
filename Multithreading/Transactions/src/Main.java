import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Bank bank = new Bank();
        ArrayList<String> accNumbers;
        accNumbers = new ArrayList<>(bank.fillMapWithRandAcc(50, 20_000, 70_000));
        int threads = 0;

        ArrayList<Thread> thr = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            String from = accNumbers.get(random.nextInt(0, accNumbers.size()));
            String to = accNumbers.get(random.nextInt(0, accNumbers.size()));
            if (Objects.equals(from, to)) {
                continue;
            }
            threads += 1;
            thr.add(new Thread(() -> bank.transfer(from, to, random.nextLong(200, 52_000))));
        }
        thr.get(0).start();
        for (int i = 1; i < thr.size(); i++) {
            try {
                thr.get(i - 1).join();
                thr.get(i).start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            thr.get(thr.size() - 1).join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Запущено потоков: " + threads);
        System.out.println("Попыток транзакций: " + bank.getTransactionAttempts());
        System.out.println("Успешных транзакций: " + bank.getSuccessfulTrans());
        System.out.println("Заблокировано подозрительных транзакций: " + bank.getBlockedSusTrans());
        System.out.println("Транзакций не удалось из-за блокировки аккаунтов: " + bank.getAccsWasAlreadyBlockedTrans());
        System.out.println("Транзакций не удалось из-за недостатка средств: " + bank.getNotEnoughMoneyTrans());
        System.out.println("Общее количество завершенных попыток: " + (bank.getSuccessfulTrans() + bank.getBlockedSusTrans()
                + bank.getAccsWasAlreadyBlockedTrans() + bank.getNotEnoughMoneyTrans()));
    }
}
