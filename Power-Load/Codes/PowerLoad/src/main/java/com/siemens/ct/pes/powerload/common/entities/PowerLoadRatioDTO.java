/**
 * DTO for power load ratio
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load ratio
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadRatioDTO {

    private int id;

    private String name;

    private BigDecimal capacity;

    private List<LinePointDTO> points;

    /**
     * Default constructor
     */
    public PowerLoadRatioDTO() {
        id = CommonDefine.INIT_VALUE;
        name = CommonDefine.EMPTY;
        capacity = new BigDecimal(0);
        points = new ArrayList<LinePointDTO>();
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
     * @return the capacity
     */
    public BigDecimal getCapacity() {
        return capacity;
    }

    /**
     * @param capacity
     *            the capacity to set
     */
    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
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