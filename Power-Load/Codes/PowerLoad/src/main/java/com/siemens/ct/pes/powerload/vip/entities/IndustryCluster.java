/**
 * 
 */
package com.siemens.ct.pes.powerload.vip.entities;

import java.util.ArrayList;
import java.util.List;

import com.siemens.ct.pes.powerload.common.utils.CommonDefine;

/**
 * Industry cluster entity
 * 
 * @author Hao Liu
 *
 */
public class IndustryCluster {

    private String type;

    private List<IndustryClusterCategory> categories;

    private List<IndustryClusterNode> nodes;

    private List<IndustryClusterLink> links;

    /**
     * Default constructor
     */
    public IndustryCluster() {
        type = CommonDefine.EMPTY;
        categories = new ArrayList<IndustryClusterCategory>();
        nodes = new ArrayList<IndustryClusterNode>();
        links = new ArrayList<IndustryClusterLink>();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the categories
     */
    public List<IndustryClusterCategory> getCategories() {
        return categories;
    }

    /**
     * @param categories
     *            the categories to set
     */
    public void setCategories(List<IndustryClusterCategory> categories) {
        this.categories = categories;
    }

    /**
     * @return the nodes
     */
    public List<IndustryClusterNode> getNodes() {
        return nodes;
    }

    /**
     * @param nodes
     *            the nodes to set
     */
    public void setNodes(List<IndustryClusterNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * @return the links
     */
    public List<IndustryClusterLink> getLinks() {
        return links;
    }

    /**
     * @param links
     *            the links to set
     */
    public void setLinks(List<IndustryClusterLink> links) {
        this.links = links;
    }
}