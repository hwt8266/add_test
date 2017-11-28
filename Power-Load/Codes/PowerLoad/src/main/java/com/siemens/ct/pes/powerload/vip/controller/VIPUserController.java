/**
 * Controller for VIP user power load module
 */
package com.siemens.ct.pes.powerload.vip.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.siemens.ct.pes.powerload.common.entities.FeatureLineDTO;
import com.siemens.ct.pes.powerload.common.entities.Industry;
import com.siemens.ct.pes.powerload.common.entities.LinePointDTO;
import com.siemens.ct.pes.powerload.common.entities.Pair;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadEnvImpactDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioYoYDTO;
import com.siemens.ct.pes.powerload.common.entities.VIP;
import com.siemens.ct.pes.powerload.common.enums.FeatureType;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;
import com.siemens.ct.pes.powerload.common.utils.Utilities;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadFeatureEntityDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadModeDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadModeEntitiesDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadRatioStatisticDTO;
import com.siemens.ct.pes.powerload.vip.dao.VIPUserDAO;
import com.siemens.ct.pes.powerload.vip.entities.IndustryCluster;
import com.siemens.ct.pes.powerload.vip.entities.IndustryClusterCategory;
import com.siemens.ct.pes.powerload.vip.entities.IndustryClusterLink;
import com.siemens.ct.pes.powerload.vip.entities.IndustryClusterNode;

