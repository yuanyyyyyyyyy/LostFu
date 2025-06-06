package com.zhulang.xfxh.controller;

import com.zhulang.xfxh.pojo.Menu;
import com.zhulang.xfxh.serviceImpl.MenuServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    MenuServiceImpl service;

    /**
     * 根据角色ID获取菜单列表
     *
     * @param roleId 角色ID，用于指定特定角色对应的菜单权限
     * @return 返回该角色ID对应的菜单列表
     */
    @GetMapping("/MenuList")
    public List<Menu> MenuList(@RequestParam("roleId") Integer roleId) {
        // 调用服务层方法，根据角色ID查询菜单列表
        return service.findMenuByRoleId(roleId);
    }
    /**
     * 通过POST请求删除菜单。
     *
     * @param id 要删除的菜单的ID。
     */
    @PostMapping("/deleteMenu")
    public void deleteMenu(@RequestParam("id") Integer id) {
        service.removeById(id);
    }
    /**
     * 通过POST请求插入菜单。
     * 此方法接收一个Menu对象作为请求体，然后将其插入到数据库中。
     * 使用@RequestBody注解表明请求体中的数据将被绑定到方法参数上。
     *
     * @param menu 菜单对象，包含要插入的菜单的详细信息。
     */
    @PostMapping("/insertMenu")
    public void insertMenu(@RequestBody Menu menu){
        service.insertMenu(menu);
    }
    /**
     * 通过POST请求更新菜单信息。
     *
     * 该方法接收一个Menu对象作为请求体，其中包含了需要更新的菜单的详细信息。
     * 调用服务层的updateMenu方法来执行实际的更新操作。
     * 使用@RequestBody注解将请求体绑定到方法参数上，确保请求中的数据能正确解析并传递给方法。
     *
     * @param menu 包含待更新菜单信息的对象。
     */
    @PostMapping("/updateMenu")
    public void updateMenu(@RequestBody Menu menu){
        service.updateMenu(menu);
    }
}
