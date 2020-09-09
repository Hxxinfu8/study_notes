package com.hx.webflux.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author hx
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("pm_role")
public class PmRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色标识
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Integer isDeleted;

    /**
     * [1,2,3]的格式存储菜单主键
     */
    private String menuId;


}
