/**
 * DAO for transformer power load module
 */
package com.siemens.ct.pes.powerload.trans.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ct.pes.powerload.common.entities.IndustryClustered;
import com.siemens.ct.pes.powerload.common.entities.Mease;
import com.siemens.ct.pes.powerload.common.entities.Transformer;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;
import com.siemens.ct.pes.powerload.trans.entities.IndustryParamDTO;
import com.siemens.ct.pes.powerload.trans.entities.TransIndustryMatrix;
import com.siemens.ct.pes.powerload.trans.entities.TransIndustryParameter;

/**
 * DAO for transformer power load module
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class TransDAO {

    @PersistenceContext(unitName = "siemens.ct.pes.powerload")
    private EntityManager em;

    /**
     * Query transformer entities by id list
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Transformer> queryTransByIDs(final List<Integer> ids) {
	List<Transformer> rs = new ArrayList<Transformer>();

	if (ids.isEmpty()) {
	    return rs;
	}

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT * FROM M_TRANS T WHERE T.id ");

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

	rs = em.createNativeQuery(sql.toString(), Transformer.class).getResultList();

	return rs;
    }

    /**
     * Query mease entities by transformer id list
     * 
     * @param ids
     * @return
     */
    public List<Mease> queryMeaseByTransIDs(final List<Integer> ids) {
	return em.createNamedQuery("Mease.findByTransID", Mease.class).setParameter("transids", ids).getResultList();
    }

    /**
     * Query transformer basic information by transformer id list
     * 
     * <li>0-Transformer ID
     * <li>1-Transformer description
     * <li>2-Transformer capacity
     * <li>3-Mease ID
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryTransBasicInfo(final List<Integer> ids) {
	if (null == ids || ids.isEmpty()) {
	    return new ArrayList<Object[]>();
	}

	StringBuffer sql = new StringBuffer();
	sql.append(
		"SELECT T.id, T.descrb, T.capacity, M.id FROM M_TRANS T LEFT JOIN M_MEASE M ON T.id = M.trans_id WHERE M.trans_id IS NOT NULL AND T.id ");

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
     * Query transformer's history power load ratio, group by transformer id and
     * order by collect date ascend
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
    public List<Object[]> queryTransPowerLoadRatio(final long start, final long end, final List<Integer> ids) {
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
     * Query transformer's feature data
     * 
     * <li>0-transformer id
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
    public List<Object[]> queryTransPowerLoadFeature(final List<Integer> ids) {
	if (null == ids || ids.isEmpty()) {
	    return new ArrayList<Object[]>();
	}

	StringBuffer sql = new StringBuffer();
	sql.append(
		"SELECT F.user_id, F.type, F.feature_index, F.line_index, F.pv, F.dynamic_param, F.static_param FROM A_FEATURE F WHERE F.user_type = 3 AND F.user_id ");

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
     * <li>0 - Transformer ID
     * <li>1 - Mode index
     * <li>2 - Index in line
     * <li>3 - Power load value
     * 
     * @param ids
     *            Transformer id list
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryTransPowerLoadMode(final List<Integer> ids) {
	if (null == ids || ids.isEmpty()) {
	    return new ArrayList<Object[]>();
	}

	StringBuffer sql = new StringBuffer();
	sql.append(
		"SELECT F.user_id, F.feature_index, F.line_index, F.pv FROM A_FEATURE F WHERE F.type = 1 AND F.user_type = 3 AND F.user_id ");

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
     * Query transformer's power load limit data(max and minimum)
     * 
     * <li>0 - Transformer ID
     * <li>1 - Limit type: 1 - max, 2 - minimum
     * <li>2 - Index of point in line
     * <li>3 - Value of point in line
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryTransPowerLoadLimit(final List<Integer> ids) {
	if (null == ids || ids.isEmpty()) {
	    return new ArrayList<Object[]>();
	}

	StringBuffer sql = new StringBuffer();
	sql.append(
		"SELECT PL.obj_id, PL.type, PL.line_index, PL.power_value FROM A_POWERLOAD_LIMIT PL WHERE PL.obj_type = 1 AND PL.obj_id ");

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
     * Query transformer's power load environment impacts
     * 
     * <li>0 - Transformer ID
     * <li>1 - Transformer name
     * <li>2 - Rainfall
     * <li>3 - Temperature
     * <li>4 - Wind speed
     * <li>5 - Humidity
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryTransPowerLoadEnvImpact(final List<Integer> ids) {
	if (null == ids || ids.isEmpty()) {
	    return new ArrayList<Object[]>();
	}

	StringBuffer sql = new StringBuffer();
	sql.append(
		"SELECT P.obj_id, T.descrb, P.rainfall, P.temperature, P.wind_speed, P.humidity FROM A_POWERFACTOR P LEFT JOIN M_TRANS T ON P.obj_id = T.id WHERE P.obj_type = 1 AND P.obj_id ");

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
     * Query transformer's power load environment impacts
     * 
     * <li>0 - Transformer ID
     * <li>1 - Transformer name
     * <li>2 - VIP ID
     * <li>3 - VIP industry
     * <li>4 - Industry name
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryTransVIPIndustryInfo(final List<Integer> ids) {
	List<Object[]> result = new ArrayList<Object[]>();

	if (null == ids || ids.isEmpty()) {
	    return result;
	}

	// VIP industry
	StringBuffer sqlVIP = new StringBuffer();
	sqlVIP.append(
		"SELECT DISTINCT(M.trans_id), T.descrb, M.vip_id, I.id, I.name FROM M_MEASE M LEFT JOIN M_TRANS T ON M.trans_id = T.id LEFT JOIN M_VIP V ON M.vip_id = V.id LEFT JOIN A_INDUSTRY I ON V.industry_new = I.id WHERE M.vip_id != -1 AND M.trans_id ");

	if (ids.size() == 1) {
	    sqlVIP.append("= ").append(ids.get(0));
	} else {
	    sqlVIP.append("IN (");

	    for (int i = 0, j = ids.size(); i < j; i++) {
		sqlVIP.append(ids.get(i));

		if (i < j - 1) {
		    sqlVIP.append(CommonDefine.COMMA);
		}
	    }

	    sqlVIP.append(")");
	}

	result.addAll(em.createNativeQuery(sqlVIP.toString()).getResultList());

	// VIP industry
	StringBuffer sqlSubarea = new StringBuffer();
	sqlSubarea.append(
		"SELECT DISTINCT(M.trans_id), T.descrb, M.subarea_id, -1, \"台区\" FROM M_MEASE M LEFT JOIN M_TRANS T ON M.trans_id = T.id LEFT JOIN M_SUBAREA S ON M.subarea_id = S.id LEFT JOIN A_INDUSTRY I ON S.industry_new = I.id WHERE M.subarea_id != -1 AND M.trans_id ");

	if (ids.size() == 1) {
	    sqlSubarea.append("= ").append(ids.get(0));
	} else {
	    sqlSubarea.append("IN (");

	    for (int i = 0, j = ids.size(); i < j; i++) {
		sqlSubarea.append(ids.get(i));

		if (i < j - 1) {
		    sqlSubarea.append(CommonDefine.COMMA);
		}
	    }

	    sqlSubarea.append(")");
	}

	result.addAll(em.createNativeQuery(sqlSubarea.toString()).getResultList());

	return result;
    }

    /**
     * Query industry and development level matrix by industry id
     * 
     * @param industryID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TransIndustryMatrix> queryPowerLoadFCMatrix(final List<Integer> ids) {
	List<TransIndustryMatrix> result = new ArrayList<TransIndustryMatrix>();

	if (ids.isEmpty()) {
	    return result;
	}

	StringBuffer sql = new StringBuffer();

	sql.append("SELECT * FROM A_TRANS_INDUSTRY_MATRIX M WHERE M.industry_id ");

	if (ids.size() == 1) {
	    sql.append("= ").append(ids.get(0));
	} else {
	    sql.append("IN (");

	    for (int i = 0; i < ids.size(); i++) {
		sql.append(ids.get(i));

		if (i < ids.size() - 1) {
		    sql.append(",");
		}
	    }

	    sql.append(")");
	}

	return em.createNativeQuery(sql.toString(), TransIndustryMatrix.class).getResultList();
    }

    /**
     * Query industry entities
     * 
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<IndustryClustered> queryIndustryInfo(final List<Integer> ids) {
	List<IndustryClustered> rs = new ArrayList<IndustryClustered>();

	if (ids.isEmpty()) {
	    return rs;
	}

	StringBuffer sql = new StringBuffer();

	sql.append("SELECT * FROM A_INDUSTRY I WHERE I.id ");

	if (ids.size() == 1) {
	    sql.append("= ").append(ids.get(0));
	} else {
	    sql.append("IN (");

	    for (int i = 0; i < ids.size(); i++) {
		sql.append(ids.get(i));

		if (i < ids.size() - 1) {
		    sql.append(",");
		}
	    }

	    sql.append(")");
	}

	return em.createNativeQuery(sql.toString(), IndustryClustered.class).getResultList();
    }

    /**
     * Query transformer industry parameters
     * 
     * @param transMatrixMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TransIndustryParameter> queryTransIndustryParameter(
	    final Map<Integer, List<TransIndustryMatrix>> transMatrixMap) {
	if (transMatrixMap.isEmpty()) {
	    return new ArrayList<TransIndustryParameter>();
	}

	List<Integer> tranIDs = new ArrayList<Integer>();

	for (Entry<Integer, List<TransIndustryMatrix>> entry : transMatrixMap.entrySet()) {
	    if (!entry.getValue().isEmpty()) {
		tranIDs.add(entry.getKey());
	    }
	}

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT * FROM A_TRANS_INDUSTRY_PARAMETER P WHERE ");

	for (int i = 0; i < tranIDs.size(); i++) {
	    int tid = tranIDs.get(i);

	    sql.append("(P.trans_id = ").append(tid).append(" AND P.matrix_id IN (");

	    for (int j = 0; j < transMatrixMap.get(tid).size(); j++) {
		sql.append(transMatrixMap.get(tid).get(j).getId());

		if (j < transMatrixMap.get(tid).size() - 1) {
		    sql.append(",");
		}
	    }

	    sql.append(")) ");

	    if (i < tranIDs.size() - 1) {
		sql.append("OR ");
	    }
	}

	return em.createNativeQuery(sql.toString(), TransIndustryParameter.class).getResultList();
    }

    /**
     * Query transformer power load forecast data
     * 
     * <li>0 - Transformer ID
     * <li>1 - Matrix ID
     * <li>2 - Line index
     * <li>3 - Power value
     * 
     * @param transIds
     *            List of transformer ID
     * @param matrixIds
     *            List of industry and development level matrix id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryPowerloadFC(final List<Integer> transIds, final List<Integer> matrixIds) {
	if (null == transIds || transIds.isEmpty() || null == matrixIds || matrixIds.isEmpty()) {
	    return new ArrayList<Object[]>();
	}

	StringBuffer sql = new StringBuffer();

	sql.append(
		"SELECT FC.trans_id, FC.matrix_id, FC.line_index, FC.power FROM A_TRANSPOWER_FORCAST FC WHERE FC.trans_id ");

	if (transIds.size() == 1) {
	    sql.append("= ").append(transIds.get(0));
	} else {
	    sql.append("IN (");

	    for (int i = 0, j = transIds.size(); i < j; i++) {
		sql.append(transIds.get(i));

		if (i < j - 1) {
		    sql.append(CommonDefine.COMMA);
		}
	    }

	    sql.append(")");
	}

	sql.append(" AND FC.matrix_id ");

	if (matrixIds.size() == 1) {
	    sql.append("= ").append(matrixIds.get(0));
	} else {
	    sql.append("IN (");

	    for (int i = 0, j = matrixIds.size(); i < j; i++) {
		sql.append(matrixIds.get(i));

		if (i < j - 1) {
		    sql.append(CommonDefine.COMMA);
		}
	    }

	    sql.append(")");
	}

	return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query matrix ids
     * 
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> queryMatrixID(final List<IndustryParamDTO> param) {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT M.id FROM A_TRANS_INDUSTRY_MATRIX M WHERE (M.industry_id=")
		.append(param.get(0).getIndustryID()).append(" AND M.dev_level=").append(param.get(0).getDevLevel())
		.append(")");

	for (int i = 1; i < param.size(); i++) {
	    sql.append(" OR (M.industry_id=").append(param.get(i).getIndustryID()).append(" AND M.dev_level=")
		    .append(param.get(i).getDevLevel()).append(")");
	}

	return em.createNativeQuery(sql.toString()).getResultList();
    }

    /**
     * Query industry parameters
     * 
     * <li>0 - Industry ID
     * <li>1 - High
     * <li>2 - Middle
     * <li>3 - low
     * 
     * @param industryIDs
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryIndustryParam(final List<Integer> industryIDs) {
	String sql = "SELECT I.id, I.high, I.middle, I.low FROM A_INDUSTRY I WHERE I.id IN ?ids";

	return em.createNativeQuery(sql).setParameter("ids", industryIDs).getResultList();
    }

    /**
     * Query power load customized forecast data
     * 
     * <li>0 - Matrix ID
     * <li>1 - Line index
     * <li>2 - Power value
     * 
     * @param transID
     * @param matrixIds
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryPowerLoadCustomizeFC(final int transID, final List<Integer> matrixIds) {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT F.matrix_id, F.line_index, F.power FROM A_TRANSPOWER_FORCAST F WHERE (F.trans_id=")
		.append(transID).append(" AND F.matrix_id=").append(matrixIds.get(0)).append(")");

	for (int i = 1; i < matrixIds.size(); i++) {
	    sql.append(" OR (F.trans_id=").append(transID).append(" AND F.matrix_id=").append(matrixIds.get(i))
		    .append(")");
	}

	return em.createNativeQuery(sql.toString()).getResultList();
    }
}