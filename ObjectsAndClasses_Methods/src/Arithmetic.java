public class Arithmetic {
    int number1;
    int number2;

    public Arithmetic(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public void sum(){
        System.out.println(number1 + " + " + number2 + " = " + (number1 + number2));
    }

    public void multiply(){
        System.out.println(number1 + " * " + number2 + " = " + (number1 * number2));
    }

    public void max(){
        if (number1 > number2) {
            System.out.println("Наибольшее из чисел: " + number1);
        }else {
            System.out.println("Наибольшее из чисел: " + number2);
        }
    }

    public void min(){
        if (number1 < number2) {
            System.out.println("Наименьшее из чисел: " + number1);
        }else {
            System.out.println("Наименьшее из чисел: " + number2);
        }
    }
}
