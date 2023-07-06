public class Manager implements Employee{
    int salary = (int) (Math.random() * (140_000 - 115_000) + 115_000);
    Company company;
    @Override
    public double getMonthSalary() {
        return salary + (((double) salary /100) * 5);
    }

    public Manager(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return salary + " руб.";
    }
}
