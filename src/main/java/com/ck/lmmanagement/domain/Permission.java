package com.ck.lmmanagement.domain;

/**
 * @author 01378803
 * @date 2018/11/7 15:08
 * Description  : 权限类
 */
public class Permission extends BaseForm{
    /**
     *  权限id
     */
    private Long id;
    /**
     * 权限编码
     */
    private String permissionCode;
    /**
     * 权限描述
     */
    private String permissionDesc;
    /**
     * 权限路径
     */
    private String resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}
