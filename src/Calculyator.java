

import java.util.Scanner;

public  class Calculyator {
    static Scanner sc = new Scanner(System.in);
//Метод int getInt() - должен считывать с консоли целое число и возвращать его
public static int getInt (){
    System.out.println("введите число" );
    int num = sc.nextInt();
return num;
}

// Метод char getOperation() - должен считывать с консоли какое-то значение и возвращать
   // символ с операцией (+, -, * или /)
    public static char getOperation(){
    System.out.println("придумайте операцию - ");
    char operation = sc.next().charAt(0);
        return operation;
}
//Метод int calc(int num1, int num2, char operation) - должен выполнять над числами num1 и num2
    // арифметическую операцию,
//        заданную operation.
public static int calc(int num1, int num2, char operation){
        int result;
        
    switch (operation) {
        case '+' -> result = num1 + num2;
        case '-' -> result = num1 - num2;
        case '*' -> result = num1 * num2;
        case '/' -> result = num1 / num2;
        default -> {
            System.out.println("операция не распознана ,повторите ввод");
            result = calc(num1, num2, operation);

        }
    }

        return result;

   }
    // Метод main() - должен считывать 2 числа (с помощью getInt()), считать операцию (с помощью getOperation(),
    //        передать все методу calc() и вывести на экран результат.
    public static void main(String[] args) {
        int num1 = getInt();
        int num2 = getInt();
        char operation = getOperation();
        int result = calc(num1,num2,operation);
        System.out.println("ваш результат- " + result);

    }

    }
