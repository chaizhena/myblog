package com.cz.myblog.mapper;

import com.cz.myblog.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.myblog.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Author: @achai
 * @since 2022-01-06
 */
public interface BookMapper extends BaseMapper<Book> {

    @Select("select * from category where id = (select cid from book where id = #{id})")
    Category selectCategoryByBook(@Param("id") Integer id);

}
