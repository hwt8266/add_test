/**
 * Enum for department type
 */
package com.siemens.ct.pes.powerload.common.enums;

/**
 * Enum for department type
 * 
 * @author Hao Liu
 *
 */
public enum DeptType {
    Bureau(1), Station(2);

    private int index;

    private DeptType(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}