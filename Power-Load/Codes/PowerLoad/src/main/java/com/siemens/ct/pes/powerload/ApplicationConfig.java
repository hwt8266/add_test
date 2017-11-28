package com.siemens.ct.pes.powerload;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.siemens.ct.pes.powerload.area.boudary.AreaRESTFacade;
import com.siemens.ct.pes.powerload.auth.boundary.AuthRESTFacade;
import com.siemens.ct.pes.powerload.common.boundary.CommonRESTFacade;
import com.siemens.ct.pes.powerload.trans.boundary.TransRESTFacade;
import com.siemens.ct.pes.powerload.vip.boundary.VIPUserRESTFacade;

/**
 * @author Hao Liu
 */
@ApplicationPath("api")
public class ApplicationConfig extends Application {

    /*
     * (non-Javadoc)
     * 
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> cls = new HashSet<Class<?>>();

        cls.add(AuthRESTFacade.class);
        cls.add(CommonRESTFacade.class);
        cls.add(AreaRESTFacade.class);
        cls.add(TransRESTFacade.class);
        cls.add(VIPUserRESTFacade.class);

        return cls;
    }
}