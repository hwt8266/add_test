/**
 * DTO for power load ratio
 */
package com.siemens.ct.pes.powerload.common.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load ratio
 * 
 * @author Hao Liu
 *
 */
public class DisItemtParamDTO {

    private int itemType;

    private int pid;
    
    public DisItemtParamDTO()
    {
    	this.itemType = CommonDefine.INIT_VALUE;
    	this.pid = CommonDefine.INIT_VALUE;
    }

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

    
   
}