public class TopManager implements Employee{
    double salary = (int) (Math.random() * (140_000 - 115_000) + 115_000);
    Company company;

    @Override
    public String toString() {
        return salary + " руб.";
    }

    @Override
    public double getMonthSalary() {
        return salary;
    }
    public TopManager(Company company) {
        if (company.getIncome() > 10_000_00){
            salary = salary + salary * 1.5;
        }
        this.company = company;
    }


}
