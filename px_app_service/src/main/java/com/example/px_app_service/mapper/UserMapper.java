package com.example.px_app_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.px_app_service.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
