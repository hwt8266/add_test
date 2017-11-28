/**
 * DTO for power load ratio
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load ratio
 * 
 * @author Hao Liu
 *
 */
public class DeptDTO {

    private int v_deptID;

    private String v_deptName;

    private List<DeptDTO> children;

    private List<DisItemDTO> items;

    public List<DisItemDTO> getItems() {
        return items;
    }

    public void setItems(List<DisItemDTO> items) {
        this.items = items;
    }

    public List<DeptDTO> getChildren() {
        return children;
    }

    public void setChildren(List<DeptDTO> children) {
        this.children = children;
    }

    public DeptDTO() {
        super();
        this.v_deptID = CommonDefine.INIT_VALUE;
        ;
        this.v_deptName = CommonDefine.EMPTY;
        this.children = new ArrayList<DeptDTO>();
        this.items = new ArrayList<DisItemDTO>();
    }

    public int getV_deptID() {
        return v_deptID;
    }

    public void setV_deptID(int v_deptID) {
        this.v_deptID = v_deptID;
    }

    public String getV_deptName() {
        return v_deptName;
    }

    public void setV_deptName(String v_deptName) {
        this.v_deptName = v_deptName;
    }

}