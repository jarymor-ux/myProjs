public class TopManager implements Employee{
    double salary = (int) (Math.random() * (140_000 - 115_000) + 115_000);
    Company company;

    @Override
    public String toString() {
        return salary + " руб.";
    }

    @Override
    public double getMonthSalary() {
        if (company.getIncome() > 10_000_000) {
            return (int) (salary + salary * 1.5);
        }
        return salary;
    }
    public TopManager(Company company) {
        this.company = company;
    }


}
