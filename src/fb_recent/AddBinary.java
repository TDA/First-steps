package fb_recent;

import java.util.Arrays;

public class AddBinary {
    public String addBinary(String a, String b) {
        System.out.println("---------------------------");
        int maxLength = Math.max(a.length(), b.length());
        a = String.format("%" + (maxLength + 1) + "s", a).replace(" ", "0");
        b = String.format("%" + (maxLength + 1) + "s", b).replace(" ", "0");
        System.out.println(a);
        System.out.println(b);
        char[] aArray = a.toCharArray();
        char[] bArray = b.toCharArray();
        int[] result = new int[aArray.length + 1];
        int carry = 0;
        for (int i = aArray.length - 1; i >= 0; i--) {
            int aNum = aArray[i] - '0';
            int bNum = bArray[i] - '0';
            int c = (aNum + bNum + carry);
            System.out.printf(" C %s and carry %s \n", c, carry);
            carry = c / 2;
            c = c % 2;
            result[i + 1] = c;
        }
        System.out.println(Arrays.toString(result));
        int index = 0;
        while (result[index] == 0) {
            index++;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < result.length; i++) {
            stringBuilder.append(result[i]);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args){
        AddBinary addBinary = new AddBinary();
        System.out.println(addBinary.addBinary("100", "11"));
        System.out.println(addBinary.addBinary("1", "11"));
    }
}
