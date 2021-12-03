package fb_recent;

public class AddStrings {
    public String addStrings(String num1, String num2) {
        int length = Math.max(num1.length(), num2.length());
        num1 = String.format("%" + length + "s", num1).replace(' ', '0');
        num2 = String.format("%" + length + "s", num2).replace(' ', '0');

        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int c1 = num1.charAt(i) - 48;
            int c2 = num2.charAt(i) - 48;
            int res = c1 + c2;
            res += carry;
            carry = res / 10;
            res = res % 10;
            result.append(res);
        }
        if (carry != 0)
            result.append(carry);
        return result.reverse().toString();
    }

    public static void main(String[] args){
        AddStrings addStrings = new AddStrings();
        System.out.println(addStrings.addStrings("123", "11"));
        System.out.println(addStrings.addStrings("1", "9"));
    }
}
