/**
 * Controller for common module
 */
package com.siemens.ct.pes.powerload.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.siemens.ct.pes.powerload.common.dao.CommonDAO;
import com.siemens.ct.pes.powerload.common.entities.Dept;
import com.siemens.ct.pes.powerload.common.entities.DeptDTO;
import com.siemens.ct.pes.powerload.common.entities.DisItem;
import com.siemens.ct.pes.powerload.common.entities.DisItemDTO;
import com.siemens.ct.pes.powerload.common.entities.DisItemtParamDTO;
import com.siemens.ct.pes.powerload.common.entities.TranType;
import com.siemens.ct.pes.powerload.common.entities.Transformer;
import com.siemens.ct.pes.powerload.common.entities.VIP;
import com.siemens.ct.pes.powerload.common.entities.VLevel;
import com.siemens.ct.pes.powerload.common.enums.DeptType;
import com.siemens.ct.pes.powerload.common.enums.DisplayItemType;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * Controller for common module
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class CommonController {

    @EJB
    private CommonDAO dao;

    /**
     * Get cached display items by type
     * 
     * @param itemType
     * @return
     */
    public List<DisItemDTO> getDisplayItem(final int itemType) {
	List<DisItemDTO> result = new ArrayList<DisItemDTO>();

	List<DisItem> rs = dao.queryDisItems(itemType);

	for (DisItem item : rs) {
	    DisItemDTO dto = new DisItemDTO();
	    dto.setObjID(item.getId().getItemId());
	    dto.setObjName(item.getItemName());
	    dto.setObjType(item.getId().getItemType());

	    result.add(dto);
	}

	return result;
    }

    /**
     * Update display items cache
     * 
     * @param param
     * @return
     */
    public String updateDisplayItem(final List<DisItemDTO> param) {
	boolean r = true;

	r = dao.removeDisItems();

	r = r && dao.addDisItem(param);

	return r ? CommonDefine.RESULT_SUCCEED : CommonDefine.RESULT_FAILED;
    }

    /**
     * Construct left department tree
     * 
     * @param param
     * @return
     */
    public List<DeptDTO> constructDeptTree(DisItemtParamDTO param) {
	List<DeptDTO> results = new ArrayList<DeptDTO>();

	List<Dept> depts = dao.getAllDept();
	Map<Integer, List<Dept>> typeMap = new HashMap<Integer, List<Dept>>();

	for (Dept d : depts) {
	    List<Dept> lst = typeMap.get(d.getType());

	    if (null == lst) {
		lst = new ArrayList<Dept>();
		typeMap.put(d.getType(), lst);
	    }

	    lst.add(d);
	}

	Map<Integer, DeptDTO> deptDTOMap = new HashMap<Integer, DeptDTO>();

	// Top level
	for (Dept d : typeMap.get(DeptType.Bureau.getIndex())) {
	    DeptDTO dto = new DeptDTO();
	    dto.setV_deptID(d.getId());
	    dto.setV_deptName(d.getName());

	    results.add(dto);

	    deptDTOMap.put(d.getId(), dto);
	}

	// Construct children
	for (Dept d : typeMap.get(DeptType.Station.getIndex())) {
	    int parentID = d.getParentId();

	    DeptDTO dto = new DeptDTO();
	    dto.setV_deptID(d.getId());
	    dto.setV_deptName(d.getName());

	    deptDTOMap.put(d.getId(), dto);

	    // Add station to bureau
	    DeptDTO parentDTO = deptDTOMap.get(parentID);
	    parentDTO.getChildren().add(dto);
	}

	int displayItemType = param.getItemType();

	if (displayItemType == DisplayItemType.VIP.getIndex()) {
	    List<VIP> vips = dao.getAllVIP();

	    for (VIP v : vips) {
		DisItemDTO dto = new DisItemDTO();

		dto.setObjID(v.getId());
		dto.setObjName(v.getName());
		dto.setObjType(DisplayItemType.VIP.getIndex());

		DeptDTO deptDTO = deptDTOMap.get(v.getDeptId());

		deptDTO.getItems().add(dto);
	    }
	} else {
	    List<Transformer> trans = dao.getAllTran();

	    for (Transformer t : trans) {
		DisItemDTO dto = new DisItemDTO();

		dto.setObjID(t.getId());
		dto.setObjName(t.getDescrb());
		dto.setObjType(DisplayItemType.Transformer.getIndex());

		DeptDTO deptDTO = deptDTOMap.get(t.getDeptId());

		if (null != deptDTO) {
		    deptDTO.getItems().add(dto);
		}
	    }
	}

	return results;
    }

    /**
     * Get all transformer's types
     * 
     * @return
     */
    public List<TranType> getAllTranType() {
	return dao.queryAllTranType();
    }

    /**
     * Get all v level
     * 
     * @return
     */
    public List<VLevel> getAllVLevel() {
	return dao.queryAllVLevel();
    }
}