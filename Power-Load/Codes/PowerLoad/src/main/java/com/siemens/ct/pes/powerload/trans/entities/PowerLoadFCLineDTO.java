/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.entities.LinePointDTO;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for a transformer power load forecast line
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadFCLineDTO {

    /**
     * Defined by {@link com.siemens.ct.pes.powerload.common.enums.DevelopmentLevel}
     */
    private int lineType;

    private List<LinePointDTO> points;

    /**
     * Default constructor
     */
    public PowerLoadFCLineDTO() {
        lineType = CommonDefine.INIT_VALUE;
        points = new ArrayList<LinePointDTO>();
    }

    /**
     * @return the lineType
     */
    public int getLineType() {
        return lineType;
    }

    /**
     * @param lineType
     *            the lineType to set
     */
    public void setLineType(int lineType) {
        this.lineType = lineType;
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