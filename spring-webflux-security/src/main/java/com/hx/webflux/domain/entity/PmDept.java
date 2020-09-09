package com.hx.webflux.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author hx
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("pm_dept")
public class PmDept implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 部门名
     */
    private String name;

    /**
     * 部门描述
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private Integer isDeleted;


}
