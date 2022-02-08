package com.cz.myblog.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Author: @achai
 * @since 2022-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Commit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String commit;

    private LocalDateTime createTime;


}
