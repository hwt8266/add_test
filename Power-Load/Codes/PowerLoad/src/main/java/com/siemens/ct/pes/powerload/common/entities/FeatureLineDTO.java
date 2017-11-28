/**
 * DTO for feature
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for feature
 * 
 * @author Hao Liu
 *
 */
public class FeatureLineDTO implements Comparable<FeatureLineDTO> {

    private int index;

    private BigDecimal dynamicParam;

    private BigDecimal staticParam;

    private List<LinePointDTO> featureDataList;

    /**
     * Default constructor
     */
    public FeatureLineDTO() {
        index = CommonDefine.INIT_VALUE;
        dynamicParam = new BigDecimal(0);
        staticParam = new BigDecimal(0);
        featureDataList = new ArrayList<LinePointDTO>();
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
     * @return the dynamicParam
     */
    public BigDecimal getDynamicParam() {
        return dynamicParam;
    }

    /**
     * @param dynamicParam
     *            the dynamicParam to set
     */
    public void setDynamicParam(BigDecimal dynamicParam) {
        this.dynamicParam = dynamicParam;
    }

    /**
     * @return the staticParam
     */
    public BigDecimal getStaticParam() {
        return staticParam;
    }

    /**
     * @param staticParam
     *            the staticParam to set
     */
    public void setStaticParam(BigDecimal staticParam) {
        this.staticParam = staticParam;
    }

    /**
     * @return the featureDataList
     */
    public List<LinePointDTO> getFeatureDataList() {
        return featureDataList;
    }

    /**
     * @param featureDataList
     *            the featureDataList to set
     */
    public void setFeatureDataList(List<LinePointDTO> featureDataList) {
        this.featureDataList = featureDataList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(FeatureLineDTO o) {
        if (index < o.getIndex()) {
            return -1;
        } else if (index > o.getIndex()) {
            return 1;
        } else {
            return 0;
        }
    }
}