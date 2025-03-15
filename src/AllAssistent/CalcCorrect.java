package AllAssistent;

import java.util.Scanner;

public class CalcCorrect {
     static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int num1 = num();
        int num2 =  num();
        char oper = oper();
        int result = calc(num1, num2, oper);
        System.out.println("результат вашей операции: " + result);
    }
        public static int num (){
            System.out.println("введите любое число кроме 0.");
            while (!sc.hasNextInt()){
                System.out.println("ошибка при вводе, поворите.");
                sc.next();}
        return sc.nextInt();
        }
        public static char oper (){
            System.out.println("введите операцию(+,-,/,*): ");
        return sc.next().charAt(0);
        }
        public static int calc(int num1, int num2, char oper) {
            switch (oper) {
                case ('+'):
                    return num1 + num2;
                case ('-'):
                    return num1 - num2;
                case ('*'):
                    return num1 * num2;
                case ('/'):
                if (num2 == 0) {
                    throw new ArithmeticException
                            ("на 0 делить нельзя.");
                }
                return num1 / num2;
                default:
                    throw new
                            IllegalArgumentException
                            ("неверный оператор:" + oper);
            }
          }

        }

