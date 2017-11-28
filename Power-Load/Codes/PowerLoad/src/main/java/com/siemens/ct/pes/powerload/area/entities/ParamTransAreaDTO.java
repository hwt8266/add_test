/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for add transformers to area parameter
 * 
 * @author Hao Liu
 *
 */
public class ParamTransAreaDTO {

    private int areaId;

    private List<Integer> tranIds;

    /**
     * Default constructor
     */
    public ParamTransAreaDTO() {
        areaId = CommonDefine.INIT_VALUE;
        tranIds = new ArrayList<Integer>();
    }

    /**
     * @return the areaId
     */
    public int getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     *            the areaId to set
     */
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the tranIds
     */
    public List<Integer> getTranIds() {
        return tranIds;
    }

    /**
     * @param tranIds
     *            the tranIds to set
     */
    public void setTranIds(List<Integer> tranIds) {
        this.tranIds = tranIds;
    }
}