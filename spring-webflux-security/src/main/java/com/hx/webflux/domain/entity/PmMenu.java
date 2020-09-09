package com.hx.webflux.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author hx
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("pm_menu")
public class PmMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单名权限名
     */
    private String menuName;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单标识
     */
    private String remark;

    /**
     * 排序,从大到小
     */
    private Integer sort;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Integer isDeleted;


}
