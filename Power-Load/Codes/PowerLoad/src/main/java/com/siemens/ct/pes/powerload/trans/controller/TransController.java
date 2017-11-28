/**
 * Controller for transformer power load module
 */
package com.siemens.ct.pes.powerload.trans.controller;

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

import com.siemens.ct.pes.powerload.area.dao.AreaDAO;
import com.siemens.ct.pes.powerload.common.entities.FeatureLineDTO;
import com.siemens.ct.pes.powerload.common.entities.IndustryClustered;
import com.siemens.ct.pes.powerload.common.entities.LinePointDTO;
import com.siemens.ct.pes.powerload.common.entities.Pair;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadEnvImpactDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioYoYDTO;
import com.siemens.ct.pes.powerload.common.entities.Transformer;
import com.siemens.ct.pes.powerload.common.enums.DevelopmentLevel;
import com.siemens.ct.pes.powerload.common.enums.FeatureType;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;
import com.siemens.ct.pes.powerload.common.utils.Utilities;
import com.siemens.ct.pes.powerload.trans.dao.TransDAO;
import com.siemens.ct.pes.powerload.trans.entities.IndustryParamDTO;
import com.siemens.ct.pes.powerload.trans.entities.IndustryUserCountDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadDefaultFCEntityDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadFCLineDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadFeatureEntityDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadModeDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadModeEntitiesDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadRatioStatisticDTO;
import com.siemens.ct.pes.powerload.trans.entities.TransIndustryMatrix;
import com.siemens.ct.pes.powerload.trans.entities.TransIndustryParameter;

/**
 * Controller for transformer power load module
 * 
 * @author Hao Liu
 *
 */
@Stateless
public class TransController {

    @EJB
    private TransDAO tranDao;

    @EJB
    private AreaDAO areaDao;

    private BigDecimal handred = new BigDecimal("100");

    /**
     * Get transformer power load ratio
     * 
     * @param start
     * @param end
     * @param transIDs
     * @return
     */
    public List<PowerLoadRatioDTO> getTransPowerLoadRatio(final long start, final long end,
	    final List<Integer> transIDs) {
	List<PowerLoadRatioDTO> results = new ArrayList<PowerLoadRatioDTO>();

	// Key:Tran_ID
	Map<Integer, PowerLoadRatioDTO> tranMap = new HashMap<Integer, PowerLoadRatioDTO>();

	// Key:Tran_ID, Value:MP_IDs
	Map<Integer, Set<Integer>> tmMap = new HashMap<Integer, Set<Integer>>();

	// Key:MP_ID, Value:Trans_ID
	Map<Integer, Integer> mtMap = new HashMap<Integer, Integer>();

	List<Object[]> rs = tranDao.queryTransBasicInfo(transIDs);

	for (Object[] row : rs) {
	    int tranID = Integer.parseInt(row[0].toString().trim());

	    if (null == tranMap.get(tranID)) {
		PowerLoadRatioDTO dto = new PowerLoadRatioDTO();
		dto.setId(tranID);
		dto.setName(row[1].toString());
		dto.setCapacity(new BigDecimal(row[2].toString()));

		tranMap.put(tranID, dto);
	    }

	    Set<Integer> measeIDs = tmMap.get(tranID);

	    if (null == measeIDs) {
		measeIDs = new HashSet<Integer>();
		tmMap.put(tranID, measeIDs);
	    }

	    int mpID = Integer.parseInt(row[3].toString().trim());
	    measeIDs.add(mpID);

	    mtMap.put(mpID, tranID);
	}

	List<Integer> measeIDs = new ArrayList<>();

	for (Set<Integer> ids : tmMap.values()) {
	    measeIDs.addAll(ids);
	}

	List<Object[]> ratios = tranDao.queryTransPowerLoadRatio(start, end, measeIDs);

	// Tran_ID, <TV, PS>
	Map<Integer, Map<Long, BigDecimal>> map = new HashMap<Integer, Map<Long, BigDecimal>>();

	for (Object[] row : ratios) {
	    int measeID = Integer.parseInt(row[0].toString().trim());
	    int tranID = mtMap.get(measeID);

	    Map<Long, BigDecimal> tranResultMap = map.get(tranID);

	    if (null == tranResultMap) {
		tranResultMap = new HashMap<Long, BigDecimal>();
		map.put(tranID, tranResultMap);
	    }

	    BigDecimal ps = row[2] == null ? new BigDecimal(0) : new BigDecimal(row[2].toString().trim());

	    long tv = Long.parseLong(row[1].toString().trim());
	    BigDecimal sum = tranResultMap.get(tv);

	    if (null == sum) {
		sum = new BigDecimal(0);
	    }

	    sum = sum.add(ps);
	    tranResultMap.put(tv, sum);
	}

	for (Entry<Integer, Map<Long, BigDecimal>> entry : map.entrySet()) {
	    int tranID = entry.getKey();

	    PowerLoadRatioDTO dto = tranMap.get(tranID);

	    List<Long> tvs = new ArrayList<Long>();
	    tvs.addAll(entry.getValue().keySet());
	    Collections.sort(tvs);

	    for (long tv : tvs) {
		LinePointDTO line = new LinePointDTO();

		line.setTv(tv);
		line.setValue(entry.getValue().get(tv).divide(dto.getCapacity(), 4, BigDecimal.ROUND_HALF_UP)
			.multiply(handred));

		dto.getPoints().add(line);
	    }
	}

	results.addAll(tranMap.values());

	return results;
    }

