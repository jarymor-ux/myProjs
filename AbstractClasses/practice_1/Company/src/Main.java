import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Company company = new Company();
        for (int i = 0; i < 180; i++) {
            company.hire(new Operator(company));
        }
        for (int i = 0; i < 80; i++) {
            company.hire(new Manager(company));
        }
        for (int i = 0; i < 10; i++) {
            company.hire(new TopManager(company));
        }

        System.out.println(company.getTopSalaryStaff(13));
        System.out.println(company.getLowestSalaryStaff(30));

        List<Employee> employees = company.getEmployees();
        int size = employees.size();
        System.out.println(employees.size());
        for (int i = 0; i < size / 2; i++) {
            company.fire(employees.get(i));
        }

        System.out.println(company.getEmployees().size());

        System.out.println(company.getTopSalaryStaff(13));
        System.out.println(company.getLowestSalaryStaff(30));

        System.out.println(company);
    }
}
