/**
 * DAO for common module
 */
package com.siemens.ct.pes.powerload.common.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ct.pes.powerload.common.entities.Dept;
import com.siemens.ct.pes.powerload.common.entities.DisItem;
import com.siemens.ct.pes.powerload.common.entities.DisItemDTO;
import com.siemens.ct.pes.powerload.common.entities.DisItemPK;
import com.siemens.ct.pes.powerload.common.entities.TranType;
import com.siemens.ct.pes.powerload.common.entities.Transformer;
import com.siemens.ct.pes.powerload.common.entities.VIP;
import com.siemens.ct.pes.powerload.common.entities.VLevel;

/**
 * DAO for common module
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class CommonDAO {

    @PersistenceContext(unitName = "siemens.ct.pes.powerload")
    private EntityManager em;

    public List<Dept> getDeptListById(final List<Integer> pids) {
	List<Dept> result = em.createNamedQuery("Dept.findByPIDs", Dept.class).setParameter("pids", pids)
		.getResultList();
	return result;
    }

    public List<Dept> getDeptListByType(final int type) {
	List<Dept> result = em.createNamedQuery("Dept.findByType", Dept.class).setParameter("Type", type)
		.getResultList();
	return result;
    }

    /**
     * Query all departments
     * 
     * @return
     */
    public List<Dept> getAllDept() {
	return em.createNamedQuery("Dept.findAll", Dept.class).getResultList();
    }

    /**
     * Query all VIP
     * 
     * @return
     */
    public List<VIP> getAllVIP() {
	return em.createNamedQuery("VIP.findAll", VIP.class).getResultList();
    }

    /**
     * Query all transformers
     * 
     * @return
     */
    public List<Transformer> getAllTran() {
	return em.createNamedQuery("Transformer.findAll", Transformer.class).getResultList();
    }

    /**
     * Query private transformers
     * 
     * @return
     */
    public List<Transformer> getPrivateTran() {
	return em.createNamedQuery("Transformer.findByType", Transformer.class).setParameter("type", 2).getResultList();
    }

    /**
     * Query cached display transformers
     * 
     * @return
     */
    public List<DisItem> queryDisItems(final int type) {
	return em.createNamedQuery("DisItem.findByType", DisItem.class).setParameter("type", type).getResultList();
    }

    /**
     * Remove display items
     * 
     * @return Operation result
     */
    public boolean removeDisItems() {
	boolean result = true;

	try {
	    em.createNativeQuery("DELETE FROM APP_DISPLAY_ITEM").executeUpdate();
	} catch (Exception e) {
	    result = false;
	}

	return result;
    }

    /**
     * Insert transformer display items to cache
     * 
     * @param ids
     * @return
     */
    public boolean addDisItem(final List<DisItemDTO> items) {
	if (items.isEmpty()) {
	    return true;
	}

	boolean result = true;

	for (DisItemDTO dto : items) {
	    DisItemPK key = new DisItemPK();
	    key.setItemId(dto.getObjID());
	    key.setItemType(dto.getObjType());

	    DisItem item = new DisItem();
	    item.setId(key);
	    item.setItemName(dto.getObjName());

	    em.persist(item);
	}

	return result;
    }

    /**
     * Query all transformer types
     * 
     * @return
     */
    public List<TranType> queryAllTranType() {
	return em.createNamedQuery("TranType.findAll", TranType.class).getResultList();
    }

    /**
     * Query all v levels
     * 
     * @return
     */
    public List<VLevel> queryAllVLevel() {
	return em.createNamedQuery("VLevel.findAll", VLevel.class).getResultList();
    }
}