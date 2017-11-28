/**
 * DAO for VIP user power load module
 */
package com.siemens.ct.pes.powerload.vip.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ct.pes.powerload.common.entities.Industry;
import com.siemens.ct.pes.powerload.common.entities.IndustryClustered;
import com.siemens.ct.pes.powerload.common.entities.VIP;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DAO for VIP user power load module
 *
 * @author Hao Liu
 *
 */
@Stateless
public class VIPUserDAO {

    @PersistenceContext(unitName = "siemens.ct.pes.powerload")
    private EntityManager em;

    /**
     * Query VIP entities by id list
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<VIP> queryVIPByIDs(final List<Integer> ids) {
        List<VIP> rs = new ArrayList<VIP>();

        if (ids.isEmpty()) {
            return rs;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM M_VIP V WHERE V.id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0; i < ids.size(); i++) {
                sql.append(ids.get(i));

                if (i < ids.size() - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        rs = em.createNativeQuery(sql.toString(), VIP.class).getResultList();

        return rs;
    }

    /**
     * Query VIP basic information by transformer id list
     * 
     * <li>0-VIP ID
     * <li>1-VIP description
     * <li>2-VIP capacity
     * <li>3-Mease ID
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryVIPBasicInfo(final List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Object[]>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT V.id, V.name, V.capacity, M.id FROM M_VIP V LEFT JOIN M_MEASE M ON V.id = M.vip_id WHERE M.vip_id IS NOT NULL AND V.id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0, j = ids.size(); i < j; i++) {
                sql.append(ids.get(i));

                if (i < j - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query all VIP entities in DB
     * 
     * @return
     */
    public List<VIP> queryAllVIPEntities() {
        return em.createNamedQuery("VIP.findAll", VIP.class).getResultList();
    }

    /**
     * Query VIP user's history power load ratio, group by transformer id and order by collect date ascend
     * 
     * <li>0 - mease id
     * <li>1 - tv
     * <li>2 - PS
     * 
     * @param start
     * @param end
     * @param ids
     *            Mease ID list
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryVIPPowerLoadRatio(final long start, final long end, final List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Object[]>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT P.CJ_MP_ID, P.DATA_DATE, P.PZ FROM D_MP_YC_READ P WHERE P.CJ_MP_ID ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0, j = ids.size(); i < j; i++) {
                sql.append(ids.get(i));

                if (i < j - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        sql.append(" AND P.DATA_DATE BETWEEN ?start AND ?end ORDER BY P.DATA_DATE ASC");

        return em.createNativeQuery(sql.toString()).setParameter("start", start).setParameter("end", end)
                .getResultList();
    }

    /**
     * Query VIP user's feature data
     * 
     * <li>0-VIP id
     * <li>1-feature type
     * <li>2-feature index
     * <li>3-line index
     * <li>4-power value
     * <li>5-dynamic parameter
     * <li>6-static parameter
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryVIPPowerLoadFeature(final List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Object[]>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT F.user_id, F.type, F.feature_index, F.line_index, F.pv, F.dynamic_param, F.static_param FROM A_FEATURE F WHERE F.user_type = 1 AND F.user_id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0, j = ids.size(); i < j; i++) {
                sql.append(ids.get(i));

                if (i < j - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query VIP user's power load limit data(max and minimum)
     * 
     * <li>0 - VIP ID
     * <li>1 - Limit type: 1 - max, 2 - minimum
     * <li>2 - Index of point in line
     * <li>3 - Value of point in line
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryVIPPowerLoadLimit(final List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Object[]>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT PL.obj_id, PL.type, PL.line_index, PL.power_value FROM A_POWERLOAD_LIMIT PL WHERE PL.obj_type = 2 AND PL.obj_id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0, j = ids.size(); i < j; i++) {
                sql.append(ids.get(i));

                if (i < j - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query transformer's power load mode
     * 
     * <li>0 - VIP ID
     * <li>1 - Mode index
     * <li>2 - Index in line
     * <li>3 - Power load value
     * 
     * @param ids
     *            Transformer id list
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryVIPPowerLoadMode(final List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Object[]>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT F.user_id, F.feature_index, F.line_index, F.pv FROM A_FEATURE F WHERE F.type = 1 AND F.user_type = 1 AND F.user_id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0, j = ids.size(); i < j; i++) {
                sql.append(ids.get(i));

                if (i < j - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query VIP user power load environment impacts
     * 
     * <li>0 - VIP ID
     * <li>1 - VIP name
     * <li>2 - Rainfall
     * <li>3 - Temperature
     * <li>4 - Wind speed
     * <li>5 - Humidity
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryVIPPowerLoadEnvImpact(final List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return new ArrayList<Object[]>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT P.obj_id, T.descrb, P.rainfall, P.temperature, P.wind_speed, P.humidity FROM A_POWERFACTOR P LEFT JOIN M_VIP V ON P.obj_id = V.id WHERE P.obj_type = 2 AND P.obj_id ");

        if (ids.size() == 1) {
            sql.append("= ").append(ids.get(0));
        } else {
            sql.append("IN (");

            for (int i = 0, j = ids.size(); i < j; i++) {
                sql.append(ids.get(i));

                if (i < j - 1) {
                    sql.append(CommonDefine.COMMA);
                }
            }

            sql.append(")");
        }

        return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query all original industry entities
     * 
     * @return
     */
    public List<Industry> queryAllIndustry() {
        return em.createNamedQuery("Industry.findAll", Industry.class).getResultList();
    }

    /**
     * Query all clustered industry entites
     * 
     * @return
     */
    public List<IndustryClustered> queryAllClusteredIndustries() {
        return em.createNamedQuery("IndustryClustered.findAll", IndustryClustered.class).getResultList();
    }
}