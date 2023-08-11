public class Account {

    private long money;
    private final String accNumber;
    private boolean isBlocked;

    public String getAccNumber() {
        return accNumber;
    }

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = false;
    }

    public long getMoney() {
        return money;
    }

    public void topUp(long amount) {
        money += amount;
    }

    public void withdraw(long amount) {
        money -= amount;
    }

    public void blockAccount(){
        isBlocked = true;
    }
    public boolean isAvailable(){
        return !isBlocked;
    }
}
