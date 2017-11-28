/**
 * RESTful API for VIP user power load module
 */
package com.siemens.ct.pes.powerload.vip.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.siemens.ct.pes.powerload.common.entities.PowerLoadEnvImpactDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadQueryParamDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioDTO;
import com.siemens.ct.pes.powerload.common.entities.PowerLoadRatioYoYDTO;
import com.siemens.ct.pes.powerload.common.utils.Utilities;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadFeatureEntityDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadModeEntitiesDTO;
import com.siemens.ct.pes.powerload.trans.entities.PowerLoadRatioStatisticDTO;
import com.siemens.ct.pes.powerload.vip.controller.VIPUserController;
import com.siemens.ct.pes.powerload.vip.entities.IndustryCluster;

/**
 * RESTful API for VIP user power load module
 * 
 * @author Hao Liu
 *
 */
@Path("vipmgr")
public class VIPUserRESTFacade {

    @EJB
    private VIPUserController controller;

    @POST
    @Path("powerload/history")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadRatioDTO> getvipPowerLoadRatio(final PowerLoadQueryParamDTO param) {
        List<Integer> ids = Utilities.buildIDList(param.getIds());

        return controller.getVipsPowerLoadRatio(param.getStartDate(), param.getEndDate(), ids);
    }

    @POST
    @Path("powerload/feature")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadFeatureEntityDTO> getvipPowerLoadFeatrues(final PowerLoadQueryParamDTO param) {
        List<Integer> ids = Utilities.buildIDList(param.getIds());

        return controller.getVipsPowerLoadFeature(ids);
    }

    @POST
    @Path("powerload/ratio/statistic")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PowerLoadRatioStatisticDTO getvipPowerLoadRatioStatistic(final PowerLoadQueryParamDTO param) {
        return controller.getVipsPowerLoadRatioStatistic(param.getStartDate(), param.getEndDate(),
                param.getIds().get(0).getId());
    }

    @POST
    @Path("powerload/mode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadModeEntitiesDTO> getvipPowerLoadModes(final PowerLoadQueryParamDTO param) {
        List<Integer> ids = Utilities.buildIDList(param.getIds());

        return controller.getVipsPowerLoadMode(ids);
    }

    @POST
    @Path("powerload/impact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadEnvImpactDTO> getvipPowerLoadEnvImpact(final PowerLoadQueryParamDTO param) {
        List<Integer> ids = Utilities.buildIDList(param.getIds());

        return controller.getVipsPowerLoadEnvImpact(ids);
    }

    @POST
    @Path("powerload/yoy")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PowerLoadRatioYoYDTO> getvipPowerLoadRatioYoY(final PowerLoadQueryParamDTO param) {
        List<Integer> ids = Utilities.buildIDList(param.getIds());

        return controller.getVipsPowerLoadRatioYoY(ids);
    }

    @GET
    @Path("industry/cluster")
    @Produces(MediaType.APPLICATION_JSON)
    public IndustryCluster getIndustryCluster() {
        return controller.getIndustryCluster();
    }
}