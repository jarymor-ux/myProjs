public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        container.addCount(2);
        System.out.println(container.getCount());

        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

        int startCode = 'А'; // Код буквы А
        int endCode = 'я';   // Код буквы я
        for (int code = startCode; code <= endCode; code++) {
            char letter = (char) code;
            System.out.println("Буква: " + letter + " Код: " + code);
            if (letter == 'е') {
                int yoCode = 'ё'; // Код буквы ё
                System.out.println("Буква: ё Код: " + yoCode);
            }
            if (letter == 'Е') {
                int yoCode = 'Ё'; // Код буквы Ё
                System.out.println("Буква: Ё Код: " + yoCode);
            }
        }
    }
}