/**
 * Controller for VIP user power load module
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class VIPUserController {

    @EJB
    private VIPUserDAO dao;

    /**
     * Get VIP user power load ratio
     * 
     * @param start
     * @param end
     * @param vipIDs
     * @return
     */
    public List<PowerLoadRatioDTO> getVipsPowerLoadRatio(final long start, final long end, final List<Integer> vipIDs) {
        List<PowerLoadRatioDTO> results = new ArrayList<PowerLoadRatioDTO>();

        // Key:VIP_ID
        Map<Integer, PowerLoadRatioDTO> vipMap = new HashMap<Integer, PowerLoadRatioDTO>();

        // Key:VIP_ID, Value:MP_IDs
        Map<Integer, Set<Integer>> vmMap = new HashMap<Integer, Set<Integer>>();

        // Key:MP_ID, Value:VIP_ID
        Map<Integer, Integer> mvMap = new HashMap<Integer, Integer>();

        List<Object[]> rs = dao.queryVIPBasicInfo(vipIDs);

        for (Object[] row : rs) {
            int vipID = Integer.parseInt(row[0].toString().trim());

            if (null == vipMap.get(vipID)) {
                PowerLoadRatioDTO dto = new PowerLoadRatioDTO();
                dto.setId(vipID);
                dto.setName(row[1].toString());
                dto.setCapacity(new BigDecimal(row[2].toString()));

                vipMap.put(vipID, dto);
            }

            Set<Integer> measeIDs = vmMap.get(vipID);

            if (null == measeIDs) {
                measeIDs = new HashSet<Integer>();
                vmMap.put(vipID, measeIDs);
            }

            int mpID = Integer.parseInt(row[3].toString().trim());
            measeIDs.add(mpID);

            mvMap.put(mpID, vipID);
        }

        List<Integer> measeIDs = new ArrayList<>();

        for (Set<Integer> ids : vmMap.values()) {
            measeIDs.addAll(ids);
        }

        List<Object[]> ratios = dao.queryVIPPowerLoadRatio(start, end, measeIDs);

        // VIP_ID, <TV, PS>
        Map<Integer, Map<Long, BigDecimal>> map = new HashMap<Integer, Map<Long, BigDecimal>>();

        for (Object[] row : ratios) {
            int measeID = Integer.parseInt(row[0].toString().trim());
            int vipID = mvMap.get(measeID);

            Map<Long, BigDecimal> vipResultMap = map.get(vipID);

            if (null == vipResultMap) {
                vipResultMap = new HashMap<Long, BigDecimal>();
                map.put(vipID, vipResultMap);
            }

            BigDecimal ps = row[2] == null ? new BigDecimal(0) : new BigDecimal(row[2].toString().trim());

            long tv = Long.parseLong(row[1].toString().trim());
            BigDecimal sum = vipResultMap.get(tv);

            if (null == sum) {
                sum = new BigDecimal(0);
            }

            sum = sum.add(ps);
            vipResultMap.put(tv, sum);
        }

        for (Entry<Integer, Map<Long, BigDecimal>> entry : map.entrySet()) {
            int tranID = entry.getKey();

            PowerLoadRatioDTO dto = vipMap.get(tranID);

            List<Long> tvs = new ArrayList<Long>();
            tvs.addAll(entry.getValue().keySet());
            Collections.sort(tvs);

            for (long tv : tvs) {
                LinePointDTO line = new LinePointDTO();

                line.setTv(tv);
                line.setValue(entry.getValue().get(tv));

                dto.getPoints().add(line);
            }
        }

        results.addAll(vipMap.values());

        return results;
    }

    /**
     * Get transformer power load features
     * 
     * @param ids
     *            Vipsformer id list
     * @return
     */
    public List<PowerLoadFeatureEntityDTO> getVipsPowerLoadFeature(final List<Integer> ids) {
        List<PowerLoadFeatureEntityDTO> results = new ArrayList<PowerLoadFeatureEntityDTO>();

        List<Object[]> rs = dao.queryVIPPowerLoadFeature(ids);
        List<VIP> vs = dao.queryVIPByIDs(ids);

        // VIP ID, <Feature type, <Feature line index, <Point index, PointDTO>>>
        Map<Integer, Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>>> cache = new HashMap<Integer, Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>>>();
        // VIP ID, <Feature line index, DynamicParam>>
        Map<Integer, Map<Integer, BigDecimal>> dynamicParamMap = new HashMap<Integer, Map<Integer, BigDecimal>>();
        // VIP ID, <Feature line index, StaticParam>>
        Map<Integer, Map<Integer, BigDecimal>> staticParamMap = new HashMap<Integer, Map<Integer, BigDecimal>>();
        // VIP ID, VIP Name
        Map<Integer, String> vipIDNameMap = new HashMap<Integer, String>();

        for (VIP v : vs) {
            vipIDNameMap.put(v.getId(), v.getName());
        }

        for (Object[] row : rs) {
            int vipID = Integer.valueOf(row[0].toString().trim());
            int type = Integer.valueOf(row[1].toString().trim());
            int fIndex = Integer.valueOf(row[2].toString().trim());
            int lineIndex = Integer.valueOf(row[3].toString().trim());
            BigDecimal power = new BigDecimal(row[4].toString().trim());

            if (type == FeatureType.DAY.getIndex()) {
                Map<Integer, BigDecimal> fdMap = dynamicParamMap.get(vipID);

                if (null == fdMap) {
                    fdMap = new HashMap<Integer, BigDecimal>();
                    dynamicParamMap.put(vipID, fdMap);
                }

                fdMap.put(fIndex, new BigDecimal(row[5].toString().trim()));

                Map<Integer, BigDecimal> fsMap = staticParamMap.get(vipID);

                if (null == fsMap) {
                    fsMap = new HashMap<Integer, BigDecimal>();
                    staticParamMap.put(vipID, fsMap);
                }

                fsMap.put(fIndex, new BigDecimal(row[6].toString().trim()));
            }

            Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>> vipData = cache.get(vipID);

            if (null == vipData) {
                vipData = new HashMap<Integer, Map<Integer, Map<Integer, LinePointDTO>>>();
                Map<Integer, Map<Integer, LinePointDTO>> featureGroup = new HashMap<Integer, Map<Integer, LinePointDTO>>();
                Map<Integer, LinePointDTO> lineMap = new HashMap<Integer, LinePointDTO>();

                LinePointDTO point = new LinePointDTO();
                point.setIndex(lineIndex);
                point.setValue(power);

                lineMap.put(lineIndex, point);
                featureGroup.put(fIndex, lineMap);
                vipData.put(type, featureGroup);
                cache.put(vipID, vipData);
            } else {
                Map<Integer, Map<Integer, LinePointDTO>> featureGroup = vipData.get(type);

                if (null == featureGroup) {
                    featureGroup = new HashMap<Integer, Map<Integer, LinePointDTO>>();
                    Map<Integer, LinePointDTO> lineMap = new HashMap<Integer, LinePointDTO>();

                    LinePointDTO point = new LinePointDTO();
                    point.setIndex(lineIndex);
                    point.setValue(power);

                    lineMap.put(lineIndex, point);
                    featureGroup.put(fIndex, lineMap);
                    vipData.put(type, featureGroup);
                } else {
                    Map<Integer, LinePointDTO> lineMap = featureGroup.get(fIndex);

                    if (null == lineMap) {
                        lineMap = new HashMap<Integer, LinePointDTO>();
                        featureGroup.put(fIndex, lineMap);
                    }

                    LinePointDTO point = lineMap.get(lineIndex);

                    if (null == point) {
                        point = new LinePointDTO();
                        point.setIndex(lineIndex);
                        point.setValue(power);

                        lineMap.put(lineIndex, point);
                    }
                }
            }
        }

        // Build DTO
        for (Entry<Integer, Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>>> trans : cache.entrySet()) {
            PowerLoadFeatureEntityDTO dto = new PowerLoadFeatureEntityDTO();

            int transID = trans.getKey();
            dto.setId(transID);

            if (vipIDNameMap.get(transID) != null) {
                dto.setName(vipIDNameMap.get(transID));
            }

            for (Entry<Integer, Map<Integer, Map<Integer, LinePointDTO>>> featureGroup : trans.getValue().entrySet()) {
                int type = featureGroup.getKey();

                for (Entry<Integer, Map<Integer, LinePointDTO>> featureInType : featureGroup.getValue().entrySet()) {
                    FeatureLineDTO line = new FeatureLineDTO();

                    for (Entry<Integer, LinePointDTO> point : featureInType.getValue().entrySet()) {
                        line.setIndex(point.getKey());
                        line.getFeatureDataList().add(point.getValue());
                    }

                    if (type == FeatureType.DAY.getIndex()) {
                        line.setDynamicParam(dynamicParamMap.get(transID).get(featureInType.getKey()));
                        line.setStaticParam(staticParamMap.get(transID).get(featureInType.getKey()));

                        dto.getFeatureD().add(line);
                    }

                    if (type == FeatureType.MONTH.getIndex()) {
                        dto.getFeatureM().add(line);
                    }

                    if (type == FeatureType.QUARTER.getIndex()) {
                        dto.getFeatureQ().add(line);
                    }
                }
            }

            // Sort day feature point index
            for (FeatureLineDTO line : dto.getFeatureD()) {
                Collections.sort(line.getFeatureDataList());
            }

            // Sort month feature point index
            Collections.sort(dto.getFeatureM());

            for (FeatureLineDTO line : dto.getFeatureM()) {
                Collections.sort(line.getFeatureDataList());
            }

            // Sort quarter feature point index
            Collections.sort(dto.getFeatureQ());

            for (FeatureLineDTO line : dto.getFeatureQ()) {
                Collections.sort(line.getFeatureDataList());
            }

            results.add(dto);
        }

        return results;
    }

    /**
     * Get VIP power load ratio statistic data
     * 
     * @param startTime
     * @param endTime
     * @param id
     * @return
     */
    public PowerLoadRatioStatisticDTO getVipsPowerLoadRatioStatistic(final long startTime, final long endTime,
            final int id) {
        PowerLoadRatioStatisticDTO dto = new PowerLoadRatioStatisticDTO();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(id);

        List<Object[]> rs = dao.queryVIPBasicInfo(ids);

        dto.setId(id);

        if (null != rs && !rs.isEmpty()) {
            BigDecimal capacity = new BigDecimal(rs.get(0)[2].toString().trim());
            List<Integer> measeIDs = new ArrayList<Integer>();

            for (Object[] row : rs) {
                int mpID = Integer.parseInt(row[3].toString().trim());
                measeIDs.add(mpID);
            }

            List<Object[]> ratios = dao.queryVIPPowerLoadRatio(startTime, endTime, measeIDs);
            Map<String, Double> sumMap = new HashMap<String, Double>();

            for (Object[] row : ratios) {
                String tv = row[1].toString();
                double ps = row[2] == null ? 0 : Double.parseDouble(row[2].toString().trim());

                Double sum = sumMap.get(tv);

                if (null == sum) {
                    sum = Double.valueOf(0);
                }

                sum += ps;

                sumMap.put(tv, sum);
            }

            int lowerThirty = 0;
            int thirtyToSixty = 0;
            int sixtyToNinety = 0;
            int greaterNinety = 0;

            for (double ps : sumMap.values()) {
                int scale = (int) (Math.abs(ps) / capacity.doubleValue() * 100);

                if (scale <= 30) {
                    lowerThirty++;
                } else if (scale > 30 && scale <= 60) {
                    thirtyToSixty++;
                } else if (scale > 60 && scale <= 90) {
                    sixtyToNinety++;
                } else {
                    greaterNinety++;
                }
            }

            dto.setLowerThirty(lowerThirty);
            dto.setThirtyToSixty(thirtyToSixty);
            dto.setSixtyToNinety(sixtyToNinety);
            dto.setGreaterNinety(greaterNinety);
        }

        return dto;
    }

    /**
     * Get VIP user's power load modes
     * 
     * @param ids
     *            VIP id list
     * @return
     */
    public List<PowerLoadModeEntitiesDTO> getVipsPowerLoadMode(final List<Integer> ids) {
        List<PowerLoadModeEntitiesDTO> results = new ArrayList<PowerLoadModeEntitiesDTO>();
        Map<Integer, PowerLoadModeEntitiesDTO> map = new HashMap<Integer, PowerLoadModeEntitiesDTO>();

        List<VIP> vips = dao.queryVIPByIDs(ids);

        for (VIP v : vips) {
            PowerLoadModeEntitiesDTO dto = new PowerLoadModeEntitiesDTO();
            dto.setId(v.getId());
            dto.setName(v.getName());
            map.put(v.getId(), dto);
        }

        // Query limit power load mode
        List<Object[]> lrs = dao.queryVIPPowerLoadLimit(ids);

        for (Object[] row : lrs) {
            int vipID = Integer.parseInt(row[0].toString().trim());
            int limitType = Integer.parseInt(row[1].toString().trim());
            int index = Integer.parseInt(row[2].toString().trim());
            BigDecimal power = new BigDecimal(row[3].toString().trim());

            PowerLoadModeEntitiesDTO dto = map.get(vipID);

            LinePointDTO point = new LinePointDTO();

            point.setIndex(index);
            point.setValue(power);

            if (limitType == 1) {
                dto.getMaxRatio().add(point);
            } else {
                dto.getMinRatio().add(point);
            }

            Collections.sort(dto.getMaxRatio());
            Collections.sort(dto.getMinRatio());

            map.put(vipID, dto);
        }

        // Query power load mode
        List<Object[]> rs = dao.queryVIPPowerLoadMode(ids);

        // Key: VIP ID, <Mode index, Mode line>
        Map<Integer, Map<Integer, List<LinePointDTO>>> nMap = new HashMap<Integer, Map<Integer, List<LinePointDTO>>>();

        for (Object[] row : rs) {
            int vipID = Integer.parseInt(row[0].toString().trim());
            int modeIndex = Integer.parseInt(row[1].toString().trim());
            int index = Integer.parseInt(row[2].toString().trim());
            BigDecimal power = new BigDecimal(row[3].toString());

            Map<Integer, List<LinePointDTO>> modeMap = nMap.get(vipID);

            if (null == modeMap) {
                modeMap = new HashMap<Integer, List<LinePointDTO>>();

                nMap.put(vipID, modeMap);
            }

            List<LinePointDTO> line = modeMap.get(modeIndex);

            if (null == line) {
                line = new ArrayList<LinePointDTO>();
                modeMap.put(modeIndex, line);
            }

            LinePointDTO point = new LinePointDTO();
            point.setIndex(index);
            point.setValue(power);

            line.add(point);
        }

        for (int transID : ids) {
            PowerLoadModeEntitiesDTO dto = map.get(transID);

            if (null != nMap.get(transID)) {
                for (Entry<Integer, List<LinePointDTO>> modeEntry : nMap.get(transID).entrySet()) {
                    PowerLoadModeDTO mode = new PowerLoadModeDTO();

                    mode.setModeID(modeEntry.getKey());
                    mode.setPoints(modeEntry.getValue());

                    Collections.sort(mode.getPoints());

                    dto.getPowerModes().add(mode);
                }
            }

            results.add(dto);
        }

        return results;
    }

    /**
     * Get VIP user's power load environment impact factors
     * 
     * @param ids
     *            VIP id list
     * @return
     */
    public List<PowerLoadEnvImpactDTO> getVipsPowerLoadEnvImpact(final List<Integer> ids) {
        List<PowerLoadEnvImpactDTO> results = new ArrayList<PowerLoadEnvImpactDTO>();

        List<Object[]> rs = dao.queryVIPPowerLoadEnvImpact(ids);

        for (Object[] row : rs) {
            PowerLoadEnvImpactDTO dto = new PowerLoadEnvImpactDTO();

            dto.setId((Integer) row[0]);
            dto.setName(row[1].toString());
            dto.setRainfall((BigDecimal) row[2]);
            dto.setTemperature((BigDecimal) row[3]);
            dto.setWindSpeed((BigDecimal) row[4]);
            dto.setHumidity((BigDecimal) row[5]);

            results.add(dto);
        }

        return results;
    }

    /**
     * Get VIP user's year on year power load ratio
     * 
     * @param ids
     *            List of VIP IDs
     * @return
     */
    public List<PowerLoadRatioYoYDTO> getVipsPowerLoadRatioYoY(final List<Integer> ids) {
        List<PowerLoadRatioYoYDTO> results = new ArrayList<PowerLoadRatioYoYDTO>();
        Map<Integer, Pair<String, BigDecimal>> maxImpactMap = new HashMap<Integer, Pair<String, BigDecimal>>();

        List<Object[]> impactRS = dao.queryVIPPowerLoadEnvImpact(ids);

        for (Object[] row : impactRS) {
            int id = Integer.parseInt(row[0].toString().trim());
            BigDecimal rainfall = new BigDecimal(row[2].toString().trim());
            BigDecimal temperature = new BigDecimal(row[3].toString().trim());
            BigDecimal windSpeed = new BigDecimal(row[4].toString().trim());
            BigDecimal humidity = new BigDecimal(row[5].toString().trim());

            BigDecimal max = rainfall;
            String impactName = "降雨";

            if (max.compareTo(temperature) < 0) {
                max = temperature;
                impactName = "温度";
            }

            if (max.compareTo(windSpeed) < 0) {
                max = windSpeed;
                impactName = "风速";
            }

            if (max.compareTo(humidity) < 0) {
                max = humidity;
                impactName = "湿度";
            }

            Pair<String, BigDecimal> impactPair = new Pair<String, BigDecimal>(impactName, max);
            maxImpactMap.put(id, impactPair);
        }

        // <VIP_ID,YOYDTO>
        Map<Integer, PowerLoadRatioYoYDTO> map = new HashMap<Integer, PowerLoadRatioYoYDTO>();
        // <MP_ID, VIP_ID>
        Map<Integer, Integer> mvMap = new HashMap<Integer, Integer>();

        List<Object[]> rs = dao.queryVIPBasicInfo(ids);

        for (Object[] row : rs) {
            int vipID = Integer.parseInt(row[0].toString().trim());

            if (null == map.get(vipID)) {
                PowerLoadRatioYoYDTO dto = new PowerLoadRatioYoYDTO();

                dto.setId(vipID);
                dto.setName(row[1].toString());
                dto.setCapacity(new BigDecimal(row[2].toString().trim()));
                dto.setImpactName(maxImpactMap.get(dto.getId()) == null ? CommonDefine.EMPTY
                        : maxImpactMap.get(dto.getId()).getKey());
                dto.setImpactValue(maxImpactMap.get(dto.getId()) == null ? new BigDecimal(0)
                        : maxImpactMap.get(dto.getId()).getValue());

                map.put(vipID, dto);
            }

            int mpID = Integer.parseInt(row[3].toString().trim());

            mvMap.put(mpID, vipID);
        }

        List<Integer> measeIDs = new ArrayList<>();
        measeIDs.addAll(mvMap.keySet());

        // <This year<Last month start, Last month end>, Last year<Last month start, Last month end>>
        // Pair<Pair<Long, Long>, Pair<Long, Long>> pair = getYoYTimeStamp(System.currentTimeMillis());
        // <201608_start, 201608_end>, 201508_start, 201508_end>>
        Pair<Pair<Long, Long>, Pair<Long, Long>> pair = new Pair<Pair<Long, Long>, Pair<Long, Long>>(
                new Pair<Long, Long>(1469980800l, 1472659199l), new Pair<Long, Long>(1438358400l, 1441036799l));

        List<Object[]> thisYearRatios = dao.queryVIPPowerLoadRatio(pair.getKey().getKey(), pair.getKey().getValue(),
                measeIDs);

        // <Tran_ID, <Index, Power values>>
        Map<Integer, Map<Integer, List<BigDecimal>>> vector = new HashMap<Integer, Map<Integer, List<BigDecimal>>>();

        // Group data by index
        for (Object[] row : thisYearRatios) {
            int measeID = Integer.parseInt(row[0].toString().trim());
            int vipID = mvMap.get(measeID);

            Map<Integer, List<BigDecimal>> line = vector.get(vipID);

            if (null == line) {
                line = new HashMap<Integer, List<BigDecimal>>();

                vector.put(vipID, line);
            }

            long tv = Long.parseLong(row[1].toString().trim());

            int index = Utilities.getIndexOfDay(tv);

            List<BigDecimal> pValues = line.get(index);

            if (null == pValues) {
                pValues = new ArrayList<BigDecimal>();

                line.put(index, pValues);
            }

            BigDecimal ps = row[2] == null ? new BigDecimal(0) : new BigDecimal(row[2].toString().trim());

            pValues.add(ps);
        }

        // Set data into DTO
        for (Entry<Integer, Map<Integer, List<BigDecimal>>> entry : vector.entrySet()) {
            int tranID = entry.getKey();

            PowerLoadRatioYoYDTO dto = map.get(tranID);

            for (Entry<Integer, List<BigDecimal>> e : entry.getValue().entrySet()) {
                BigDecimal pv = Utilities.addAll(e.getValue());

                // Average all day's power values
                pv = pv.divide(new BigDecimal(e.getValue().size()), 2, BigDecimal.ROUND_HALF_UP);

                LinePointDTO point = new LinePointDTO();
                point.setIndex(e.getKey());
                point.setValue(pv);

                dto.getThisYearLine().add(point);
            }
        }

        List<Object[]> lastYearRatios = dao.queryVIPPowerLoadRatio(pair.getValue().getKey(), pair.getValue().getValue(),
                measeIDs);

        vector.clear();

        // Group data by index
        for (Object[] row : lastYearRatios) {
            int measeID = Integer.parseInt(row[0].toString().trim());
            int vipID = mvMap.get(measeID);

            Map<Integer, List<BigDecimal>> line = vector.get(vipID);

            if (null == line) {
                line = new HashMap<Integer, List<BigDecimal>>();

                vector.put(vipID, line);
            }

            long tv = Long.parseLong(row[1].toString().trim());

            int index = Utilities.getIndexOfDay(tv);

            List<BigDecimal> pValues = line.get(index);

            if (null == pValues) {
                pValues = new ArrayList<BigDecimal>();

                line.put(index, pValues);
            }

            BigDecimal ps = row[2] == null ? new BigDecimal(0) : new BigDecimal(row[2].toString().trim());

            pValues.add(ps);
        }

        // Set data into DTO
        for (Entry<Integer, Map<Integer, List<BigDecimal>>> entry : vector.entrySet()) {
            int tranID = entry.getKey();

            PowerLoadRatioYoYDTO dto = map.get(tranID);

            if (null != dto) {
                for (Entry<Integer, List<BigDecimal>> e : entry.getValue().entrySet()) {
                    BigDecimal pv = Utilities.addAll(e.getValue());

                    // Average all day's power values
                    pv = pv.divide(new BigDecimal(e.getValue().size()), 2, BigDecimal.ROUND_HALF_UP);

                    LinePointDTO point = new LinePointDTO();
                    point.setIndex(e.getKey());
                    point.setValue(pv);

                    dto.getLastYearLine().add(point);
                }
            }
        }

        results.addAll(map.values());

        return results;
    }

    /**
     * Get year on year time stamp
     * 
     * @param timeStamp
     * @return <This year<last month start, last month end>, Last year<last month start, last month end>>
     */
    public Pair<Pair<Long, Long>, Pair<Long, Long>> getYoYTimeStamp(final long timeStamp) {
        Date base = new Date(timeStamp);

        Calendar cThisYear = Calendar.getInstance();
        Calendar cLastYear = Calendar.getInstance();
        cThisYear.setTime(base);
        cLastYear.setTime(base);
        cLastYear.set(Calendar.YEAR, cLastYear.get(Calendar.YEAR) - 1);

        return new Pair<Pair<Long, Long>, Pair<Long, Long>>(getMonthStartAndEndTimeStamp(cThisYear),
                getMonthStartAndEndTimeStamp(cLastYear));
    }

    /**
     * Get start and end time stamp of given calendar
     * 
     * @param c
     * @return <Last month start, Last month end>
     */
    public Pair<Long, Long> getMonthStartAndEndTimeStamp(final Calendar c) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println("Current date: " + simpleDateFormat.format(c.getTime()));

        long start = 0;
        long end = 0;

        // Set calendar to first day of current month
        c.set(Calendar.DAY_OF_MONTH, 1);

        System.out.println("Current month first day: " + simpleDateFormat.format(c.getTime()));

        // Set calendar to the last moment of last month
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        end = c.getTimeInMillis() / 1000;

        System.out.println("The last day of last month: " + simpleDateFormat.format(c.getTime()));

        // Set calendar to the first day of last month
        c.set(Calendar.DAY_OF_MONTH, 1);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        start = c.getTimeInMillis() / 1000;

        System.out.println("The start time of last month: " + simpleDateFormat.format(c.getTime()));

        return new Pair<Long, Long>(start, end);
    }

    /**
     * Get industry cluster result
     * 
     * @return
     */
    public IndustryCluster getIndustryCluster() {
        // List<IndustryClustered> ins = dao.queryAllClusteredIndustries();
        List<Industry> ins = dao.queryAllIndustry();
        List<VIP> vips = dao.queryAllVIPEntities();

        // Key: POJO ID, Value: DTO ID
        Map<Integer, Integer> categoryIDMap = new HashMap<Integer, Integer>();

        // Key: Category ID, Value: Item count
        Map<Integer, Integer> categoryCountMap = new HashMap<Integer, Integer>();

        IndustryCluster dto = new IndustryCluster();

        if (null != ins && !ins.isEmpty()) {
            int nodeID = 0;

            for (int i = 0; i < ins.size(); i++, nodeID++) {
                Industry pojo = ins.get(i);

                IndustryClusterCategory c = new IndustryClusterCategory();
                c.setName(pojo.getName());

                dto.getCategories().add(c);

                IndustryClusterNode node = new IndustryClusterNode();
                node.setCategory(i);
                node.setName(pojo.getName());
                node.setValue(1);

                categoryIDMap.put(pojo.getId(), nodeID);

                dto.getNodes().add(node);
            }

            if (null != vips && !vips.isEmpty()) {
                for (int i = 0; i < vips.size(); i++, nodeID++) {
                    VIP pojo = vips.get(i);

                    IndustryClusterNode node = new IndustryClusterNode();
                    node.setCategory(categoryIDMap.get(pojo.getIndustryOld()));
                    node.setName(pojo.getName());
                    node.setValue(2);

                    dto.getNodes().add(node);

                    Integer count = categoryCountMap.get(node.getCategory());

                    if (null == count) {
                        categoryCountMap.put(node.getCategory(), 1);
                    } else {
                        categoryCountMap.put(node.getCategory(), count + 1);
                    }

                    IndustryClusterLink link = new IndustryClusterLink();

                    link.setSource(node.getCategory());
                    link.setTarget(nodeID);

                    dto.getLinks().add(link);
                }

                for (int i = 0; i < ins.size(); i++) {
                    IndustryClusterCategory c = dto.getCategories().get(i);

                    String newName = c.getName() + "[" + categoryCountMap.get(i) + "]";

                    c.setName(newName);
                }
            }
        }

        return dto;
    }
}