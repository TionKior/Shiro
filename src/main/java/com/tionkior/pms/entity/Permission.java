package com.tionkior.pms.entity;

import lombok.ToString;

import java.io.Serializable;

/**
 * 权限表(Permissions)实体类
 *
 * @author makejava
 * @since 2022-03-12 14:53:04
 */
@ToString
public class Permission implements Serializable {
    private static final long serialVersionUID = -71766948398349092L;
    /**
     * 主键,权限表ID
     */
    private Integer perId;
    /**
     * 资源名称
     */
    private String perName;
    /**
     * 资源类型,menu,button等
     */
    private String perType;
    /**
     * 权限字符串
     */
    private String perPermission;
    /**
     * 访问url地址
     */
    private String perUrl;
    /**
     * 是否可用,1:可用,0:不可用
     */
    private String perAvailable;
    /**
     * 页面地址
     */
    private String perPageUrl;
    /**
     * 父菜单id,一般菜单为0
     */
    private Integer perParentId;


    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public String getPerPermission() {
        return perPermission;
    }

    public void setPerPermission(String perPermission) {
        this.perPermission = perPermission;
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public String getPerAvailable() {
        return perAvailable;
    }

    public void setPerAvailable(String perAvailable) {
        this.perAvailable = perAvailable;
    }

    public String getPerPageUrl() {
        return perPageUrl;
    }

    public void setPerPageUrl(String perPageUrl) {
        this.perPageUrl = perPageUrl;
    }

    public Integer getPerParentId() {
        return perParentId;
    }

    public void setPerParentId(Integer perParentId) {
        this.perParentId = perParentId;
    }

}

