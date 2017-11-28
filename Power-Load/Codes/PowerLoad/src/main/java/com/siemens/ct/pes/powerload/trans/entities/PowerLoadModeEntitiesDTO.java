/**
 * 
 */
package com.siemens.ct.pes.powerload.trans.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.entities.LinePointDTO;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load mode
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadModeEntitiesDTO {

    private int id;

    private String name;

    private List<LinePointDTO> maxRatio;

    private List<LinePointDTO> minRatio;

    private List<PowerLoadModeDTO> powerModes;

    /**
     * Default consturctor
     */
    public PowerLoadModeEntitiesDTO() {
        id = CommonDefine.INIT_VALUE;
        name = CommonDefine.EMPTY;
        maxRatio = new ArrayList<LinePointDTO>();
        minRatio = new ArrayList<LinePointDTO>();
        powerModes = new ArrayList<PowerLoadModeDTO>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the maxRatio
     */
    public List<LinePointDTO> getMaxRatio() {
        return maxRatio;
    }

    /**
     * @param maxRatio
     *            the maxRatio to set
     */
    public void setMaxRatio(List<LinePointDTO> maxRatio) {
        this.maxRatio = maxRatio;
    }

    /**
     * @return the minRatio
     */
    public List<LinePointDTO> getMinRatio() {
        return minRatio;
    }

    /**
     * @param minRatio
     *            the minRatio to set
     */
    public void setMinRatio(List<LinePointDTO> minRatio) {
        this.minRatio = minRatio;
    }

    /**
     * @return the powerModes
     */
    public List<PowerLoadModeDTO> getPowerModes() {
        return powerModes;
    }

    /**
     * @param powerModes
     *            the powerModes to set
     */
    public void setPowerModes(List<PowerLoadModeDTO> powerModes) {
        this.powerModes = powerModes;
    }
}