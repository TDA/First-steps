package Square;

// Coding: Feature flags are used to toggle functionality for users. For example:
//
//if (featureFlag.getValue(user)) {
//// feature is enabled, do something different
//} else {
//// feature is disabled for user, default functionality
//}
//
//The first question: getValue(userid), return bool. For the same user this function must return the same value every time
// Second question: Percentage Rollouts. Feature flags usually have the capability of percentage rollouts. For instance, let's say we want some new feature enabled for. 1% of users, then 10%, then 50%–oh no we have a bug! --Rollback to 0%.
// Question 3: Implement Multivariate Flags. Feature flags can also be used to drive non-boolean values. For example,
// string buttonColor = featureFlag.getValue(user)
// button.setColor(buttonColor)
//
//Non-boolean values mean that >2 variations are possible.
//Using the previous example, the flag could return "blue", "red", "yellow", or any string as the color.
//The last task is to implement multivariate flags that support N variations.
//Each variation should have an assigned rollout percentage.
//No longer returns bool, but returns a certain color according to probability. Depending on how you implement it, you may be asked if you can further optimize

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Queue;

public class FeatureFlags {
    private final Map<String, String> customerFlagMapping = new HashMap<>();
    private final Map<String, Queue<String>> treatmentCustomerMapping = new HashMap<>();
    private Map<String, Double> treatmentTargetPercentageMapping;

    String getTreatmentForUser(String customerId) {
        if (!customerFlagMapping.containsKey(customerId)) {
            // user checking first time, allocate them something deterministic that we can use later
            addToTreatment(customerId);
        }
        // user exists, return mapping
        return customerFlagMapping.get(customerId);
    }

    private void addToTreatment(String customerId) {
        // adding 1
        int totalCustomers = customerFlagMapping.keySet().size() + 1;
        for (Map.Entry<String, Double> entry : treatmentTargetPercentageMapping.entrySet()) {
            double target = getTarget(totalCustomers, entry.getValue());
            String treatment = entry.getKey();
            Queue<String> customersInTreatment = treatmentCustomerMapping.getOrDefault(treatment, new ArrayDeque<>());
            int current = customersInTreatment.size();

            if (current < target) {
                customerFlagMapping.put(customerId, treatment);
                customersInTreatment.add(customerId);
                treatmentCustomerMapping.put(treatment, customersInTreatment);
                break;
            }
        }
    }

    private void rebalanceTreatments(Queue<String> customersToAdd, int totalCustomers) {
        for (Map.Entry<String, Double> entry : treatmentTargetPercentageMapping.entrySet()) {
            double target = Math.floor(getTarget(totalCustomers, entry.getValue()));
            String treatment = entry.getKey();
            Queue<String> customersInTreatment = new ArrayDeque<>();
            int current = 0;

            while (current < target && !customersToAdd.isEmpty()) {
                String polledCustomer = customersToAdd.poll();
                customersInTreatment.add(polledCustomer);
                customerFlagMapping.put(polledCustomer, treatment);
                current++;
            }

            treatmentCustomerMapping.put(treatment, customersInTreatment);
        }
    }

    private double getTarget(int totalCustomers, Double percent) {
        return percent / 100 * totalCustomers;
    }

    boolean getValue(String customerId, String treatment) {
        String treatmentForUser = getTreatmentForUser(customerId);
        return treatmentForUser.equals(treatment);
    }

    boolean getValue(String customerId) {
        return getValue(customerId, "T1");
    }

    public void setTargetPercentageForTreatments(Map<String, Double> targetPercentageForTreatments) {
        double sumPercentages = 0;
        for (Map.Entry<String, Double> entry : targetPercentageForTreatments.entrySet()) {
            double target = entry.getValue();
            sumPercentages += target;
        }
        if (sumPercentages != 100.0) {
            throw new InputMismatchException("Cannot have sum of percentages > 100");
        }
        treatmentTargetPercentageMapping = new HashMap<>(targetPercentageForTreatments);

        rebalanceTreatments(new ArrayDeque<>(customerFlagMapping.keySet()), customerFlagMapping.keySet().size());
    }

    public static void main(String[] args){
        FeatureFlags featureFlags = new FeatureFlags();
        HashMap<String, Double> targetPercentages = new HashMap<>();
        targetPercentages.put("C", 50.0);
        targetPercentages.put("T1", 50.0);

        featureFlags.setTargetPercentageForTreatments(targetPercentages);
        System.out.println(featureFlags.getValue("Sai"));
        System.out.println(featureFlags.getValue("Li"));
        System.out.println(featureFlags.getValue("Adam"));
        System.out.println(featureFlags.getValue("Lucas"));
        System.out.println(featureFlags.getValue("Wen"));
        System.out.println(featureFlags.customerFlagMapping);

        targetPercentages.put("C", 33.33);
        targetPercentages.put("T1", 33.33);
        targetPercentages.put("T2", 33.34);
        featureFlags.setTargetPercentageForTreatments(targetPercentages);
        System.out.println(featureFlags.customerFlagMapping);
        System.out.println(featureFlags.getTreatmentForUser("Sai"));
        System.out.println(featureFlags.getTreatmentForUser("Li"));
        System.out.println(featureFlags.getTreatmentForUser("Adam"));
        System.out.println(featureFlags.getTreatmentForUser("Lucas"));
        System.out.println(featureFlags.getTreatmentForUser("Wen"));
        System.out.println(featureFlags.getTreatmentForUser("NewUser"));
        System.out.println(featureFlags.getTreatmentForUser("NewUser1"));
        System.out.println(featureFlags.getTreatmentForUser("NewUser2"));
        System.out.println(featureFlags.getTreatmentForUser("NewUser3"));

        targetPercentages.put("C", 25.0);
        targetPercentages.put("T1", 25.0);
        targetPercentages.put("T2", 25.0);
        targetPercentages.put("T3", 25.0);
        featureFlags.setTargetPercentageForTreatments(targetPercentages);
        System.out.println(featureFlags.customerFlagMapping);
        System.out.println(featureFlags.getValue("Sai", "C"));
        System.out.println(featureFlags.getValue("Sai", "T1"));
        System.out.println(featureFlags.getValue("Sai", "T2"));
        System.out.println(featureFlags.getValue("Sai", "T3"));
    }
}
