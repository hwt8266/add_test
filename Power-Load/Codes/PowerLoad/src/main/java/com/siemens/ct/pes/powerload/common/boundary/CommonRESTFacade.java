/**
 * RESTful API for common module
 */
package com.siemens.ct.pes.powerload.common.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.siemens.ct.pes.powerload.common.controller.CommonController;
import com.siemens.ct.pes.powerload.common.entities.DeptDTO;
import com.siemens.ct.pes.powerload.common.entities.DisItemDTO;
import com.siemens.ct.pes.powerload.common.entities.DisItemtParamDTO;
import com.siemens.ct.pes.powerload.common.entities.TranType;
import com.siemens.ct.pes.powerload.common.entities.VLevel;

/**
 * RESTful API for common module
 * 
 * @author Hao Liu
 *
 */
@Path("common")
public class CommonRESTFacade {

    @EJB
    private CommonController controller;

    @POST
    @Path("depttree")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeptDTO> getTreeItem(DisItemtParamDTO param) {
	List<DeptDTO> result = controller.constructDeptTree(param);
	return result;
    }

    @GET
    @Path("displayitem")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DisItemDTO> getDisplayItem(@QueryParam("type") int itemType) {
	return controller.getDisplayItem(itemType);
    }

    @POST
    @Path("displayitem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateDisplayItem(final List<DisItemDTO> param) {
	return controller.updateDisplayItem(param);
    }

    @GET
    @Path("trantype")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TranType> getAllTranType() {
	return controller.getAllTranType();
    }

    @GET
    @Path("vlevel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VLevel> getAllVLevel() {
	return controller.getAllVLevel();
    }
}