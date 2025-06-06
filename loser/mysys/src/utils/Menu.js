//普通用户
const menus = [
    {
        type: 'item',
        index: 'allList',
        icon: 'el-icon-s-comment',
        title: '信息公告栏'
    },
    {
        type: 'item',
        index: 'searchMe',
        icon: 'el-icon-document',
        title: '查看已发表帖子',

    }
]
//管理员
const adminMenu = [
    {
        type: 'item',
        index: 'allList',
        icon: 'el-icon-icon-menu',
        title: '信息公告栏'
    }
    ,
    {
        type: 'item',
        index: 'userInfo',
        icon: 'el-icon-setting',
        title: '所有普通用户信息'
    }
]
//超级管理员
const superMenu = [
    {
        type: 'item',
        index: 'log',
        icon: 'el-icon-icon-menu',
        title: 'Navigator Two'
    }
    , {
        type: 'item',
        index: 'wantAd',
        icon: 'el-icon-icon-menu',
        title: 'Navigator Two'
    }

]
const allMenus = [
    {
        type: 'item',
        index: 'allList',
        icon: 'el-icon-s-comment',
        title: '信息公告栏'
    },
    {
        type: 'item',
        index: 'searchMe',
        icon: 'el-icon-document',
        title: '查看已发表帖子',
    },
    {
        type: 'item',
        index: 'info',
        icon: 'el-icon-s-custom',
        title: '所有普通用户信息'
    },
    {
        type: 'item',
        index: 'log',
        icon: 'el-icon-s-order',
        title: '操作日志记录'
    },
    {
        type: 'item',
        index: 'adminInfo',
        icon: 'el-icon-s-check',
        title: '管理员信息表'
    }
    ,
    {
        type: 'item',
        index: 'wantAd',
        icon: 'el-icon-s-cooperation',
        title: '管理员申请表'
    }
];
export { menus, adminMenu, superMenu, allMenus }
