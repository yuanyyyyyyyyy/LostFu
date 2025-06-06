package com.zhulang.xfxh.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhulang.xfxh.mapper.MenuMapper;
import com.zhulang.xfxh.pojo.History;
import com.zhulang.xfxh.pojo.Menu;
import com.zhulang.xfxh.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MenuServiceImpl  extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    public List<Menu> findMenuByRoleId(Integer roleId)
    {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return list(queryWrapper);

    }
    public void insertMenu(Menu menu)
    {
        save(menu);
    }
    public void deleteMenu(Integer id)
    {
        removeById(id);
    }
    public void updateMenu(Menu menu)
    {
        updateById(menu);
    }
}
