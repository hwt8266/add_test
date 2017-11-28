/**
 * 
 */
package com.siemens.ct.pes.powerload.area.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ct.pes.powerload.area.entities.Area;
import com.siemens.ct.pes.powerload.area.entities.AreaTran;
import com.siemens.ct.pes.powerload.area.entities.AreaTranPK;

/**
 * DAO for area functions
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class AreaDAO {

    @PersistenceContext(unitName = "siemens.ct.pes.powerload")
    private EntityManager em;

    /**
     * Query area by area id
     * 
     * @param areaId
     * @return
     */
    public Area queryAreaByID(final int areaId) {
        return em.createNamedQuery("Area.findAllByID", Area.class).setParameter("id", areaId).getSingleResult();
    }

    /**
     * Query area entities by filter criteria
     * 
     * @param areaName
     * @param remark
     * @param minIncrease
     * @param maxIncrease
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryAreas(final String areaName, final String remark, final BigDecimal minIncrease,
            final BigDecimal maxIncrease) {
        List<Object[]> rs = new ArrayList<Object[]>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM M_AREA ");

        if (areaName != null || remark != null || minIncrease != null || maxIncrease != null) {
            sql.append("WHERE ");

            if (areaName != null) {
                sql.append("name LIKE \"%").append(areaName).append("%\" ");

                if (remark != null || minIncrease != null || maxIncrease != null) {
                    sql.append("AND ");
                }
            }

            if (remark != null) {
                sql.append("remark LIKE \"%").append(remark).append("%\" ");

                if (minIncrease != null || maxIncrease != null) {
                    sql.append("AND ");
                }
            }

            if (minIncrease != null) {
                sql.append("increase >= ").append(minIncrease);

                if (maxIncrease != null) {
                    sql.append(" AND ");
                }
            }

            if (maxIncrease != null) {
                sql.append("increase <= ").append(maxIncrease);
            }
        }

        rs = em.createNativeQuery(sql.toString()).getResultList();

        return rs;
    }

    /**
     * Persist a area
     * 
     * @param a
     * @return
     */
    public boolean saveArea(final Area a) {
        boolean result = true;

        try {
            em.persist(a);
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * Delete area(s) by id(s)
     * 
     * @param ids
     * @return
     */
    public boolean deleteAreas(final List<Integer> ids) {
        if (ids.size() == 0) {
            return true;
        }

        StringBuffer sql = new StringBuffer();

        sql.append("delete from M_AREA where id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("in (").append(ids.get(0));

            for (int i = 1; i < ids.size(); i++) {
                sql.append(",").append(ids.get(i));
            }

            sql.append(")");
        }

        boolean result = true;

        try {
            em.createNativeQuery(sql.toString()).executeUpdate();
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * Query area menu items
     * 
     * @param areaName
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryAreaMenuItems(final String areaName) {
        List<Object[]> rs = new ArrayList<Object[]>();

        StringBuffer sql = new StringBuffer();
        sql.append("select area.id, area.name, area_count.tran_count from M_AREA area inner join ")
                .append("(select area_tran.area_id as area_id, count(area_tran.tran_id) as tran_count from M_AREA_TRAN area_tran group by area_tran.area_id) ")
                .append("as area_count on area.id = area_count.area_id");

        if (areaName != null) {
            sql.append("where area.name like \"%").append(areaName).append("%\"");
        }

        rs = em.createNativeQuery(sql.toString()).getResultList();

        return rs;
    }

    /**
     * Delete all transformers of areas
     * 
     * @param ids
     * @return
     */
    public boolean deleteAllTransOfAreas(final List<Integer> ids) {
        if (ids.size() == 0) {
            return true;
        }

        StringBuffer sql = new StringBuffer();

        sql.append("delete from M_AREA_TRAN where area_id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("in (").append(ids.get(0));

            for (int i = 1; i < ids.size(); i++) {
                sql.append(",").append(ids.get(i));
            }

            sql.append(")");
        }

        boolean result = true;

        try {
            em.createNativeQuery(sql.toString()).executeUpdate();
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * Query raw transformers data for areas
     * 
     * <li>0 - Transformer ID
     * <li>1 - Transformer name
     * <li>2 - Transformer type
     * <li>3 - Transformer capacity
     * <li>4 - V level
     * <li>5 - Increase
     * <li>6 - Low period
     * <li>7 - High period
     * <li>8 - Over period
     * <li>9 - Department name
     * 
     * @param areaId
     * @param tranName
     * @param tranType
     * @param vLevel
     * @param deptName
     * @param minCap
     * @param maxCap
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryTransInArea(final int areaId, final String tranName, final int tranType, int vLevel,
            final String deptName, final BigDecimal minCap, final BigDecimal maxCap, final int minLow, final int maxLow,
            final int minHigh, final int maxHigh, final int minOver, final int maxOver) {
        List<Object[]> rs = new ArrayList<Object[]>();

        StringBuffer sql = new StringBuffer();
        sql.append(
                "select ta.tran_id, t.descrb, tt.name, v.descrb, t.capacity, ta.increase, ta.low_min, ta.high_min, ta.over_min, d.name from M_AREA_TRAN ta ")
                .append("inner join M_TRANS t on ta.tran_id = t.id ")
                .append("inner join MD_VLEVEL v on t.vlevel = v.id ")
                .append("inner join MD_TRAN_TYPE tt on t.trans_type = tt.type ")
                .append("inner join M_DEPT d on t.dept_id = d.id where ta.area_id = ?areaId");

        if (tranName != null) {
            sql.append(" and t.descrb LIKE \"%").append(tranName).append("%\"");
        }

        if (tranType != 0) {
            sql.append(" and t.trans_type = ").append(tranType);
        }

        if (vLevel != 0) {
            sql.append(" and v.id = ").append(vLevel);
        }

        if (deptName != null) {
            sql.append(" and d.name LIKE \"%").append(deptName).append("%\"");
        }

        if (minCap != null) {
            sql.append(" and t.capacity >= ").append(minCap);
        }

        if (maxCap != null) {
            sql.append(" and t.capacity <= ").append(maxCap);
        }

        if (minLow != 0) {
            sql.append(" and ta.low_min >= ").append(minLow);
        }

        if (maxLow != 0) {
            sql.append(" and ta.low_min <= ").append(maxLow);
        }

        if (minHigh != 0) {
            sql.append(" and ta.high_min >= ").append(minHigh);
        }

        if (maxHigh != 0) {
            sql.append(" and ta.high_min <= ").append(maxHigh);
        }

        if (minOver != 0) {
            sql.append(" and ta.over_min >= ").append(minOver);
        }

        if (maxOver != 0) {
            sql.append(" and ta.over_min <= ").append(maxOver);
        }

        rs = em.createNativeQuery(sql.toString()).setParameter("areaId", areaId).getResultList();

        return rs;
    }

    /**
     * Query transformer in area by IDs
     * 
     * @param areaId
     * @param tranId
     * @return
     */
    public AreaTran queryTranInAreaByID(final int areaId, final int tranId) {
        return em.createNamedQuery("AreaTran.findByIDs", AreaTran.class).setParameter("areaId", areaId)
                .setParameter("tranId", tranId).getSingleResult();
    }

    /**
     * Persist changes of transformer's parameters
     * 
     * @param at
     * @return
     */
    public boolean saveTranInArea(final AreaTran at) {
        boolean result = true;

        try {
            em.persist(at);
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * Delete transformers from area
     * 
     * @param areaId
     * @param tranIds
     * @return
     */
    public boolean deleteTransFromArea(final int areaId, final List<Integer> tranIds) {
        if (tranIds.size() == 0) {
            return true;
        }

        StringBuffer sql = new StringBuffer();

        sql.append("delete from M_AREA_TRAN where area_id = ?areaId and tran_id ");

        if (tranIds.size() == 1) {
            sql.append("= ").append(tranIds.get(0));
        } else {
            sql.append("in (").append(tranIds.get(0));

            for (int i = 1; i < tranIds.size(); i++) {
                sql.append(",").append(tranIds.get(i));
            }

            sql.append(")");
        }

        boolean result = true;

        try {
            em.createNativeQuery(sql.toString()).setParameter("areaId", areaId).executeUpdate();
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * Query raw transformers data for areas
     * 
     * <li>1 - Transformer ID
     * <li>2 - Transformer name
     * <li>3 - Transformer type
     * <li>4 - Transformer capacity
     * <li>5 - V level
     * <li>6 - Department name
     * 
     * @param tranName
     * @param tranType
     * @param vLevel
     * @param deptName
     * @param minCap
     * @param maxCap
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryRawTrans(final String tranName, final int tranType, int vLevel, final String deptName,
            final BigDecimal minCap, final BigDecimal maxCap) {
        List<Object[]> rs = new ArrayList<Object[]>();

        StringBuffer sql = new StringBuffer();
        sql.append("select t.id, t.descrb, tt.name, t.capacity, v.descrb, d.name from M_TRANS t ")
                .append("inner join M_DEPT d on t.dept_id = d.id inner join MD_TRAN_TYPE tt on t.trans_type = tt.type ")
                .append("inner join MD_VLEVEL v on t.vlevel = v.id");

        if (tranName != null || tranType != 0 || vLevel != 0 || deptName != null || minCap != null || maxCap != null) {
            sql.append(" where ");

            if (tranName != null) {
                sql.append("t.descrb LIKE \"%").append(tranName).append("%\" ");

                if (tranType != 0 || vLevel != 0 || deptName != null || minCap != null || maxCap != null) {
                    sql.append("AND ");
                }
            }

            if (tranType != 0) {
                sql.append("t.trans_type = ").append(tranType);

                if (vLevel != 0 || deptName != null || minCap != null || maxCap != null) {
                    sql.append(" AND ");
                }
            }

            if (vLevel != 0) {
                sql.append("v.id = ").append(vLevel);

                if (deptName != null || minCap != null || maxCap != null) {
                    sql.append(" AND ");
                }
            }

            if (deptName != null) {
                sql.append("d.name LIKE \"%").append(deptName).append("%\" ");

                if (minCap != null || maxCap != null) {
                    sql.append("AND ");
                }
            }

            if (minCap != null) {
                sql.append("t.capacity >= ").append(minCap);

                if (maxCap != null) {
                    sql.append(" AND ");
                }
            }

            if (maxCap != null) {
                sql.append("t.capacity <= ").append(maxCap);
            }
        }

        rs = em.createNativeQuery(sql.toString()).getResultList();

        return rs;
    }

    /**
     * Add transformers into area
     * 
     * @param areaId
     * @param tranIds
     * @return
     */
    public boolean addTranToArea(final int areaId, final List<Integer> tranIds) {
        Area a = queryAreaByID(areaId);

        boolean result = false;

        if (a != null) {
            try {
                for (int tranId : tranIds) {
                    AreaTran at = new AreaTran();

                    AreaTranPK key = new AreaTranPK();
                    key.setAreaId(areaId);
                    key.setTranId(tranId);

                    at.setId(key);
                    at.setIncrease(a.getIncrease());

                    em.persist(at);
                }

                result = true;
            } catch (Exception e) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Query area transformer's increase
     * 
     * @param tranIds
     * @return
     */
    public List<AreaTran> queryAreaTrans(final List<Integer> tranIds) {
        if (tranIds.isEmpty()) {
            return new ArrayList<AreaTran>();
        }

        // StringBuffer sql = new StringBuffer();
        //
        // sql.append("select tran_id, increase from M_AREA_TRAN where tran_id
        // ");
        //
        // if (tranIds.size() == 1) {
        // sql.append("= ").append(tranIds.get(0));
        // } else {
        // sql.append("in (").append(tranIds.get(0));
        //
        // for (int i = 1; i < tranIds.size(); i++) {
        // sql.append(",").append(tranIds.get(i));
        // }
        //
        // sql.append(")");
        // }
        //
        // return em.createNativeQuery(sql.toString()).getResultList();

        if (tranIds.size() == 1) {
            return em.createNamedQuery("AreaTran.findByTranID", AreaTran.class).setParameter("tranId", tranIds.get(0))
                    .getResultList();
        } else {
            return em.createNamedQuery("AreaTran.findByTranIDs", AreaTran.class).setParameter("tranIds", tranIds)
                    .getResultList();
        }
    }

    /**
     * Query area transformer's attributes
     * 
     * <li>0 - Area ID
     * <li>1 - Area Name
     * <li>2 - Transformer ID
     * <li>3 - Transformer Name
     * <li>4 - Transformer Type
     * <li>5 - V Level
     * <li>6 - Capacity
     * <li>7 - Low_min
     * <li>8 - High_min
     * <li>9 - Over_min
     * <li>10 - Increase
     * 
     * @param areaIds
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryAreaTranDetail(final List<Integer> areaIds) {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT AREA.id, AREA.name, AREATRAN.tran_id, T.descrb, TY.NAME, V.descrb, T.capacity, AREATRAN.low_min, AREATRAN.high_min, AREATRAN.over_min, AREATRAN.increase FROM M_AREA AREA ")
                .append("INNER JOIN M_AREA_TRAN AREATRAN ON AREA.id = AREATRAN.area_id ")
                .append("INNER JOIN M_TRANS T ON AREATRAN.tran_id = T.id ")
                .append("INNER JOIN MD_VLEVEL V ON T.vlevel = V.id ")
                .append("INNER JOIN MD_TRAN_TYPE TY ON T.trans_type = TY.`type` ").append("WHERE AREA.id ");

        if (areaIds.size() == 1) {
            sql.append("= ").append(areaIds.get(0));
        } else {
            sql.append("IN (").append(areaIds.get(0));

            for (int i = 1; i < areaIds.size(); i++) {
                sql.append(",").append(areaIds.get(i));
            }

            sql.append(")");
        }

        return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Persist area transformers
     * 
     * @param areaTrans
     */
    public void saveAreaTran(final Collection<AreaTran> areaTrans) {
        for (AreaTran at : areaTrans) {
            StringBuffer sql = new StringBuffer();
            sql.append("update M_AREA_TRAN set increase = ").append(at.getIncrease()).append(", low_min = ")
                    .append(at.getLowMin()).append(", high_min = ").append(at.getHighMin()).append(", over_min = ")
                    .append(at.getOverMin()).append(" where tran_id = ").append(at.getId().getTranId());
            em.createNativeQuery(sql.toString()).executeUpdate();
        }
    }
}