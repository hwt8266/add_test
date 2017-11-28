/**
 * Data transfer object for cached display items
 */
package com.siemens.ct.pes.powerload.common.entities;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * Data transfer object for cached display items
 * 
 * @author Hao Liu
 *
 */
public class DisItemDTO {

    private int objID;

    private String objName;

    private int objType;

    /**
     * Default constructor
     */
    public DisItemDTO() {
        objID = CommonDefine.INIT_VALUE;
        objName = CommonDefine.EMPTY;
    }

    /**
     * @return the objID
     */
    public int getObjID() {
        return objID;
    }

    /**
     * @param objID
     *            the objID to set
     */
    public void setObjID(int objID) {
        this.objID = objID;
    }

    /**
     * @return the objType
     */
    public int getObjType() {
        return objType;
    }

    /**
     * @param objType
     *            the objType to set
     */
    public void setObjType(int objType) {
        this.objType = objType;
    }

    /**
     * @return the objName
     */
    public String getObjName() {
        return objName;
    }

    /**
     * @param objName
     *            the objName to set
     */
    public void setObjName(String objName) {
        this.objName = objName;
    }
}