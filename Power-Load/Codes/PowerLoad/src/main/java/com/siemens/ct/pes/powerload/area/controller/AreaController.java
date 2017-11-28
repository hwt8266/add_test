/**
 * 
 */
package com.siemens.ct.pes.powerload.area.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.siemens.ct.pes.powerload.area.dao.AreaDAO;
import com.siemens.ct.pes.powerload.area.entities.Area;
import com.siemens.ct.pes.powerload.area.entities.AreaDTO;
import com.siemens.ct.pes.powerload.area.entities.AreaMenuItemDTO;
import com.siemens.ct.pes.powerload.area.entities.AreaPTableDTO;
import com.siemens.ct.pes.powerload.area.entities.AreaTran;
import com.siemens.ct.pes.powerload.area.entities.TranDTO;
import com.siemens.ct.pes.powerload.area.entities.TranInAreaPTableDTO;
import com.siemens.ct.pes.powerload.common.enums.DevelopmentLevel;
import com.siemens.ct.pes.powerload.common.utils.ExcelUtils;
import com.siemens.ct.pes.powerload.trans.dao.TransDAO;
import com.siemens.ct.pes.powerload.trans.entities.IndustryParamDTO;

/**
 * Controller for area functions
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class AreaController {

    private final static BigDecimal one = new BigDecimal("1");
    private final static BigDecimal hundred = new BigDecimal("100");

    @EJB
    private AreaDAO areaDao;

    @EJB
    private TransDAO tranDao;

    /**
     * Query areas list with basic information by area name and transformer name
     * 
     * @param areaName
     * @param remark
     * @param minIncrease
     * @param maxIncrease
     * @param pageNum
     * @return
     */
    public AreaPTableDTO queryAreaList(final String areaName, final String remark, final BigDecimal minIncrease,
            final BigDecimal maxIncrease, final int pageNum) {
        List<Object[]> rs = areaDao.queryAreas(areaName, remark, minIncrease, maxIncrease);

        // Make Demo data
        AreaPTableDTO dto = new AreaPTableDTO();

        dto.setCurrentPage(pageNum);
        dto.setTotalPage(rs.size() / 5 + (rs.size() % 5 == 0 ? 0 : 1));

        int start = (pageNum - 1) * 5;
        int end = (start + 4) < rs.size() ? (start + 4) : (rs.size() - 1);

        for (int i = 0; i < rs.size(); i++) {
            Object[] row = rs.get(i);

            dto.getAllIds().add((Integer) row[0]);

            if (i >= start && i <= end) {
                AreaDTO a = new AreaDTO();

                a.setId((Integer) row[0]);
                a.setName(row[1].toString());
                a.setIncrease(new BigDecimal(row[2].toString()));
                a.setRemark(row[3].toString());

                dto.getRowData().add(a);
            }
        }

        return dto;
    }

    /**
     * Create(id = 0) or modify area
     * 
     * @param area
     * @return
     */
    public boolean createOrModifyArea(final AreaDTO dto) {
        Area a;

        // Need update transformer's threshold
        boolean needUpdate = false;

        if (dto.getId() > 0) {
            a = areaDao.queryAreaByID(dto.getId());

            needUpdate = !dto.getIncrease().equals(a.getIncrease());
        } else {
            a = new Area();
        }

        try {
            a.setName(URLDecoder.decode(dto.getName(), "UTF-8"));
            a.setRemark(URLDecoder.decode(dto.getRemark(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        a.setIncrease(dto.getIncrease());

        boolean result = areaDao.saveArea(a);

        if (needUpdate) {
            updateAreaTranThreshold(a.getId(), a.getIncrease());
        }

        return result;
    }

    /**
     * Update area transformers threshold
     * 
     * @param areaId
     */
    public void updateAreaTranThreshold(final int areaId, final BigDecimal increase) {
        List<Object[]> ats = areaDao.queryTransInArea(areaId, null, 0, 0, null, null, null, 0, 0, 0, 0, 0, 0);
        List<Integer> tranIds = new ArrayList<Integer>();

        for (Object[] row : ats) {
            tranIds.add((Integer) row[0]);
        }

        Runnable thread = new Runnable() {
            @Override
            public void run() {
                calTransPredictThreshold(tranIds, increase);
            }
        };

        Thread t = new Thread(thread);

        t.start();
    }

    /**
     * Update area transformers threshold
     * 
     * @param tranIds
     */
    public void updateAreaTranThreshold(final List<Integer> tranIds, final BigDecimal increase) {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                calTransPredictThreshold(tranIds, increase);
            }
        };

        Thread t = new Thread(thread);

        t.start();
    }

    /**
     * Delete areas by area's id
     * 
     * @param areaIds
     * @return
     */
    public boolean deleteAreaByIds(final List<Integer> areaIds) {
        return areaDao.deleteAllTransOfAreas(areaIds) && areaDao.deleteAreas(areaIds);
    }

    /**
     * Query area menu items
     * 
     * @param areaName
     * @return
     */
    public List<AreaMenuItemDTO> getAreaMenuItems(final String areaName) {
        List<Object[]> rs = areaDao.queryAreaMenuItems(areaName);
        List<AreaMenuItemDTO> result = new ArrayList<AreaMenuItemDTO>();

        for (Object[] row : rs) {
            AreaMenuItemDTO dto = new AreaMenuItemDTO();

            dto.setAreaId((Integer) row[0]);
            dto.setAreaName(row[1].toString());
            dto.setTranCount((Long) row[2]);

            result.add(dto);
        }

        return result;
    }

    /**
     * Query transformers included in area
     * 
     * @param areaId
     * @param tranName
     * @param tranType
     * @param vLevel
     * @param deptName
     * @param minCap
     * @param maxCap
     * @param minLow
     * @param maxLow
     * @param minHigh
     * @param maxHigh
     * @param minOver
     * @param maxOver
     * @param pageNum
     * @return
     */
    public TranInAreaPTableDTO queryTransInArea(final int areaId, final String tranName, final int tranType,
            final int vLevel, final String deptName, final BigDecimal minCap, final BigDecimal maxCap, final int minLow,
            final int maxLow, final int minHigh, final int maxHigh, final int minOver, final int maxOver,
            final int pageNum) {
        List<Object[]> rs = areaDao.queryTransInArea(areaId, tranName, tranType, vLevel, deptName, minCap, maxCap,
                minLow, maxLow, minHigh, maxHigh, minOver, maxOver);

        TranInAreaPTableDTO dto = new TranInAreaPTableDTO();

        dto.setCurrentPage(pageNum);
        dto.setTotalPage(rs.size() / 5 + (rs.size() % 5 == 0 ? 0 : 1));

        int start = (pageNum - 1) * 5;
        int end = (start + 4) < rs.size() ? (start + 4) : (rs.size() - 1);

        for (int i = 0; i < rs.size(); i++) {
            int tranId = (Integer) rs.get(i)[0];

            dto.getAllIds().add(tranId);

            if (i >= start && i <= end) {
                TranDTO t = new TranDTO();

                t.setId(tranId);
                t.setName(rs.get(i)[1].toString());
                t.setType(rs.get(i)[2].toString());
                t.setvLevel(rs.get(i)[3].toString());
                t.setCapacity((BigDecimal) rs.get(i)[4]);
                t.setIncrease((BigDecimal) rs.get(i)[5]);
                t.setLowPeriod((Integer) rs.get(i)[6]);
                t.setHeavyPeriod((Integer) rs.get(i)[7]);
                t.setOverPeriod((Integer) rs.get(i)[8]);
                t.setDeptName(rs.get(i)[9].toString());

                dto.getRowData().add(t);
            }
        }

        return dto;
    }

    /**
     * Modify transformer's parameter
     * 
     * @param areaId
     * @param tranId
     * @param increase
     * @return
     */
    public boolean modifyTranParameter(final int areaId, final int tranId, final BigDecimal increase) {
        AreaTran at = areaDao.queryTranInAreaByID(areaId, tranId);

        at.setIncrease(increase);

        List<Integer> tranIds = new ArrayList<Integer>();
        tranIds.add(tranId);

        boolean result = areaDao.saveTranInArea(at);

        updateAreaTranThreshold(tranIds, increase);

        return result;
    }

    /**
     * Query transformers included in area
     * 
     * @param areaId
     * @param tranName
     * @param deptName
     * @param minCap
     * @param maxCap
     * @param pageNum
     * @return
     */
    public TranInAreaPTableDTO queryRawTransForArea(final int areaId, final String tranName, final int tranType,
            int vLevel, final String deptName, final BigDecimal minCap, final BigDecimal maxCap, final int pageNum) {
        List<Object[]> rs = areaDao.queryRawTrans(tranName, tranType, vLevel, deptName, minCap, maxCap);
        List<Object[]> rsTranInArea = areaDao.queryTransInArea(areaId, null, 0, 0, null, null, null, 0, 0, 0, 0, 0, 0);

        Set<Integer> tranIDInArea = new HashSet<Integer>();

        for (Object[] row : rsTranInArea) {
            tranIDInArea.add((Integer) row[0]);
        }

        List<Object[]> finalRS = new ArrayList<Object[]>();

        for (Object[] row : rs) {
            int tranId = (Integer) row[0];

            if (!tranIDInArea.contains(tranId)) {
                finalRS.add(row);
            }
        }

        TranInAreaPTableDTO dto = new TranInAreaPTableDTO();

        dto.setCurrentPage(pageNum);
        dto.setTotalPage(finalRS.size() / 5 + (finalRS.size() % 5 == 0 ? 0 : 1));

        int start = (pageNum - 1) * 5;
        int end = (start + 4) < finalRS.size() ? (start + 4) : (finalRS.size() - 1);

        for (int i = 0; i < finalRS.size(); i++) {
            dto.getAllIds().add((Integer) finalRS.get(i)[0]);

            if (i >= start && i <= end) {
                TranDTO t = new TranDTO();

                t.setId((Integer) finalRS.get(i)[0]);
                t.setName(finalRS.get(i)[1].toString());
                t.setType(finalRS.get(i)[2].toString());
                t.setCapacity(new BigDecimal(finalRS.get(i)[3].toString()));
                t.setvLevel(finalRS.get(i)[4].toString());
                t.setDeptName(finalRS.get(i)[5].toString());

                dto.getRowData().add(t);
            }
        }

        return dto;
    }

    /**
     * Delete transformers from area
     * 
     * @param areaId
     * @param tranIds
     * @return
     */
    public boolean deleteTransInArea(final int areaId, final List<Integer> tranIds) {
        return areaDao.deleteTransFromArea(areaId, tranIds);
    }

    /**
     * Add transformers to area
     * 
     * @param areaId
     *            Target area's ID
     * @param tranIds
     *            Transformer's IDs
     * @return
     */
    public boolean addTransToArea(final int areaId, final List<Integer> tranIds) {
        boolean result = areaDao.addTranToArea(areaId, tranIds);
        Area a = areaDao.queryAreaByID(areaId);

        updateAreaTranThreshold(tranIds, a.getIncrease());

        return result;
    }

    /**
     * Get transformer's power load forecast data
     * 
     * @param tranIds
     * @return
     */
    public List<Object[]> getTranPowerLoadFC(final List<Integer> tranIds) {
        List<Object[]> rs = tranDao.queryTransVIPIndustryInfo(tranIds);

        Set<Integer> industries = new HashSet<Integer>();

        // All Private transformer
        if (rs.size() == tranIds.size()) {
            for (Object[] row : rs) {
                industries.add(Integer.parseInt(row[3].toString()));
            }
        }
        // Public transformer
        else {
            industries.add(-1);
        }

        List<IndustryParamDTO> indusParams = new ArrayList<IndustryParamDTO>();

        // Only use high development level
        for (int indusId : industries) {
            IndustryParamDTO param = new IndustryParamDTO();
            param.setIndustryID(indusId);
            param.setDevLevel(DevelopmentLevel.HIGH.getIndex());

            indusParams.add(param);
        }

        List<Integer> matrixIds = tranDao.queryMatrixID(indusParams);

        return tranDao.queryPowerloadFC(tranIds, matrixIds);
    }

    /**
     * Get transformer's forecast data
     * 
     * @param tranIds
     * @return
     */
    public Map<Integer, Map<Integer, BigDecimal>> getTranFCData(final List<Integer> tranIds) {
        // Get transformer's power load forecast data
        List<Object[]> fcs = getTranPowerLoadFC(tranIds);

        // <tran_id, <line_index, pv>>
        Map<Integer, Map<Integer, BigDecimal>> tranPVMap = new HashMap<Integer, Map<Integer, BigDecimal>>();

        for (Object[] row : fcs) {
            int tranId = (Integer) row[0];
            int lineIndex = (Integer) row[2];
            BigDecimal pv = (BigDecimal) row[3];

            Map<Integer, BigDecimal> lineMap = tranPVMap.get(tranId);

            if (null == lineMap) {
                lineMap = new HashMap<Integer, BigDecimal>();
                tranPVMap.put(tranId, lineMap);
            }

            BigDecimal totalPV = lineMap.get(lineIndex);

            if (totalPV == null) {
                lineMap.put(lineIndex, pv);
            } else {
                lineMap.put(lineIndex, totalPV.add(pv));
            }
        }

        return tranPVMap;
    }

    /**
     * Calculate transformer's power load predict data's threshold
     * 
     * @param tranIds
     * @param increase
     * @return
     */
    public void calTransPredictThreshold(final List<Integer> tranIds, final BigDecimal increase) {
        Map<Integer, Map<Integer, BigDecimal>> tranPVMap = getTranFCData(tranIds);

        List<AreaTran> areaTrans = areaDao.queryAreaTrans(tranIds);

        Map<Integer, AreaTran> areaTranMap = new HashMap<Integer, AreaTran>();

        for (AreaTran at : areaTrans) {
            at.setIncrease(increase);
            areaTranMap.put(at.getId().getTranId(), at);
        }

        Map<Integer, Integer[]> thredholds = new HashMap<Integer, Integer[]>();

        for (Entry<Integer, Map<Integer, BigDecimal>> entry : tranPVMap.entrySet()) {
            int tranId = entry.getKey();

            // 0-low,1-high,2-over
            Integer[] thredhold = thredholds.get(tranId);

            if (null == thredhold) {
                thredhold = new Integer[3];
                thredhold[0] = 0;
                thredhold[1] = 0;
                thredhold[2] = 0;

                thredholds.put(tranId, thredhold);
            }

            for (Entry<Integer, BigDecimal> line : entry.getValue().entrySet()) {
                BigDecimal valueB = line.getValue()
                        .multiply(areaTranMap.get(tranId).getIncrease().divide(hundred).add(one)).multiply(hundred);
                double value = valueB.doubleValue();

                if (value <= 20) {
                    thredhold[0] = thredhold[0] + 1;
                } else if (value >= 80 && value <= 100) {
                    thredhold[1] = thredhold[1] + 1;
                } else if (value > 100) {
                    thredhold[2] = thredhold[2] + 1;
                }
            }
        }

        for (AreaTran at : areaTranMap.values()) {
            // 0-low,1-high,2-over
            Integer[] thredhold = thredholds.get(at.getId().getTranId());

            at.setLowMin(null == thredhold ? 0 : 15 * thredhold[0]);
            at.setHighMin(null == thredhold ? 0 : 15 * thredhold[1]);
            at.setOverMin(null == thredhold ? 0 : 15 * thredhold[2]);
        }

        areaDao.saveAreaTran(areaTranMap.values());
    }

    /**
     * Export area forecast data
     * 
     * @param areaIds
     * @param filePath
     */
    public void exportForcastData(final List<Integer> areaIds, final String filePath) throws IOException {
        // Area ID/Name map
        Map<Integer, String> areaNameMap = new HashMap<Integer, String>();
        // Area transformer attributes(<area_id, <tran_id,
        // <Array[area_name,tran_name,tran_type,vlevel,capacity,low_min,high_min,over_min,highest]>>>)
        // map
        Map<Integer, Map<Integer, List<String>>> attrMap = new HashMap<Integer, Map<Integer, List<String>>>();
        // Transformer power load forecast data map<tran_id, <Array[96]>>
        Map<Integer, List<String>> tranPowerLoad = new HashMap<Integer, List<String>>();

        // Query transformer's attributes
        List<Object[]> attrRS = areaDao.queryAreaTranDetail(areaIds);

        List<Integer> tranIds = new ArrayList<Integer>();
        Map<Integer, BigDecimal> tranIncreaseMap = new HashMap<Integer, BigDecimal>();

        // Fill basic attributes
        for (Object[] row : attrRS) {
            int areaId = (Integer) row[0];
            String areaName = row[1].toString();
            int tranId = (Integer) row[2];
            String tranName = row[3].toString();
            String tranType = row[4].toString();
            String vlevel = row[5].toString();
            String capacity = row[6].toString();
            String lowMin = row[7].toString();
            String highMin = row[8].toString();
            String overMin = row[9].toString();
            BigDecimal increase = (BigDecimal) row[10];

            areaNameMap.put(areaId, areaName);

            Map<Integer, List<String>> tranAttrMap = attrMap.get(areaId);

            if (null == tranAttrMap) {
                tranAttrMap = new HashMap<Integer, List<String>>();
                attrMap.put(areaId, tranAttrMap);
            }

            List<String> attrs = tranAttrMap.get(tranId);

            if (null == attrs) {
                attrs = new ArrayList<String>();
                tranAttrMap.put(tranId, attrs);
            }

            attrs.add(areaName);
            attrs.add(tranName);
            attrs.add(tranType);
            attrs.add(vlevel);
            attrs.add(capacity);
            attrs.add(lowMin);
            attrs.add(highMin);
            attrs.add(overMin);

            tranIds.add(tranId);
            tranIncreaseMap.put(tranId, increase);
        }

        // Get transformer's power load forecast data:<tran_id,<line_index,pv>>
        Map<Integer, Map<Integer, BigDecimal>> tranPVMap = getTranFCData(tranIds);

        Map<Integer, BigDecimal> tranFCMaxMap = new HashMap<Integer, BigDecimal>();

        for (Entry<Integer, Map<Integer, BigDecimal>> entryPV : tranPVMap.entrySet()) {
            BigDecimal max = new BigDecimal(0);
            int tranId = entryPV.getKey();
            BigDecimal increase = tranIncreaseMap.get(tranId);

            List<String> pvs = tranPowerLoad.get(tranId);

            if (null == pvs) {
                pvs = new ArrayList<String>();
                tranPowerLoad.put(tranId, pvs);
            }

            for (int i = 0; i < 96; i++) {
                BigDecimal pv = entryPV.getValue().get(i);

                BigDecimal finalPV = pv.multiply(increase.divide(hundred).add(one)).multiply(hundred);

                pvs.add(finalPV.toString());

                if (max.compareTo(finalPV) < 0) {
                    max = finalPV;
                }
            }

            tranFCMaxMap.put(tranId, max);
        }

        for (Entry<Integer, Map<Integer, List<String>>> entry : attrMap.entrySet()) {
            for (Entry<Integer, List<String>> tranAttrs : entry.getValue().entrySet()) {
                BigDecimal max = tranFCMaxMap.get(tranAttrs.getKey());

                tranAttrs.getValue().add(null == max ? String.valueOf(0) : max.toString());
            }
        }

        HSSFWorkbook workbook = ExcelUtils.createExcelWorkBook(areaNameMap, attrMap, tranPowerLoad,
                ExcelUtils.LV1HEADER, ExcelUtils.getLv1HeaderPosition(), ExcelUtils.LV2HEADER,
                ExcelUtils.getLv2HeaderPosition());

        ExcelUtils.exportToFile(workbook, filePath);
    }
}