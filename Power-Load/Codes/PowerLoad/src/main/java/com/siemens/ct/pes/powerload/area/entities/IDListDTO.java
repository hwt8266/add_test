/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for ID list
 * 
 * @author Hao Liu
 *
 */
public class IDListDTO {

    private List<Integer> ids;

    /**
     * Default constructor
     */
    public IDListDTO() {
        ids = new ArrayList<Integer>();
    }

    /**
     * @return the ids
     */
    public List<Integer> getIds() {
        return ids;
    }

    /**
     * @param ids
     *            the ids to set
     */
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}