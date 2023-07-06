public interface Employee extends Comparable<Employee>{
    double getMonthSalary();

    @Override
    default int compareTo(Employee o){
        if (getMonthSalary() > o.getMonthSalary()){
            return 1;
        }
        if (getMonthSalary() < o.getMonthSalary()){
            return -1;
        }
        return 0;
    }
}
