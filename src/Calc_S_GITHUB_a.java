
import java.util.TreeMap;
import java.util.Scanner;
public class Calc_S_GITHUB_a {
            public static void main(String[] args) {
                  //2+3
                  //V-VII
                  Converter converter = new Converter();
                  String[] actions = {"+", "-", "/", "*"};
                  String[] regexActions = {"\\+", "-", "/", "\\*"};
                  Scanner scn = new Scanner(System.in);
                  System.out.print("Введите выражение в формате а+в: ");
                  String exp = scn.nextLine();
                  //Определяем арифметическое действие:
                  int actionIndex=-1;
                  for (int i = 0; i < actions.length; i++) {
                        if(exp.contains(actions[i])){
                              actionIndex = i;
                              break;
                        }
                  }
                  //Если не нашли арифметического действия, завершаем программу
                  if(actionIndex==-1){
                        System.out.println("Некорректное выражение");
                        return;
                  }
                  //"2-4".split("-")-> {"2", "4"}
                  String[] data = exp.split(regexActions[actionIndex]);
                  //Определяем, находятся ли числа в одном формате (оба римские или оба арабские)
                  if(converter.isRoman(data[0]) == converter.isRoman(data[1])){
                        int a,b;
                        
                        //конвертируем арабские числа из строки в число
                        a = Integer.parseInt(data[0]);
                        b = Integer.parseInt(data[1]);
                        
                        //выполняем с числами арифметическое действие
                        int result;
                        switch (actions[actionIndex]){
                              case "+":
                                    result = a+b;
                                    break;
                              case "-":
                                    result = a-b;
                                    break;
                              case "*":
                                    result = a*b;
                                    break;
                              default:
                                    result = a/b;
                                    break;
                        }
                        //если числа были арабские, возвращаем результат в арабском числе
                        System.out.println(result);
                  }else{
                        System.out.println("Числа должны быть в одном формате");
                  }
                  }
            }
    class Converter {
      TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
      
      public Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);
            romanKeyMap.put('D', 500);
            romanKeyMap.put('M', 1000);
      }
      
      public boolean isRoman(String number){
            //"V"->'V'
            return romanKeyMap.containsKey(number.charAt(0));
      }
      
}
