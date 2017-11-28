/**
 * 
 */
package com.siemens.ct.pes.powerload.area.boudary;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.siemens.ct.pes.powerload.area.controller.AreaController;
import com.siemens.ct.pes.powerload.area.entities.AreaDTO;
import com.siemens.ct.pes.powerload.area.entities.AreaMenuItemDTO;
import com.siemens.ct.pes.powerload.area.entities.AreaPTableDTO;
import com.siemens.ct.pes.powerload.area.entities.IDListDTO;
import com.siemens.ct.pes.powerload.area.entities.ModifyTranParamDTO;
import com.siemens.ct.pes.powerload.area.entities.ParamExportForecastDTO;
import com.siemens.ct.pes.powerload.area.entities.ParamTransAreaDTO;
import com.siemens.ct.pes.powerload.area.entities.TranInAreaPTableDTO;
import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * RESTful API for area functions
 * 
 * @author Hao Liu
 *
 */
@Path("areas")
public class AreaRESTFacade {

    @EJB
    private AreaController controller;

    @GET
    @Path("area")
    @Produces(MediaType.APPLICATION_JSON)
    public AreaPTableDTO getAllAreaBasicInfo(final @QueryParam("areaname") String areaName,
	    final @QueryParam("remark") String remark, final @QueryParam("minincrease") BigDecimal minIncrease,
	    final @QueryParam("maxincrease") BigDecimal maxIncrease, final @QueryParam("pagenum") int pageNum) {
	return controller.queryAreaList(areaName, remark, minIncrease, maxIncrease, pageNum);
    }

    @POST
    @Path("area")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommitAreaChanges(final AreaDTO area) {
	boolean result = controller.createOrModifyArea(area);

	return result ? CommonDefine.RESULT_SUCCEED : CommonDefine.RESULT_FAILED;
    }

    @GET
    @Path("areamenuitem")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AreaMenuItemDTO> getAreaMenuItems(final @QueryParam("areaname") String areaName) {
	return controller.getAreaMenuItems(areaName);
    }

    @POST
    @Path("area/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteArea(final IDListDTO areaIds) {
	boolean result = controller.deleteAreaByIds(areaIds.getIds());

	return result ? CommonDefine.RESULT_SUCCEED : CommonDefine.RESULT_FAILED;
    }

    @GET
    @Path("area/trans")
    @Produces(MediaType.APPLICATION_JSON)
    public TranInAreaPTableDTO getTransInArea(final @QueryParam("areaid") int areaId,
	    final @QueryParam("tranname") String tranName, final @QueryParam("trantype") int tranType,
	    final @QueryParam("vlevel") int vLevel, final @QueryParam("deptname") String deptName,
	    final @QueryParam("mincap") BigDecimal minCap, final @QueryParam("maxcap") BigDecimal maxCap,
	    final @QueryParam("minlow") int minLow, final @QueryParam("maxlow") int maxLow,
	    final @QueryParam("minhigh") int minHigh, final @QueryParam("maxhigh") int maxHigh,
	    final @QueryParam("minover") int minOver, final @QueryParam("maxover") int maxOver,
	    final @QueryParam("pagenum") int pageNum) {
	return controller.queryTransInArea(areaId, tranName, tranType, vLevel, deptName, minCap, maxCap, minLow, maxLow,
		minHigh, maxHigh, minOver, maxOver, pageNum);
    }

    @POST
    @Path("area/trans")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyTranIncreaseParam(final ModifyTranParamDTO param) {
	boolean result = controller.modifyTranParameter(param.getAreaId(), param.getTranId(), param.getIncrease());

	return result ? CommonDefine.RESULT_SUCCEED : CommonDefine.RESULT_FAILED;
    }

    @GET
    @Path("area/rawtrans")
    @Produces(MediaType.APPLICATION_JSON)
    public TranInAreaPTableDTO getRawTransForArea(final @QueryParam("areaid") int areaId,
	    final @QueryParam("tranname") String tranName, final @QueryParam("trantype") int tranType,
	    final @QueryParam("vlevel") int vLevel, final @QueryParam("deptname") String deptName,
	    final @QueryParam("mincap") BigDecimal minCap, final @QueryParam("maxcap") BigDecimal maxCap,
	    final @QueryParam("pagenum") int pageNum) {
	return controller.queryRawTransForArea(areaId, tranName, tranType, vLevel, deptName, minCap, maxCap, pageNum);
    }

    @POST
    @Path("area/trans/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteTranInArea(final ParamTransAreaDTO param) {
	boolean result = controller.deleteTransInArea(param.getAreaId(), param.getTranIds());

	return result ? CommonDefine.RESULT_SUCCEED : CommonDefine.RESULT_FAILED;
    }

    @POST
    @Path("area/trans/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addTranToArea(final ParamTransAreaDTO param) {
	boolean result = controller.addTransToArea(param.getAreaId(), param.getTranIds());

	return result ? CommonDefine.RESULT_SUCCEED : CommonDefine.RESULT_FAILED;
    }

    @POST
    @Path("area/export")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String exportAreaForecastData(final @Context HttpServletRequest req, final ParamExportForecastDTO param) {
	Calendar c = Calendar.getInstance();
	c.setTimeInMillis(System.currentTimeMillis());

	StringBuffer date = new StringBuffer();

	date.append(c.get(Calendar.YEAR)).append(c.get(Calendar.MONTH) + 1).append(c.get(Calendar.DAY_OF_MONTH))
		.append(c.get(Calendar.HOUR_OF_DAY)).append(c.get(Calendar.MINUTE)).append(c.get(Calendar.SECOND));

	StringBuffer absoluteFilePath = new StringBuffer();

	absoluteFilePath.append(req.getServletContext().getRealPath("/")).append("reports\\").append("report_")
		.append(date.toString()).append(".xls");

	StringBuffer contextFilePath = new StringBuffer();
	contextFilePath.append(req.getContextPath()).append("\\reports\\").append("report_").append(date.toString())
		.append(".xls");

	String result = contextFilePath.toString();

	try {
	    controller.exportForcastData(param.getAreaIds(), absoluteFilePath.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	    result = CommonDefine.RESULT_FAILED;
	}

	return result;
    }
}