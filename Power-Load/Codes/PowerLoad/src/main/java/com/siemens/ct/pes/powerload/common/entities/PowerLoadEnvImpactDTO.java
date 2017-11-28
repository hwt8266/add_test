/**
 * 
 */
package com.siemens.ct.pes.powerload.common.entities;

import java.math.BigDecimal;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * DTO for power load environment impact
 * 
 * @author Hao Liu
 *
 */
public class PowerLoadEnvImpactDTO {

    private int id;

    private String name;

    private BigDecimal rainfall;

    private BigDecimal temperature;

    private BigDecimal windSpeed;

    private BigDecimal humidity;

    /**
     * Default constructor
     */
    public PowerLoadEnvImpactDTO() {
        id = CommonDefine.INIT_VALUE;
        name = CommonDefine.EMPTY;
        temperature = new BigDecimal(0);
        windSpeed = new BigDecimal(0);
        humidity = new BigDecimal(0);
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
     * @return the rainfall
     */
    public BigDecimal getRainfall() {
        return rainfall;
    }

    /**
     * @param rainfall
     *            the rainfall to set
     */
    public void setRainfall(BigDecimal rainfall) {
        this.rainfall = rainfall;
    }

    /**
     * @return the temperature
     */
    public BigDecimal getTemperature() {
        return temperature;
    }

    /**
     * @param temperature
     *            the temperature to set
     */
    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the windSpeed
     */
    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    /**
     * @param windSpeed
     *            the windSpeed to set
     */
    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * @return the humidity
     */
    public BigDecimal getHumidity() {
        return humidity;
    }

    /**
     * @param humidity
     *            the humidity to set
     */
    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }
}