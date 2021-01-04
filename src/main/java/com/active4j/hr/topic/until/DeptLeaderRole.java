package com.active4j.hr.topic.until;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weiZiHao
 * @date 2021/1/4
 */
@Data
public class DeptLeaderRole {

    private String roleId;
    private String roleName;
    private String deptId;
    private String deptName;

    private Map<String, String> roleForDept = new HashMap<>();

    DeptLeaderRole() {
        this.roleForDept.put("f7eb65fcc106cfd6c664012ff0660440", "060a8dd57de6e415a18ac0fc67d6baae"); //便民服务中心
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
        this.roleForDept.put("", ""); //
    }
}
