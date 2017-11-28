/**
 * 
 */
package com.siemens.ct.pes.powerload.vip.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * Industry cluster link entity
 * 
 * @author Hao Liu
 *
 */
public class IndustryClusterLink {

    private int source;

    private int target;

    /**
     * Default constructor
     */
    public IndustryClusterLink() {
        source = CommonDefine.INIT_VALUE;
        target = CommonDefine.INIT_VALUE;
    }

    /**
     * @return the source
     */
    public int getSource() {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * @return the target
     */
    public int getTarget() {
        return target;
    }

    /**
     * @param target
     *            the target to set
     */
    public void setTarget(int target) {
        this.target = target;
    }
}