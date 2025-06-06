package com.demo.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.demo.Mapper.Actionlogmapper;
import com.demo.Mapper.Postmapper;
import com.demo.Pojo.actionlog;
import com.demo.Pojo.post;
import org.springframework.stereotype.Service;

@Service
public class Actionlogservice extends ServiceImpl<Actionlogmapper, actionlog> {
}
