/**
 * 
 */
package com.siemens.ct.pes.powerload.area.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for paged table: area
 * 
 * @author Hao Liu
 *
 */
public class AreaPTableDTO {

    private int currentPage;

    private int totalPage;

    private List<Integer> allIds;

    private List<AreaDTO> rowData;

    /**
     * Default constructor
     */
    public AreaPTableDTO() {
	currentPage = CommonDefine.INIT_VALUE;
	totalPage = CommonDefine.INIT_VALUE;
	allIds = new ArrayList<Integer>();
	rowData = new ArrayList<AreaDTO>();
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
	return currentPage;
    }

    /**
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    /**
     * @return the totalPage
     */
    public int getTotalPage() {
	return totalPage;
    }

    /**
     * @param totalPage
     *            the totalPage to set
     */
    public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
    }

    /**
     * @return the allIds
     */
    public List<Integer> getAllIds() {
	return allIds;
    }

    /**
     * @param allIds
     *            the allIds to set
     */
    public void setAllIds(List<Integer> allIds) {
	this.allIds = allIds;
    }

    /**
     * @return the rowData
     */
    public List<AreaDTO> getRowData() {
	return rowData;
    }

    /**
     * @param rowData
     *            the rowData to set
     */
    public void setRowData(List<AreaDTO> rowData) {
	this.rowData = rowData;
    }
}