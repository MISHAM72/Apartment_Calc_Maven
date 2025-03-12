import java.util.Scanner;

public class Calc {

    static Scanner sc = new Scanner(System.in);

    public static int num() {
        System.out.println("введите число: ");
        return sc.nextInt();

    }

    public static char operation() {
        System.out.println("введите знак операции:");
        while (!sc.hasNext("[+\\-*/0]")) {
            System.out.println("ошибка вы ввели неверный знак, попробуйте снова: ");
            sc.next();
        }
        return sc.next().charAt(0);

    }

    public static int calc(int num1, int num2, char operation) {
        int result;
        switch (operation) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> result = num1 / num2;
            default -> {
                System.out.println("вы ввели неверный знак, повторите снова.");
                result = calc(num1, num2, operation());
            }
        }
        return result;
    }

public static void main(String[] args) {

    int num1 = num();
    int num2 = num();
    char operation = operation();
    int result = calc(num1, num2, operation);
    System.out.println("result - " + result);

   }
}

