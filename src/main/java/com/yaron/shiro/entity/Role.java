package com.yaron.shiro.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaron
 * @version 1.0
 * @date 2023/02/07
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
@ApiModel("角色实体类")
public class Role implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(name="id", value = "ID主键")
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(name = "name", value = "角色名称")
    private String name;

    @TableField(exist = false)
    private List<Permission> permissions = new ArrayList<>();

}
