import java.util.Scanner;
public class IF {
      public static void main (String[] args) {
            System.out.println("введите число от 1-3");
            Scanner scanner = new Scanner ( System.in);
            int num = scanner.nextInt();
            
           
            if(num == 1){
                  System.out.println("вы ввели - 1");
            } else if(num == 2 ) {
                  System.out.println("вы ввели - 2");
            }else if(num == 3 ) {
                  System.out.println ("вы ввели - 3");
            }else {
                  System.out.println ( "вы ввели неверное число");
            }
      }
}
