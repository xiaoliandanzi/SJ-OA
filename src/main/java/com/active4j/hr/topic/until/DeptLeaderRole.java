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

    public DeptLeaderRole() {
        this.roleForDept.put("f7eb65fcc106cfd6c664012ff0660440", "060a8dd57de6e415a18ac0fc67d6baae"); //便民服务中心
        this.roleForDept.put("ae9dacadbb157305a2dd78ab1bd45dee", "197b352f3295219680ae370839ddec0c"); //社区建设办公室
        this.roleForDept.put("c1150728449e35b42fbe86db549477e8", "265ee5a451bc1f3eb1286e9d630bd7f7"); //纪检监察组
        this.roleForDept.put("7ab2ed49706b77e37ed85a63d9cff74c", "2c7c0d686d1fe28c0550a6e7a0137837"); //城市管理办公室
        this.roleForDept.put("f78603cd64a27169b6dd3008d1f5b483", "358461980e88ec0b31e9c456a94284c6"); //党群工作办公室
        this.roleForDept.put("646cb3f83aa4ab1292ff7045a8246138", "47c8580deab0e4cfdedeb746e0407a1a"); //综合行政执法队
        this.roleForDept.put("74dbe9ff9b5d9da38a0e709fed9f49b6", "74da02599b918adebb69dbfd6672265f"); //市民活动中心
        this.roleForDept.put("262e58e7dbc116123de079de2cbc24da", "981cfab8c18788b392fac4c1a7d3c9fd"); //平安建设办公室
        this.roleForDept.put("28684f79c2152ae336756b76cb558834", "be2552efb245783e683b34e23bae76fe"); //民生保障办公室
        this.roleForDept.put("73c66aaf52df970cc6113adc6de19e43", "ca0e39c74c502193ae7339e7f2454f60"); //食药所
        this.roleForDept.put("11fb89e6923d9a1d8d1e8dc58fe352bd", "d206352cbe3ba912fd72699f8b8469f1"); //综合办公室
        this.roleForDept.put("72a5f33531eb2ecad8de3076712b99ff", "f3f55ed9576dc463423e8f37c7c54a38"); //市民诉求处置中心
    }
}
