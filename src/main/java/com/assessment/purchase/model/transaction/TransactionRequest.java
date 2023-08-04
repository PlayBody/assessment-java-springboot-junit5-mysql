package com.assessment.purchase.model.transaction;

public class TransactionRequest {
    private long customerId;
    private int purchasePrice;

    public TransactionRequest(long customerId, int purchasePrice) {
        this.customerId = customerId;
        this.purchasePrice = purchasePrice;
    }

    public long getCustomerId() {
        return customerId;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

}
