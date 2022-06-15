package com.wisdge.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {
    private String id;
    private String name;
    private String fullname;
    private String jti;
    private String clientId;
    private List<String> roles;
    private String orgId;

    public static LoginUser build(Map<String, Object> mapper) {
        LoginUser user = new LoginUser();
        user.setId((String) mapper.get("userId"));
        user.setName((String) mapper.get("user_name"));
        user.setFullname((String) mapper.get("fullname"));
        user.setJti((String) mapper.get("jti"));
        user.setClientId((String) mapper.get("client_id"));
        user.setOrgId((String) mapper.get("orgId"));
        user.setRoles((List<String>) mapper.get("authorities"));
        return user;
    }
}
