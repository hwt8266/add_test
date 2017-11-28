package com.siemens.ct.pes.powerload.trans.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load ratio statistic
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadRatioStatisticDTO {

    private int id;

    private int lowerThirty;

    private int thirtyToSixty;

    private int sixtyToNinety;

    private int greaterNinety;

    /**
     * Default constructor
     */
    public PowerLoadRatioStatisticDTO() {
        lowerThirty = CommonDefine.INIT_VALUE;
        thirtyToSixty = CommonDefine.INIT_VALUE;
        sixtyToNinety = CommonDefine.INIT_VALUE;
        greaterNinety = CommonDefine.INIT_VALUE;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the lowerThirty
     */
    public int getLowerThirty() {
        return lowerThirty;
    }

    /**
     * @param lowerThirty
     *            the lowerThirty to set
     */
    public void setLowerThirty(int lowerThirty) {
        this.lowerThirty = lowerThirty;
    }

    /**
     * @return the thirtyToSixty
     */
    public int getThirtyToSixty() {
        return thirtyToSixty;
    }

    /**
     * @param thirtyToSixty
     *            the thirtyToSixty to set
     */
    public void setThirtyToSixty(int thirtyToSixty) {
        this.thirtyToSixty = thirtyToSixty;
    }

    /**
     * @return the sixtyToNinety
     */
    public int getSixtyToNinety() {
        return sixtyToNinety;
    }

    /**
     * @param sixtyToNinety
     *            the sixtyToNinety to set
     */
    public void setSixtyToNinety(int sixtyToNinety) {
        this.sixtyToNinety = sixtyToNinety;
    }

    /**
     * @return the greaterNinety
     */
    public int getGreaterNinety() {
        return greaterNinety;
    }

    /**
     * @param greaterNinety
     *            the greaterNinety to set
     */
    public void setGreaterNinety(int greaterNinety) {
        this.greaterNinety = greaterNinety;
    }
}