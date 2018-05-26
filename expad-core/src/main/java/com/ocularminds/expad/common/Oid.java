/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocularminds.expad.common;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Identifier generates a random Id with varying sizes
 *
 * @author Festus B. Jejelowo
 */
public final class Oid {

    AtomicLong min;
    AtomicInteger sequence;
    public static Oid INSTANCE = new Oid();

    public enum Type {
        LONG, MIN, SHORT, MAX;
    }

    private Oid() {
         sequence = new AtomicInteger(1); 
         min = new AtomicLong(1);
    }

    private static class Instance {
        private static final Oid INSTANCE = new Oid();
    }

    public static Oid next() {
        return Instance.INSTANCE;
    }

    public String get(final Type type) throws Exception {
        String time = new StringBuilder(Long.toString(System.currentTimeMillis()))
                .reverse().toString();
        String threadId = String.format("%02d", Thread.currentThread().getId());
        String atomicId = String.format("%08d", sequence.getAndIncrement());
        String minId = String.format("%04d", min.getAndIncrement());
        String number;
        String seed = String.format("%02d", (int)(Math.random()*99));
        if (null == type) {
            return time.substring(0, 1).concat(threadId.substring(1,2))
                    .concat(seed).concat(atomicId.substring(4, 8));
        }
        switch (type) {
            case MAX:
                number = threadId.concat(time.substring(1, 4)).concat(seed).concat(atomicId);
                break;
            case LONG:                
                number = time.substring(0, 1).concat(threadId.substring(1,2));
                number = number.concat(seed).concat(atomicId.substring(4, 8));
                break;
            case MIN:
                number = time.substring(0, 1).concat(threadId.substring(1,2));
                number = number.concat(minId.substring(2, 4));
                break;
            default:
                number = threadId.concat(minId);
                break;
        }
        return number;
    }
}
