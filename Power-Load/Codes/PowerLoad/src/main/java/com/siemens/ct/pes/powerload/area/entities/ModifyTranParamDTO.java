/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.math.BigDecimal;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for modify transformer's increase parameter
 * 
 * @author Hao Liu
 *
 */
public class ModifyTranParamDTO {

    private int areaId;

    private int tranId;

    private BigDecimal increase;

    /**
     * Default constructor
     */
    public ModifyTranParamDTO() {
	areaId = CommonDefine.INIT_VALUE;
	tranId = CommonDefine.INIT_VALUE;
	increase = new BigDecimal(0);
    }

    public int getAreaId() {
	return areaId;
    }

    public void setAreaId(int areaId) {
	this.areaId = areaId;
    }

    public int getTranId() {
	return tranId;
    }

    public void setTranId(int tranId) {
	this.tranId = tranId;
    }

    public BigDecimal getIncrease() {
	return increase;
    }

    public void setIncrease(BigDecimal increase) {
	this.increase = increase;
    }
}