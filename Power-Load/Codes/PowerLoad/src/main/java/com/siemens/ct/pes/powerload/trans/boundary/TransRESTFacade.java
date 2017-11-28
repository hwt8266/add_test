/**
 * RESTful API for transformer power load module
 */
package com.siemens.ct.pes.powerload.trans.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.siemens.ct.pes.powerload.common.entities.LinePointDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadEnvImpactDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadQueryParamDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioYoYDTO;
import com.siemens.ct.pes.powerload.common.utils.Utilities;
import com.siemens.ct.pes.powerload.trans.controller.TransController;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadDefaultFCEntityDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadFCQueryParamDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadFeatureEntityDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadModeEntitiesDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadRatioStatisticDTO;

/**
 * RESTful API for transformer power load module
 * 
 * @author Hao Liu
 *
 */
@Path("transmgr")
public class TransRESTFacade {

    @EJB
    private TransController controller;

    @POST
    @Path("powerload/history")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadRatioDTO> getTransPowerLoadRatio(final PowerLoadQueryParamDTO param) {
	List<Integer> ids = Utilities.buildIDList(param.getIds());

	return controller.getTransPowerLoadRatio(param.getStartDate(), param.getEndDate(), ids);
    }

    @POST
    @Path("powerload/feature")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadFeatureEntityDTO> getTransPowerLoadFeatrues(final PowerLoadQueryParamDTO param) {
	List<Integer> ids = Utilities.buildIDList(param.getIds());

	return controller.getTransPowerLoadFeature(ids);
    }

    @POST
    @Path("powerload/ratio/statistic")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PowerLoadRatioStatisticDTO getTransPowerLoadRatioStatistic(final PowerLoadQueryParamDTO param) {
	return controller.getTransPowerLoadRatioStatistic(param.getStartDate(), param.getEndDate(),
		param.getIds().get(0).getId());
    }

    @POST
    @Path("powerload/mode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadModeEntitiesDTO> getTransPowerLoadModes(final PowerLoadQueryParamDTO param) {
	List<Integer> ids = Utilities.buildIDList(param.getIds());

	return controller.getTransPowerLoadMode(ids);
    }

    @POST
    @Path("powerload/impact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadEnvImpactDTO> getTransPowerLoadEnvImpact(final PowerLoadQueryParamDTO param) {
	List<Integer> ids = Utilities.buildIDList(param.getIds());

	return controller.getTransPowerLoadEnvImpact(ids);
    }

    @POST
    @Path("powerload/yoy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadRatioYoYDTO> getTransPowerLoadRatioYoY(final PowerLoadQueryParamDTO param) {
	List<Integer> ids = Utilities.buildIDList(param.getIds());

	return controller.getTransPowerLoadRatioYoY(ids);
    }

    @POST
    @Path("powerload/forecast/default")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadDefaultFCEntityDTO> getTransDefaultPowerLoadFCData(final PowerLoadQueryParamDTO param) {
	List<Integer> ids = Utilities.buildIDList(param.getIds());

	return controller.getTransPowerLoadDefaultFCData(ids);
    }

    @POST
    @Path("powerload/forecast/customize")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<LinePointDTO> getTransCustomizeFCData(final PowerLoadFCQueryParamDTO param) {
	return controller.getPowerLoadFCByIndustryMatrix(param.getId(), param.getIndustryParam());
    }

    @GET
    @Path("powerload/forecast/area")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LinePointDTO> getAreaTranFCData(final @QueryParam("tranid") int tranid) {
	return controller.getAreaPowerLoadFC(tranid);
    }
}