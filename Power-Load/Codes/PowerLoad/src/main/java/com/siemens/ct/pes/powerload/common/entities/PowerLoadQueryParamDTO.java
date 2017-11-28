/**
 * DTO for query power load
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for query power load
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadQueryParamDTO {

    private long startDate;

    private long endDate;

    private List<IDDTO> ids;

    /**
     * Default constructor
     */
    public PowerLoadQueryParamDTO() {
        startDate = CommonDefine.INVALID_LONG_VALUE;
        endDate = CommonDefine.INVALID_LONG_VALUE;
        ids = new ArrayList<IDDTO>();
    }

    /**
     * @return the startDate
     */
    public long getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public long getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the ids
     */
    public List<IDDTO> getIds() {
        return ids;
    }

    /**
     * @param ids
     *            the ids to set
     */
    public void setIds(List<IDDTO> ids) {
        this.ids = ids;
    }
}