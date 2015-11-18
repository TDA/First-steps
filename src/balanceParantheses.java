import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by schandramouli on 7/19/15.
 */
public class balanceParantheses {
    public static void main(String [] args){
        String s = "{as" +
                "<da>" +
                "{s}}";
        Boolean b = isBalancedParantheses(s);
        System.out.print("Parantheses was balanced " + b);
    }

    public static boolean isBalancedParantheses(String s){
        Map<Character,Character> hm =  new HashMap<Character, Character>();
        hm.put('{','}');
        hm.put('[',']');
        hm.put('(',')');
        hm.put('<','>');
        Boolean bool = false;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            System.out.println("New char " + c);
            if (c == '{' || c == '(' || c == '[' || c == '<') {
                System.out.println("Pushed " + c);
                stack.push(c);
            } else if (c == '}' || c == ')' || c == ']' || c == '>') {
                char x = stack.pop();
                System.out.println("Balanced numerator and c " + x + " " + c);
                if (hm.get(x) == c) {
                    bool = true;
                } else {
                    return false;
                }
            }
        }
        if(!stack.empty()){
            System.out.println("Stack not empty -- Not balanced");
            bool = false;
        }
        return bool;
    }
}
