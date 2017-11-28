/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.entities.LinePointDTO;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power loader mode
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadModeDTO {

    private int modeID;

    private List<LinePointDTO> points;

    /**
     * Default constructor
     */
    public PowerLoadModeDTO() {
        modeID = CommonDefine.INIT_VALUE;
        points = new ArrayList<LinePointDTO>();
    }

    /**
     * @return the modeID
     */
    public int getModeID() {
        return modeID;
    }

    /**
     * @param modeID
     *            the modeID to set
     */
    public void setModeID(int modeID) {
        this.modeID = modeID;
    }

    /**
     * @return the points
     */
    public List<LinePointDTO> getPoints() {
        return points;
    }

    /**
     * @param points
     *            the points to set
     */
    public void setPoints(List<LinePointDTO> points) {
        this.points = points;
    }
}