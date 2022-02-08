package com.cz.myblog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author Author: @achai
 * @since 2021-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_blog")
@ApiModel("博客实体")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "摘要不能为空")
    private String description;

    @NotBlank(message = "内容不能为空")
    private String content;

    // 字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;

    private Integer status;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(value = "is_delete")
    private Integer isDelete;
}
