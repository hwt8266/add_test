/**
 * DTO for power load feature
 */
package com.siemens.ct.pes.powerload.trans.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.entities.FeatureLineDTO;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load feature
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadFeatureEntityDTO {

    private int id;

    private String name;

    private List<FeatureLineDTO> featureD;

    private List<FeatureLineDTO> featureM;

    private List<FeatureLineDTO> featureQ;

    /**
     * Default constructor
     */
    public PowerLoadFeatureEntityDTO() {
        id = CommonDefine.INIT_VALUE;
        name = CommonDefine.EMPTY;
        featureD = new ArrayList<FeatureLineDTO>();
        featureM = new ArrayList<FeatureLineDTO>();
        featureQ = new ArrayList<FeatureLineDTO>();
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
     * @return the featureD
     */
    public List<FeatureLineDTO> getFeatureD() {
        return featureD;
    }

    /**
     * @param featureD
     *            the featureD to set
     */
    public void setFeatureD(List<FeatureLineDTO> featureD) {
        this.featureD = featureD;
    }

    /**
     * @return the featureM
     */
    public List<FeatureLineDTO> getFeatureM() {
        return featureM;
    }

    /**
     * @param featureM
     *            the featureM to set
     */
    public void setFeatureM(List<FeatureLineDTO> featureM) {
        this.featureM = featureM;
    }

    /**
     * @return the featureQ
     */
    public List<FeatureLineDTO> getFeatureQ() {
        return featureQ;
    }

    /**
     * @param featureQ
     *            the featureQ to set
     */
    public void setFeatureQ(List<FeatureLineDTO> featureQ) {
        this.featureQ = featureQ;
    }
}