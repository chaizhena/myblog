package com.cz.myblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cz.myblog.ResponseResult.Result;
import com.cz.myblog.common.dto.BookDto;
import com.cz.myblog.constant.NcConstant;
import com.cz.myblog.entity.Book;
import com.cz.myblog.entity.Category;
import com.cz.myblog.enums.ResponseEnum;
import com.cz.myblog.exception.NcException;
import com.cz.myblog.mapper.BookMapper;
import com.cz.myblog.service.BookService;
import com.cz.myblog.shiro.JwtToken;
import com.cz.myblog.util.BeanHelper;
import com.cz.myblog.util.UUIDUtils;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Author: @achai
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    BookMapper bookMapper;

    /**
     * 查询所有图书
     * @return 所有图书
     */
    @GetMapping("/books")
    public Result getAllBooks() {
        List<Book> list = bookService.list();
        if (list == null || list.size() <= 0) {
            throw new NcException(ResponseEnum.DATA_NOT_EXISTS.getCode(),ResponseEnum.DATA_NOT_EXISTS.getMessage());
        }
        List<BookDto> bookDtos = BeanHelper.copyWithCollection(list, BookDto.class);
        for (BookDto bookDto : bookDtos) {
            Category category = bookMapper.selectCategoryByBook(bookDto.getId());
            bookDto.setCategory(category);
        }
        return Result.success(bookDtos);
    }

    /**
     * 根据类别查询图书
     * @param cid 类别id
     * @return 结果
     */
    @GetMapping("/categories/{cid}/books")
    public Result getBooksByCategory(@PathVariable("cid") Integer cid) {
        if (cid == null ) {
            throw new NcException(400, "图书类别不存在");
        }
        if (cid == 0) {
            return getAllBooks();
        }

        //根据类别目录进行查询
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getCid, cid);
        List<Book> list = bookService.list(queryWrapper);
        if (list == null || list.size() == 0) {
            throw new NcException(400, "该类别图书不存在");
        }
        List<BookDto> bookDtos = BeanHelper.copyWithCollection(list, BookDto.class);
        for (BookDto bookDto : bookDtos) {
            Category category = bookMapper.selectCategoryByBook(bookDto.getId());
            bookDto.setCategory(category);
        }
        return Result.success(bookDtos);
    }

    /**
     * 搜索功能
     * @param keywords 作者或标题
     * @return 结果
     */
    @GetMapping("/search")
    public Result getBookByKeywords(@RequestParam("keywords") String keywords) {
        if ("".equals(keywords)) {
            return getAllBooks();
        }

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Book::getAuthor, keywords).or().like(Book::getTitle, keywords);
        List<Book> list = bookService.list(queryWrapper);
        if (list == null || list.size() == 0) {
            throw new NcException(400, "您查询的图书不存在");
        }
        List<BookDto> bookDtos = BeanHelper.copyWithCollection(list, BookDto.class);
        for (BookDto bookDto : bookDtos) {
            Category category = bookMapper.selectCategoryByBook(bookDto.getId());
            bookDto.setCategory(category);
        }
        return Result.success(bookDtos);
    }

    /**
     * 增加或者修改图书
     * @param bookDto 前端传来的参数
     * @return 结果
     */
    @RequiresAuthentication()
    @PostMapping("/addOrUpdate")
    public Result addOrUpdate(@RequestBody BookDto bookDto) {


        if (bookDto == null) {
            throw  new NcException(400,ResponseEnum.DATA_NOT_EXISTS.getMessage());
        }

        Book book = BeanHelper.copyProperties(bookDto, Book.class);

        if (book == null) {
            throw  new NcException(400,ResponseEnum.DATA_NOT_EXISTS.getMessage());
        }

        book.setCid(bookDto.getCategory().getId());

        if (book.getId() != null) {
            int i = bookMapper.updateById(book);
            if (i <= 0) {
                throw new NcException(400, "修改失败");
            }
            return Result.success("修改成功");
        }

        int i = bookMapper.insert(book);
        if (i <= 0) {
            throw new NcException(400, "增加失败");
        }
        return Result.success("修改成功");

    }

    /**
     * 删除图书
     * @param book 前端传来的参数
     * @return 结果
     */
    @RequiresAuthentication
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Book book) {

        boolean b = bookService.removeById(book.getId());
        if (!b) {
            throw new NcException(400, "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 添加或更换书的封面
     * @param file 前端传来的封面
     * @return 图片在本低的存储地址
     */
    @PostMapping("/covers")
    public Result coverUpload(MultipartFile file) {

        if (file == null) {
            throw new NcException(ResponseEnum.INVALID_PARAM_ERROR.getMessage());
        }

        String contentType = file.getContentType();
        if (!(contentType != null && NcConstant.ALLOWED_IMG_TYPES.contains(contentType))) {
            throw new NcException(ResponseEnum.INVALID_FILE_TYPE.getMessage());
        }

        if (file.getSize() > NcConstant.maxFileSize) {
            throw new NcException(ResponseEnum.FILE_SIZE_EXCEED_MAX_LIMIT.getMessage());
        }

        String folder = "/root/img";
        File imageFolder = new File(folder);
        //生成唯一名字
        String uuid = UUIDUtils.getUUID();
        //获取原来文件的名字
        String originalFilename = file.getOriginalFilename();
        //截取文件的后缀名
        String name = originalFilename.substring(originalFilename.lastIndexOf("."));
        String imgName = uuid + name;

        File f = new File(imageFolder, imgName);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        try {
            file.transferTo(f);
            String result = "http://localhost:8081/file/" + f.getName();
            return Result.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.success("");
        }

    }

}
