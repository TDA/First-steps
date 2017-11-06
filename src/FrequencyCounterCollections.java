package com.csai;

import java.util.Collections;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        HashMap<String, Boolean> casesToAction = new HashMap<>();
        casesToAction.put("a", true);
        casesToAction.put("b", true);
        casesToAction.put("c", false);
        casesToAction.put("d", true);
        casesToAction.put("e", false);

        int expectedSuccessCount = Collections.frequency(casesToAction.values(), true);
        System.out.println(expectedSuccessCount);
    }
}