    /**
     * Get transformer power load features
     * 
     * @param ids
     *            Transformer id list
     * @return
     */
    public List<PowerLoadFeatureEntityDTO> getTransPowerLoadFeature(final List<Integer> ids) {
	List<PowerLoadFeatureEntityDTO> results = new ArrayList<PowerLoadFeatureEntityDTO>();

	List<Object[]> rs = tranDao.queryTransPowerLoadFeature(ids);
	List<Transformer> ts = tranDao.queryTransByIDs(ids);

	// Transformer ID, <Feature type, <Feature line index, <Point index,
	// PointDTO>>>
	Map<Integer, Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>>> cache = new HashMap<Integer, Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>>>();
	// Transformer ID, <Feature line index, DynamicParam>>
	Map<Integer, Map<Integer, BigDecimal>> dynamicParamMap = new HashMap<Integer, Map<Integer, BigDecimal>>();
	// Transformer ID, <Feature line index, StaticParam>>
	Map<Integer, Map<Integer, BigDecimal>> staticParamMap = new HashMap<Integer, Map<Integer, BigDecimal>>();
	// Transformer ID, Transformer Name
	Map<Integer, String> transIDNameMap = new HashMap<Integer, String>();

	for (Transformer t : ts) {
	    transIDNameMap.put(t.getId(), t.getDescrb());
	}

	for (Object[] row : rs) {
	    int transID = Integer.valueOf(row[0].toString().trim());
	    int type = Integer.valueOf(row[1].toString().trim());
	    int fIndex = Integer.valueOf(row[2].toString().trim());
	    int lineIndex = Integer.valueOf(row[3].toString().trim());
	    BigDecimal power = new BigDecimal(row[4].toString().trim());

	    // Multiply 100 to satisfy %
	    power = power.multiply(handred);

	    if (type == FeatureType.DAY.getIndex()) {
		Map<Integer, BigDecimal> fdMap = dynamicParamMap.get(transID);

		if (null == fdMap) {
		    fdMap = new HashMap<Integer, BigDecimal>();
		    dynamicParamMap.put(transID, fdMap);
		}

		fdMap.put(fIndex, new BigDecimal(row[5].toString().trim()));

		Map<Integer, BigDecimal> fsMap = staticParamMap.get(transID);

		if (null == fsMap) {
		    fsMap = new HashMap<Integer, BigDecimal>();
		    staticParamMap.put(transID, fsMap);
		}

		fsMap.put(fIndex, new BigDecimal(row[6].toString().trim()));
	    }

	    Map<Integer, Map<Integer, Map<Integer, LinePointDTO>>> transData = cache.get(transID);

	    if (null == transData) {
		transData = new HashMap<Integer, Map<Integer, Map<Integer, LinePointDTO>>>();
		Map<Integer, Map<Integer, LinePointDTO>> featureGroup = new HashMap<Integer, Map<Integer, LinePointDTO>>();
		Map<Integer, LinePointDTO> lineMap = new HashMap<Integer, LinePointDTO>();

		LinePointDTO point = new LinePointDTO();
		point.setIndex(lineIndex);
		point.setValue(power);

		lineMap.put(lineIndex, point);
		featureGroup.put(fIndex, lineMap);
		transData.put(type, featureGroup);
		cache.put(transID, transData);
	    } else {
		Map<Integer, Map<Integer, LinePointDTO>> featureGroup = transData.get(type);

		if (null == featureGroup) {
		    featureGroup = new HashMap<Integer, Map<Integer, LinePointDTO>>();
		    Map<Integer, LinePointDTO> lineMap = new HashMap<Integer, LinePointDTO>();

		    LinePointDTO point = new LinePointDTO();
		    point.setIndex(lineIndex);
		    point.setValue(power);

		    lineMap.put(lineIndex, point);
		    featureGroup.put(fIndex, lineMap);
		    transData.put(type, featureGroup);
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

	    if (transIDNameMap.get(transID) != null) {
		dto.setName(transIDNameMap.get(transID));
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
     * Get transformer power load ratio statistic data
     * 
     * @param startTime
     * @param endTime
     * @param id
     * @return
     */
    public PowerLoadRatioStatisticDTO getTransPowerLoadRatioStatistic(final long startTime, final long endTime,
	    final int id) {
	PowerLoadRatioStatisticDTO dto = new PowerLoadRatioStatisticDTO();
	List<Integer> transIDs = new ArrayList<Integer>();
	transIDs.add(id);

	List<Object[]> rs = tranDao.queryTransBasicInfo(transIDs);

	dto.setId(id);

	if (null != rs && !rs.isEmpty()) {
	    BigDecimal capacity = new BigDecimal(rs.get(0)[2].toString().trim());
	    List<Integer> measeIDs = new ArrayList<Integer>();

	    for (Object[] row : rs) {
		int mpID = Integer.parseInt(row[3].toString().trim());
		measeIDs.add(mpID);
	    }

	    List<Object[]> ratios = tranDao.queryTransPowerLoadRatio(startTime, endTime, measeIDs);
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
     * Get transformer power load modes
     * 
     * @param ids
     *            Transformer id list
     * @return
     */
    public List<PowerLoadModeEntitiesDTO> getTransPowerLoadMode(final List<Integer> ids) {
	List<PowerLoadModeEntitiesDTO> results = new ArrayList<PowerLoadModeEntitiesDTO>();
	Map<Integer, PowerLoadModeEntitiesDTO> map = new HashMap<Integer, PowerLoadModeEntitiesDTO>();

	List<Transformer> trans = tranDao.queryTransByIDs(ids);

	for (Transformer t : trans) {
	    PowerLoadModeEntitiesDTO dto = new PowerLoadModeEntitiesDTO();
	    dto.setId(t.getId());
	    dto.setName(t.getDescrb());
	    map.put(t.getId(), dto);
	}

	// Query limit power load mode
	List<Object[]> lrs = tranDao.queryTransPowerLoadLimit(ids);

	for (Object[] row : lrs) {
	    int transID = Integer.parseInt(row[0].toString().trim());
	    int limitType = Integer.parseInt(row[1].toString().trim());
	    int index = Integer.parseInt(row[2].toString().trim());
	    BigDecimal power = new BigDecimal(row[3].toString().trim());

	    // Multiply 100 to satisfy %
	    power = power.multiply(handred);

	    PowerLoadModeEntitiesDTO dto = map.get(transID);

	    LinePointDTO point = new LinePointDTO();

	    point.setIndex(index);
	    point.setValue(power);

	    if (limitType == 1) {
		dto.getMaxRatio().add(point);
	    } else {
		dto.getMinRatio().add(point);
	    }

	    map.put(transID, dto);
	}

	for (PowerLoadModeEntitiesDTO dto : map.values()) {
	    Collections.sort(dto.getMaxRatio());
	    Collections.sort(dto.getMinRatio());
	}

	// Query power load mode
	List<Object[]> rs = tranDao.queryTransPowerLoadMode(ids);

	// Key: Transformer ID, <Mode index, Mode line>
	Map<Integer, Map<Integer, List<LinePointDTO>>> nMap = new HashMap<Integer, Map<Integer, List<LinePointDTO>>>();

	for (Object[] row : rs) {
	    int transID = Integer.parseInt(row[0].toString().trim());
	    int modeIndex = Integer.parseInt(row[1].toString().trim());
	    int index = Integer.parseInt(row[2].toString().trim());
	    BigDecimal power = new BigDecimal(row[3].toString());

	    // Multiply 100 to satisfy %
	    power = power.multiply(handred);

	    Map<Integer, List<LinePointDTO>> modeMap = nMap.get(transID);

	    if (null == modeMap) {
		modeMap = new HashMap<Integer, List<LinePointDTO>>();

		nMap.put(transID, modeMap);
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
     * Get transformer's power load environment impact factors
     * 
     * @param ids
     *            Transformer id list
     * @return
     */
    public List<PowerLoadEnvImpactDTO> getTransPowerLoadEnvImpact(final List<Integer> ids) {
	List<PowerLoadEnvImpactDTO> results = new ArrayList<PowerLoadEnvImpactDTO>();

	List<Object[]> rs = tranDao.queryTransPowerLoadEnvImpact(ids);

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
     * Get transformer's year on year power load ratio
     * 
     * @param transIDs
     *            List of transformers
     * @return
     */
    public List<PowerLoadRatioYoYDTO> getTransPowerLoadRatioYoY(final List<Integer> transIDs) {
	List<PowerLoadRatioYoYDTO> results = new ArrayList<PowerLoadRatioYoYDTO>();
	Map<Integer, Pair<String, BigDecimal>> maxImpactMap = new HashMap<Integer, Pair<String, BigDecimal>>();

	List<Object[]> impactRS = tranDao.queryTransPowerLoadEnvImpact(transIDs);

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

	// <Trans_ID,YOYDTO>
	Map<Integer, PowerLoadRatioYoYDTO> map = new HashMap<Integer, PowerLoadRatioYoYDTO>();
	// <MP_ID, Trans_ID>
	Map<Integer, Integer> mtMap = new HashMap<Integer, Integer>();

	List<Object[]> rs = tranDao.queryTransBasicInfo(transIDs);

	for (Object[] row : rs) {
	    int tranID = Integer.parseInt(row[0].toString().trim());

	    if (null == map.get(tranID)) {
		PowerLoadRatioYoYDTO dto = new PowerLoadRatioYoYDTO();

		dto.setId(tranID);
		dto.setName(row[1].toString());
		dto.setCapacity(new BigDecimal(row[2].toString().trim()));
		dto.setImpactName(maxImpactMap.get(dto.getId()) == null ? CommonDefine.EMPTY
			: maxImpactMap.get(dto.getId()).getKey());
		dto.setImpactValue(maxImpactMap.get(dto.getId()) == null ? new BigDecimal(0)
			: maxImpactMap.get(dto.getId()).getValue());

		map.put(tranID, dto);
	    }

	    int mpID = Integer.parseInt(row[3].toString().trim());

	    mtMap.put(mpID, tranID);
	}

	List<Integer> measeIDs = new ArrayList<>();
	measeIDs.addAll(mtMap.keySet());

	// <This year<Last month start, Last month end>, Last year<Last month
	// start, Last month end>>
	// Pair<Pair<Long, Long>, Pair<Long, Long>> pair =
	// getYoYTimeStamp(System.currentTimeMillis());
	// <201608_start, 201608_end>, 201508_start, 201508_end>>
	Pair<Pair<Long, Long>, Pair<Long, Long>> pair = new Pair<Pair<Long, Long>, Pair<Long, Long>>(
		new Pair<Long, Long>(1469980800l, 1472659199l), new Pair<Long, Long>(1438358400l, 1441036799l));

	List<Object[]> thisYearRatios = tranDao.queryTransPowerLoadRatio(pair.getKey().getKey(),
		pair.getKey().getValue(), measeIDs);

	// <Tran_ID, <Index, Power values>>
	Map<Integer, Map<Integer, List<BigDecimal>>> vector = new HashMap<Integer, Map<Integer, List<BigDecimal>>>();

	// Group data by index
	for (Object[] row : thisYearRatios) {
	    int measeID = Integer.parseInt(row[0].toString().trim());
	    int tranID = mtMap.get(measeID);

	    Map<Integer, List<BigDecimal>> line = vector.get(tranID);

	    if (null == line) {
		line = new HashMap<Integer, List<BigDecimal>>();

		vector.put(tranID, line);
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

		// Average all day's power value and divide with tansformer's
		// capacity
		pv = pv.divide(new BigDecimal(e.getValue().size()), 2, BigDecimal.ROUND_HALF_UP)
			.divide(dto.getCapacity(), 4, BigDecimal.ROUND_HALF_UP);

		// Multiply 100 to satisfy %
		pv = pv.multiply(handred);

		LinePointDTO point = new LinePointDTO();
		point.setIndex(e.getKey());
		point.setValue(pv);

		dto.getThisYearLine().add(point);
	    }
	}

	List<Object[]> lastYearRatios = tranDao.queryTransPowerLoadRatio(pair.getValue().getKey(),
		pair.getValue().getValue(), measeIDs);

	vector.clear();

	// Group data by index
	for (Object[] row : lastYearRatios) {
	    int measeID = Integer.parseInt(row[0].toString().trim());
	    int tranID = mtMap.get(measeID);

	    Map<Integer, List<BigDecimal>> line = vector.get(tranID);

	    if (null == line) {
		line = new HashMap<Integer, List<BigDecimal>>();

		vector.put(tranID, line);
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
		    pv = pv.divide(new BigDecimal(e.getValue().size()), 2, BigDecimal.ROUND_HALF_UP)
			    .divide(dto.getCapacity(), 4, BigDecimal.ROUND_HALF_UP);

		    // Multiply 100 to satisfy %
		    pv = pv.multiply(handred);

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
     * @return <This year<last month start, last month end>, Last year<last
     *         month start, last month end>>
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
     * Get transformer's default development line
     * 
     * @param ids
     *            List of transformer id
     * @return
     */
    public List<PowerLoadDefaultFCEntityDTO> getTransPowerLoadDefaultFCData(final List<Integer> ids) {
	// Get transformer industry count data
	List<Object[]> rs = tranDao.queryTransVIPIndustryInfo(ids);

	// Trans_ID, DTO
	Map<Integer, PowerLoadDefaultFCEntityDTO> map = new HashMap<Integer, PowerLoadDefaultFCEntityDTO>();
	// <Trans_ID, <Industry_ID, IndustryUserCountDTO>>
	Map<Integer, Map<Integer, IndustryUserCountDTO>> industryCountMap = new HashMap<Integer, Map<Integer, IndustryUserCountDTO>>();

	for (Object[] r : rs) {
	    int transId = Integer.parseInt(r[0].toString().trim());
	    String transName = r[1].toString();

	    PowerLoadDefaultFCEntityDTO dto = map.get(transId);

	    if (null == dto) {
		dto = new PowerLoadDefaultFCEntityDTO();
		dto.setId(transId);
		dto.setName(transName);

		map.put(transId, dto);

		industryCountMap.put(transId, new HashMap<Integer, IndustryUserCountDTO>());
	    }

	    Map<Integer, IndustryUserCountDTO> imap = industryCountMap.get(transId);

	    if (null != r[3]) {
		int industyId = Integer.parseInt(r[3].toString().trim());
		String industryName = r[4].toString();
		IndustryUserCountDTO industryCount = imap.get(industyId);

		if (null == industryCount) {
		    industryCount = new IndustryUserCountDTO();
		    industryCount.setIndustryID(industyId);
		    industryCount.setIndustryName(industryName);
		    industryCount.setUserCount(CommonDefine.INIT_VALUE);

		    imap.put(industyId, industryCount);
		}

		industryCount.setUserCount(industryCount.getUserCount() + 1);
	    }
	}

	// Add transformer industry count into DTO
	for (PowerLoadDefaultFCEntityDTO dto : map.values()) {
	    int transId = dto.getId();

	    dto.getIndustryUserData().addAll(industryCountMap.get(transId).values());
	}

	List<Integer> industryIDs = new ArrayList<Integer>();

	for (Map<Integer, IndustryUserCountDTO> value : industryCountMap.values()) {
	    for (int id : value.keySet()) {
		if (!industryIDs.contains(id)) {
		    industryIDs.add(id);
		}
	    }
	}

	// Get all industry matrixs for calculate parameter
	List<TransIndustryMatrix> matrixs = tranDao.queryPowerLoadFCMatrix(industryIDs);

	// Matrix ID, Development level
	Map<Integer, TransIndustryMatrix> matrixMap = new HashMap<Integer, TransIndustryMatrix>();
	List<Integer> matrixIds = new ArrayList<Integer>();

	Map<Integer, List<TransIndustryMatrix>> transMatrixMap = new HashMap<Integer, List<TransIndustryMatrix>>();

	for (TransIndustryMatrix m : matrixs) {
	    matrixMap.put(m.getId(), m);
	    matrixIds.add(m.getId());

	    for (Entry<Integer, Map<Integer, IndustryUserCountDTO>> entry : industryCountMap.entrySet()) {
		int tranID = entry.getKey();

		List<TransIndustryMatrix> lst = transMatrixMap.get(tranID);

		if (null == lst) {
		    lst = new ArrayList<TransIndustryMatrix>();
		    transMatrixMap.put(tranID, lst);
		}

		for (int industryID : entry.getValue().keySet()) {
		    if (industryID == m.getIndustryId()) {
			lst.add(m);
			break;
		    }
		}
	    }
	}

	// Query power load forecast data from database
	List<Object[]> fcs = tranDao.queryPowerloadFC(ids, matrixIds);

	// <Trans_ID, <Industry_ID, <Dev_Level,LineDTO>>>
	Map<Integer, Map<Integer, Map<Integer, PowerLoadFCLineDTO>>> rMap = new HashMap<Integer, Map<Integer, Map<Integer, PowerLoadFCLineDTO>>>();

	for (Object[] row : fcs) {
	    int transId = Integer.parseInt(row[0].toString().trim());
	    int matrixId = Integer.parseInt(row[1].toString().trim());
	    int lineIndex = Integer.parseInt(row[2].toString().trim());
	    BigDecimal pv = new BigDecimal(row[3].toString().trim());

	    // Multiply 100 to satisfy %
	    pv = pv.multiply(handred);

	    // Industry_ID, <Dev_Level, LineDTO>
	    Map<Integer, Map<Integer, PowerLoadFCLineDTO>> iMap = rMap.get(transId);

	    if (null == iMap) {
		iMap = new HashMap<Integer, Map<Integer, PowerLoadFCLineDTO>>();

		rMap.put(transId, iMap);
	    }

	    // Dev_level, line
	    Map<Integer, PowerLoadFCLineDTO> dMap = iMap.get(matrixMap.get(matrixId).getIndustryId());

	    if (null == dMap) {
		dMap = new HashMap<Integer, PowerLoadFCLineDTO>();

		iMap.put(matrixMap.get(matrixId).getIndustryId(), dMap);
	    }

	    PowerLoadFCLineDTO lineDTO = dMap.get(matrixMap.get(matrixId).getDevLevel());

	    if (null == lineDTO) {
		lineDTO = new PowerLoadFCLineDTO();
		lineDTO.setLineType(matrixMap.get(matrixId).getDevLevel());

		dMap.put(matrixMap.get(matrixId).getDevLevel(), lineDTO);
	    }

	    LinePointDTO point = new LinePointDTO();
	    point.setIndex(lineIndex);
	    point.setValue(pv);

	    lineDTO.getPoints().add(point);
	}

	// <TranID,<IndustryID,FinalParam>>
	Map<Integer, Map<Integer, BigDecimal[]>> tranParamMap = new HashMap<Integer, Map<Integer, BigDecimal[]>>();

	List<IndustryClustered> industrys = tranDao.queryIndustryInfo(industryIDs);
	List<TransIndustryParameter> parameters = tranDao.queryTransIndustryParameter(transMatrixMap);

	for (Entry<Integer, Map<Integer, IndustryUserCountDTO>> entry : industryCountMap.entrySet()) {
	    int tid = entry.getKey();
	    Map<Integer, BigDecimal[]> industryParam = tranParamMap.get(tid);

	    if (null == industryParam) {
		industryParam = new HashMap<Integer, BigDecimal[]>();
		tranParamMap.put(tid, industryParam);
	    }

	    for (int industryID : entry.getValue().keySet()) {
		List<BigDecimal> planParams = new ArrayList<BigDecimal>();
		List<BigDecimal> actualParams = new ArrayList<BigDecimal>();

		for (IndustryClustered in : industrys) {
		    if (in.getId() == industryID) {
			BigDecimal one = new BigDecimal("1");

			planParams.add(in.getHigh().add(one));
			planParams.add(in.getMiddle().add(one));
			planParams.add(in.getLow().add(one));

			break;
		    }
		}

		for (TransIndustryParameter p : parameters) {
		    if (tid == p.getTransId()) {
			for (TransIndustryMatrix matrix : transMatrixMap.get(tid)) {
			    if (p.getMatrixId() == matrix.getId() && matrix.getIndustryId() == industryID) {
				actualParams.add(p.getParam());
			    }
			}
		    }
		}

		industryParam.put(industryID, calculateParam(planParams, actualParams));
	    }
	}

	// Calculate forecast line
	for (Entry<Integer, PowerLoadDefaultFCEntityDTO> entry : map.entrySet()) {
	    PowerLoadDefaultFCEntityDTO dto = entry.getValue();

	    // Transformer ID
	    int tid = entry.getKey();

	    Map<Integer, BigDecimal> hLine = new HashMap<Integer, BigDecimal>();
	    Map<Integer, BigDecimal> mLine = new HashMap<Integer, BigDecimal>();
	    Map<Integer, BigDecimal> lLine = new HashMap<Integer, BigDecimal>();

	    // Industry_ID, <Dev_Level,LineDTO>
	    Map<Integer, Map<Integer, PowerLoadFCLineDTO>> industryLineMap = rMap.get(tid);

	    if (null != industryLineMap) {
		// IndustryID,FinalParam
		Map<Integer, BigDecimal[]> industryParamMap = tranParamMap.get(tid);

		for (Entry<Integer, Map<Integer, PowerLoadFCLineDTO>> en : industryLineMap.entrySet()) {
		    // Industry ID
		    int iid = en.getKey();

		    // Parameters
		    BigDecimal[] params = industryParamMap.get(iid);

		    // High
		    for (LinePointDTO point : en.getValue().get(DevelopmentLevel.HIGH.getIndex()).getPoints()) {
			int index = point.getIndex();
			BigDecimal pv = point.getValue().multiply(params[0]);

			if (null == hLine.get(index)) {
			    hLine.put(index, pv);
			} else {
			    hLine.put(index, hLine.get(index).add(pv));
			}
		    }

		    for (LinePointDTO point : en.getValue().get(DevelopmentLevel.MEDIUM.getIndex()).getPoints()) {
			int index = point.getIndex();
			BigDecimal pv = point.getValue().multiply(params[1]);

			if (null == mLine.get(index)) {
			    mLine.put(index, pv);
			} else {
			    mLine.put(index, mLine.get(index).add(pv));
			}
		    }

		    for (LinePointDTO point : en.getValue().get(DevelopmentLevel.LOW.getIndex()).getPoints()) {
			int index = point.getIndex();
			BigDecimal pv = point.getValue().multiply(params[2]);

			if (null == lLine.get(index)) {
			    lLine.put(index, pv);
			} else {
			    lLine.put(index, lLine.get(index).add(pv));
			}
		    }
		}

		dto.getDefaultFCLine().add(buildLineDTO(DevelopmentLevel.HIGH.getIndex(), hLine));
		dto.getDefaultFCLine().add(buildLineDTO(DevelopmentLevel.MEDIUM.getIndex(), mLine));
		dto.getDefaultFCLine().add(buildLineDTO(DevelopmentLevel.LOW.getIndex(), lLine));
	    }

	}

	List<PowerLoadDefaultFCEntityDTO> results = new ArrayList<PowerLoadDefaultFCEntityDTO>();
	results.addAll(map.values());

	return results;
    }

    /**
     * Calculate parameters
     * 
     * <li>0 - High
     * <li>1 - Middle
     * <li>2 - Low
     * 
     * @param lsta
     * @param lstb
     * @return
     */
    private BigDecimal[] calculateParam(final List<BigDecimal> lsta, final List<BigDecimal> lstb) {
	BigDecimal[] result = new BigDecimal[3];

	if (lsta.size() == 3 && lstb.size() == 3) {
	    List<BigDecimal> tmp = new ArrayList<BigDecimal>();

	    for (BigDecimal a : lsta) {
		for (BigDecimal b : lstb) {
		    tmp.add(a.multiply(b));
		}
	    }

	    Collections.sort(tmp);

	    int index = tmp.size() / 2 + tmp.size() % 2;

	    // High
	    result[0] = tmp.get(tmp.size() - 1);
	    // Middle
	    result[1] = tmp.get(index);
	    // Low
	    result[2] = tmp.get(0);
	}

	return result;
    }

    /**
     * Build line DTO
     * 
     * @param lineType
     * @param lineData
     * @return
     */
    private PowerLoadFCLineDTO buildLineDTO(final int lineType, final Map<Integer, BigDecimal> lineData) {
	// Build High Line
	PowerLoadFCLineDTO lineDTO = new PowerLoadFCLineDTO();
	lineDTO.setLineType(lineType);

	for (Entry<Integer, BigDecimal> en : lineData.entrySet()) {
	    LinePointDTO point = new LinePointDTO();
	    point.setIndex(en.getKey());
	    point.setValue(en.getValue());
	    lineDTO.getPoints().add(point);
	}

	return lineDTO;
    }

    /**
     * Get transformer power load forecast data by industry and development
     * level
     * 
     * @param transId
     * @param matrixs
     * @return
     */
    public List<LinePointDTO> getPowerLoadFCByIndustryMatrix(final int transId, final List<IndustryParamDTO> matrixs) {
	// Industry parameter information: Industry ID, Development level
	Map<Integer, Integer> inParam = new HashMap<Integer, Integer>();

	for (IndustryParamDTO p : matrixs) {
	    inParam.put(p.getIndustryID(), p.getDevLevel());
	}

	List<Integer> industryIDs = new ArrayList<Integer>();

	for (int id : inParam.keySet()) {
	    if (!industryIDs.contains(id)) {
		industryIDs.add(id);
	    }
	}

	// Get all industry matrix for calculate parameter
	List<TransIndustryMatrix> allMatrixs = tranDao.queryPowerLoadFCMatrix(industryIDs);

	// Matrix ID, Development level
	Map<Integer, TransIndustryMatrix> matrixMap = new HashMap<Integer, TransIndustryMatrix>();
	List<Integer> matrixIds = new ArrayList<Integer>();

	Map<Integer, List<TransIndustryMatrix>> transMatrixMap = new HashMap<Integer, List<TransIndustryMatrix>>();

	for (TransIndustryMatrix m : allMatrixs) {
	    matrixMap.put(m.getId(), m);
	    matrixIds.add(m.getId());

	    List<TransIndustryMatrix> lst = transMatrixMap.get(transId);

	    if (null == lst) {
		lst = new ArrayList<TransIndustryMatrix>();
		transMatrixMap.put(transId, lst);
	    }

	    for (int industryID : industryIDs) {
		if (industryID == m.getIndustryId()) {
		    lst.add(m);
		    break;
		}
	    }
	}

	// <IndustryID,FinalParam>
	Map<Integer, BigDecimal[]> industryParam = new HashMap<Integer, BigDecimal[]>();

	List<IndustryClustered> industrys = tranDao.queryIndustryInfo(industryIDs);
	List<TransIndustryParameter> parameters = tranDao.queryTransIndustryParameter(transMatrixMap);

	for (int industryID : industryIDs) {
	    List<BigDecimal> planParams = new ArrayList<BigDecimal>();
	    List<BigDecimal> actualParams = new ArrayList<BigDecimal>();

	    for (IndustryClustered in : industrys) {
		if (in.getId() == industryID) {
		    BigDecimal one = new BigDecimal("1");

		    planParams.add(in.getHigh().add(one));
		    planParams.add(in.getMiddle().add(one));
		    planParams.add(in.getLow().add(one));

		    break;
		}
	    }

	    for (TransIndustryParameter p : parameters) {
		for (TransIndustryMatrix matrix : transMatrixMap.get(transId)) {
		    if (p.getMatrixId() == matrix.getId()) {
			actualParams.add(p.getParam());
		    }
		}
	    }

	    industryParam.put(industryID, calculateParam(planParams, actualParams));
	}

	List<LinePointDTO> result = new ArrayList<LinePointDTO>();

	matrixIds.clear();

	for (Entry<Integer, TransIndustryMatrix> entry : matrixMap.entrySet()) {
	    if (inParam.get(entry.getValue().getIndustryId()) == entry.getValue().getDevLevel()) {
		matrixIds.add(entry.getKey());
	    }
	}

	List<Object[]> rs = tranDao.queryPowerLoadCustomizeFC(transId, matrixIds);

	Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();

	for (int i = 0; i < 96; i++) {
	    map.put(i, new BigDecimal(0));
	}

	for (Object[] r : rs) {
	    int matrixId = Integer.parseInt(r[0].toString());
	    int index = Integer.parseInt(r[1].toString());
	    BigDecimal power = new BigDecimal(r[2].toString());

	    int industryID = matrixMap.get(matrixId).getIndustryId();
	    int devLevel = inParam.get(industryID);

	    BigDecimal parameter = null;

	    if (DevelopmentLevel.HIGH.getIndex() == devLevel) {
		parameter = industryParam.get(industryID)[0];
	    }

	    if (DevelopmentLevel.MEDIUM.getIndex() == devLevel) {
		parameter = industryParam.get(industryID)[1];
	    }

	    if (DevelopmentLevel.LOW.getIndex() == devLevel) {
		parameter = industryParam.get(industryID)[2];
	    }

	    BigDecimal v = map.get(index);

	    map.put(index, v.add(power.multiply(parameter)));
	}

	for (int i = 0; i < 96; i++) {
	    LinePointDTO point = new LinePointDTO();

	    point.setIndex(i);
	    point.setValue(map.get(i).multiply(handred));

	    result.add(point);
	}

	return result;
    }

    /**
     * Get transformer power load forecast data by area increasement
     * 
     * @param transId
     * @return
     */
    public List<LinePointDTO> getAreaPowerLoadFC(final int transId) {
	List<LinePointDTO> result = new ArrayList<LinePointDTO>();

	List<Integer> tranIds = new ArrayList<Integer>();
	tranIds.add(transId);

	List<Object[]> rs = tranDao.queryTransVIPIndustryInfo(tranIds);

	List<Integer> industries = new ArrayList<Integer>();

	// Private transformer
	if (!rs.isEmpty()) {
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

	BigDecimal increase = areaDao.queryAreaTrans(tranIds).get(0).getIncrease();

	List<Object[]> fcs = tranDao.queryPowerloadFC(tranIds, matrixIds);

	Map<Integer, BigDecimal> line = new HashMap<Integer, BigDecimal>();
	List<Integer> lineIndex = new ArrayList<Integer>();

	for (Object[] row : fcs) {
	    int index = (Integer) row[2];
	    BigDecimal pv = (BigDecimal) row[3];

	    if (line.get(index) == null) {
		line.put(index, pv);
		lineIndex.add(index);
	    } else {
		line.put(index, line.get(index).add(pv));
	    }
	}

	Collections.sort(lineIndex);

	BigDecimal one = new BigDecimal("1");

	for (int index : lineIndex) {
	    LinePointDTO point = new LinePointDTO();

	    point.setIndex(index);
	    point.setValue(line.get(index).multiply(increase.divide(handred).add(one)).multiply(handred));

	    result.add(point);
	}

	return result;
    }
}