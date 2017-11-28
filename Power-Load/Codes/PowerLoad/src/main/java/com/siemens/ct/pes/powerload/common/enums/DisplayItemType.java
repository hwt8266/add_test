/**
 * Enum for display item
 */
package com.siemens.ct.pes.powerload.common.enums;

/**
 * Enum for display item
 * 
 * @author Hao Liu
 *
 */
public enum DisplayItemType {
    VIP(2), Transformer(1);

    private int index;

    private DisplayItemType(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
