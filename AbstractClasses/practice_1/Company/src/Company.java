import java.util.*;

public class Company {
    private List<Employee> employees = new ArrayList<>();
    protected int income;
    public void hire(Employee employee){
        employees.add(employee);
    }

    public void hireAll(Collection<Employee> employees){
        for (Employee employee : employees){
            hire(employee);
        }
    }

    public void fire(Employee employee){
        employees.remove(employee);
    }

    public int getIncome(){
        for (Employee e : employees) {
            if (e instanceof Manager) {
                income += e.getMonthSalary();
            }
        }
        return income;
    }

    //TODO:2. Создайте два метода, возвращающие список указанной длины (count).
    // Они должны содержать сотрудников, отсортированных по убыванию и возрастанию заработной платы:
    // List<Employee> getTopSalaryStaff(int count),
    // List<Employee> getLowestSalaryStaff(int count).

    public List<Employee> getTopSalaryStaff(int count){
        List<Employee> e = employees;
        e.sort(Comparator.reverseOrder());
        List<Employee> e2 = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < count; i++) {
            e2.add(e.get(i));
        }
        e2.sort(Comparator.reverseOrder());
        return e2;
    }

    List<Employee> getLowestSalaryStaff(int count) {
        List<Employee> e = new ArrayList<>(employees);
        e.sort(Comparator.naturalOrder());
        List<Employee> e2 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            e2.add(e.get(i));
        }
        return e2;
    }

    public List<Employee> getEmployees() {
        return new ArrayList<Employee>(employees);
    }

    @Override
    public String toString() {
        return "Company{" +
                "employees=" + employees +
                '}';
    }
}
