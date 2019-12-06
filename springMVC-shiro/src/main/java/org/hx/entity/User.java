package org.hx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Upoint0002
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 唯一邮箱
     */
    private String email;

    /**
     * 唯一电话号码
     */
    private String mobile;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updatedTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户状态，0-正常 1-暂停 2-注销
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
