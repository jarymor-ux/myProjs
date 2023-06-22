public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        container.addCount(2);
        System.out.println(container.getCount());

        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

        for (int i = 0; i <= 65535 ; i++) {
            if (i == 'ё' || i == 'Ё') {
                System.out.println("Код буквы: " + (char)i + " - " + i);
            } else if (i >= 'А' && i <= 'я') {
                System.out.println("Код буквы: " + (char)i + " - " + i);
            }
        }
    }
}
