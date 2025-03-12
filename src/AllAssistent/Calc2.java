package AllAssistent;

import java.util.Scanner;

public class Calc2 {

    static Scanner sc = new Scanner(System.in);

    // Чтение и проверка числа
    public static int num() {
        System.out.println("Введите число: ");
        while (!sc.hasNextInt()) {
            System.out.println("Ошибка! Введите корректное число: ");
            sc.next(); // Пропустить некорректный ввод
        }
        return sc.nextInt();
    }

    // Чтение и проверка знака операции
    public static char operation() {
        System.out.println("Введите знак операции (+, -, *, /):");
        while (!sc.hasNext("[+\\-*/]")) { // Проверить ввод через регулярное выражение
            System.out.println("Ошибка! Введите корректный знак операции (+, -, *, /):");
            sc.next(); // Пропустить некорректный ввод
        }
        return sc.next().charAt(0); // Возвращает символ операции
    }

    // Выполнение операции
    public static int calc(int num1, int num2, char operation) {
        int result = 0;
        switch (operation) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> {
                if (num2 == 0) { // Проверка на деление на ноль
                    System.out.println("Ошибка! Деление на ноль. Попробуйте снова.");
                    result = calc(num1, num(), operation); // Повторный ввод второй переменной
                } else {
                    result = num1 / num2;
                }
            }
            default -> throw new IllegalArgumentException("Недопустимая операция"); // Защитный код (должно быть невозможно)
        }
        return result;
    }

    public static void main(String[] args) {
        int num1 = num(); // Ввод первого числа
        int num2 = num(); // Ввод второго числа
        char operation = operation(); // Ввод операции
        int result = calc(num1, num2, operation); // Выполнение операции
        System.out.println("Результат: " + result); // Вывод результата
    }
}
