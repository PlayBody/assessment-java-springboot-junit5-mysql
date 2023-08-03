package com.assessment.purchase.util;


public class RewardPointsCalculator {

    public int calculatePoints(int amount) {
        return Math.max(amount-50, 0) + Math.max(amount-100, 0);
    }
    
}
