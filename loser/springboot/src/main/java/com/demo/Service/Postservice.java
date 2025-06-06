package com.demo.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.demo.Mapper.Postmapper;
import com.demo.Pojo.post;
import org.springframework.stereotype.Service;

@Service
public class Postservice extends ServiceImpl<Postmapper, post> {
}
