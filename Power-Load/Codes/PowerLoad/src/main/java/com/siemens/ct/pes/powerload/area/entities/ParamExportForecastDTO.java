/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for export area forecast parameter
 * 
 * @author Hao Liu
 *
 */
public class ParamExportForecastDTO {

    private List<Integer> areaIds;

    /**
     * Default constructor
     */
    public ParamExportForecastDTO() {
	areaIds = new ArrayList<Integer>();
    }

    /**
     * @return the areaIds
     */
    public List<Integer> getAreaIds() {
	return areaIds;
    }

    /**
     * @param areaIds
     *            the areaIds to set
     */
    public void setAreaIds(List<Integer> areaIds) {
	this.areaIds = areaIds;
    }
}