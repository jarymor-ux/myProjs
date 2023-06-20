public class MainBasket {
    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Cola", 150);
        basket.add("Salt", 100,1,1.0);
        basket.add("Cucumber",30,5,0.5);
        basket.print("Корзина 1");
        System.out.println(basket.getTotalPrice());
        System.out.println(basket.getTotalWeight());
        System.out.println("-------------------------------------------------------------------------------");
        Basket basket1 = new Basket();
        basket1.add("Milk", 70);
        basket1.add("Sugar", 100,1,1.0);
        basket1.add("Orange",30,4,0.2);
        basket1.print("Корзина 2");
        System.out.println(basket1.getTotalPrice());
        System.out.println(basket1.getTotalWeight());
        System.out.println("-------------------------------------------------------------------------------");
        Basket basket2 = new Basket();
        basket2.add("Soda", 130);
        basket2.add("Tomato", 30,5,0.75);
        basket2.add("Juice",100,3,3.0);
        basket2.print("Корзина 3");
        System.out.println(basket2.getTotalPrice());
        System.out.println(basket2.getTotalWeight());
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(Basket.averageBasketPrice() + " Средняя стоимость корзины");
        System.out.println(Basket.averageTotalPrice() + " Средняя стоимость всех товаров во всех корзинах");

    }
}
