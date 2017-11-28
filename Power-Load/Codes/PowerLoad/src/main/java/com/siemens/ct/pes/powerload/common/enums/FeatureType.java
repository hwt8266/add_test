/**
 * 
 */
package com.siemens.ct.pes.powerload.common.enums;

/**
 * Enum for feature type
 * 
 * @author Hao Liu
 *
 */
public enum FeatureType {
    DAY(1), MONTH(2), QUARTER(3);

    private int index;

    private FeatureType(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}