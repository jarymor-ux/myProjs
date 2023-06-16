public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40);
        basket.add("Sugar", 100,1,0.9);
        basket.add("Orange",20,4,0.2);
        basket.print("");
        System.out.println(basket.getTotalPrice());
        System.out.println(basket.getTotalWeight());

        Arithmetic arithmetic = new Arithmetic(5,17);
        arithmetic.sum();
        arithmetic.multiply();
        arithmetic.max();
        arithmetic.min();
    }
}
