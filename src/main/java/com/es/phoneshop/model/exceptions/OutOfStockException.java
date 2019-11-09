package com.es.phoneshop.model.exceptions;

public class OutOfStockException extends RuntimeException{
    private int validStock;

    public OutOfStockException(int validStock) {
        this.validStock = validStock;
    }

    public int getValidStock() {
        return validStock;
    }

}
