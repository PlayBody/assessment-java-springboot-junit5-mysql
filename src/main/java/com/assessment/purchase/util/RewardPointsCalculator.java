package com.assessment.purchase.util;


public class RewardPointsCalculator {

    public int calculatePoints(int amount) {
        int points = 0;

        if(amount > 100){
            points += (int)((amount - 100) * 2);
        }
        if(amount > 50){
            points += (int)((amount - 50) * 1);
        }
        return points;
    }
    
}
