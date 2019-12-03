package com.es.phoneshop.DosCount;

import java.util.Date;

public class DosCount {
    private static int numberOfEntries;
    private static Date lastDate = new Date();


    public static int getNumberOfEntries() {
        return numberOfEntries;
    }

    public static Date getLastDate() {
        return lastDate;
    }

    public static void replaceDate(){
        if((new Date().getTime()-lastDate.getTime()) >60000) {
            lastDate = new Date();
            numberOfEntries=0;
        }
    }

    public static void count(){
        numberOfEntries++;
    }
}
