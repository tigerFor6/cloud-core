package com.wisdge.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "Jwt对象", description = "网关验证后的Jwt对象凭据")
public class JwtObject implements Serializable {
    @ApiModelProperty("Jwt ID")
    private String jti;
    @ApiModelProperty("用户登录名")
    private String user_name;
    @ApiModelProperty("用户账号ID")
    private String userId;
    @ApiModelProperty("用户所属租户ID")
    private String epId;
    @ApiModelProperty("登录的客户端类型")
    private String client_id;
    @ApiModelProperty("用户角色组")
    private List<String> authorities;
}
