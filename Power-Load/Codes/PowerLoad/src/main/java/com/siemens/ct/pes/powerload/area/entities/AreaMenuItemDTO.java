/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for area menu item
 * 
 * @author Hao Liu
 *
 */
public class AreaMenuItemDTO {

    private int areaId;

    private String areaName;

    private long tranCount;

    /**
     * Default constructor
     */
    public AreaMenuItemDTO() {
	areaId = CommonDefine.INIT_VALUE;
	areaName = CommonDefine.EMPTY;
	tranCount = CommonDefine.INIT_VALUE;
    }

    public int getAreaId() {
	return areaId;
    }

    public void setAreaId(int areaId) {
	this.areaId = areaId;
    }

    public String getAreaName() {
	return areaName;
    }

    public void setAreaName(String areaName) {
	this.areaName = areaName;
    }

    public long getTranCount() {
	return tranCount;
    }

    public void setTranCount(long tranCount) {
	this.tranCount = tranCount;
    }
}