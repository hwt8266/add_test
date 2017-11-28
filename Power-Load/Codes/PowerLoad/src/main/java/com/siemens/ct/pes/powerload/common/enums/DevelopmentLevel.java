/**
 * 
 */
package com.siemens.ct.pes.powerload.common.enums;

/**
 * Enum for development level
 * 
 * @author Hao Liu
 *
 */
public enum DevelopmentLevel {
    HIGH(1), MEDIUM(2), LOW(3);

    private int index;

    private DevelopmentLevel(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}