public class ExcelSheetNumber {
    public int titleToNumber(String columnTitle) {
        // ASCII shortcut instead of hashmap
        int sum = 0;
        int multiplier = 1;
        for (int i = columnTitle.length() - 1; i >= 0; i--) {
            char c = columnTitle.charAt(i);
            int value = getAlphabetValue(c);
            sum += value * multiplier;
            multiplier *= 26;
        }
        return sum;
    }

    private int getAlphabetValue(char c) {
        return c - 64;
    }

    public static void main(String[] args) {
        System.out.println(new ExcelSheetNumber().titleToNumber("A"));
        System.out.println(new ExcelSheetNumber().titleToNumber("AB"));
        System.out.println(new ExcelSheetNumber().titleToNumber("ZY"));

    }
}
