package io.code.framework.example.mysql.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
import io.code.framework.example.mysql.entity.User;
import io.code.framework.example.mysql.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 分页查询
     *
     * @param user 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryByPage")
    public ApiResponse<Page<User>> queryByPage(@RequestBody User user) {
        Page page = new Page(1, 10);
        return ApiResponseUtil.success(userService.page(page, new QueryWrapper<>(user)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/{id}")
    public ApiResponse<User> queryById(@PathVariable("id") Integer id) {
        return ApiResponseUtil.success(userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ApiResponse<Boolean> add(@RequestBody User user) {
        return ApiResponseUtil.success(userService.save(user));
    }

    /**
     * 编辑数据
     *
     * @param user 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ApiResponse<Boolean> edit(@RequestBody User user) {
        return ApiResponseUtil.success(userService.updateById(user));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public ApiResponse<Boolean> deleteById(@RequestParam Integer id) {
        return ApiResponseUtil.success(userService.removeById(id));
    }

}

