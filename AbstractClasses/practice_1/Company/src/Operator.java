public class Operator implements Employee{
    Company company;
    double salary = (int) (Math.random() * (140_000 - 115_000) + 115_000);
    @Override
    public double getMonthSalary() {
        return salary;
    }

    public Operator(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return salary + " руб.";
    }
}
