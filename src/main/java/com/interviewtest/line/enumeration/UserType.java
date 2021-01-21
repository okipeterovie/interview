
package com.interviewtest.line.enumeration;

/**
 *
 * @author Oki-Peter
 */
public enum UserType {
    
    ADMIN_USER("ROLE_ADMIN"),
    SUB_ADMIN("ROLE_SUB_ADMIN"),
    OUTLET_USER("ROLE_OUTLET_USER");



    private String roleName;
    
    UserType(String roleName){
        this.roleName = roleName;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
}
