/**
 * DTO for line point
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.math.BigDecimal;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for line point
 * 
 * @author Hao Liu
 *
 */
public class LinePointDTO implements Comparable<LinePointDTO> {

    private long tv;

    private int index;

    private BigDecimal value;

    /**
     * Default constructor
     */
    public LinePointDTO() {
        tv = CommonDefine.INVALID_LONG_VALUE;
        index = CommonDefine.INIT_VALUE;
        value = new BigDecimal(0);
    }

    /**
     * @return the tv
     */
    public long getTv() {
        return tv;
    }

    /**
     * @param tv
     *            the tv to set
     */
    public void setTv(long tv) {
        this.tv = tv;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(LinePointDTO o) {
        if (index < o.getIndex()) {
            return -1;
        } else if (index > o.getIndex()) {
            return 1;
        } else {
            return 0;
        }
    }
}