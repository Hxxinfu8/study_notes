package com.hx.webflux.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author hx
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("pm_user")
public class PmUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 账号名称
     */
    private String account;

    /**
     * 用户名称
     */
    private String username;

    private String password;

    private String salt;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 停用,启用
     */
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Integer isDeleted;
}
