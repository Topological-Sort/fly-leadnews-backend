package com.heima.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.IApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.heima.model.common.enums.AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST;
import static com.heima.model.common.enums.AppHttpCodeEnum.LOGIN_PASSWORD_ERROR;

/**
 * <p>
 * APP用户信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-02-16
 */
@Service
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements IApUserService {

    @Resource
    ApUserMapper apUserMapper;

    @Override
    public ResponseResult<Map<String, Object>> login(LoginDto loginDto) {
        String phone = loginDto.getPhone();
        String password = loginDto.getPassword();
        if (!StringUtils.isBlank(phone) && !StringUtils.isBlank(password)) { // 正常登录
            // 1.查询用户
            ApUser apUser = apUserMapper.selectOne(
                    new LambdaQueryWrapper<ApUser>().eq(ApUser::getPhone, phone));
            if (apUser == null) {
                return ResponseResult.errorResult(AP_USER_DATA_NOT_EXIST);
            }
            // 2.对比密码
            String salt = apUser.getSalt();  // salt+md5加密
            password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if (!password.equals(apUser.getPassword())){
                return ResponseResult.errorResult(LOGIN_PASSWORD_ERROR);
            }
            // 3.返回jwt
            log.info(StringUtils.repeat("-", 100));
            log.info("app login userId = {}", apUser.getId());
            log.info(StringUtils.repeat("-", 100));
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(apUser.getId().longValue()));
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);
            return ResponseResult.okResult(map);
        } else {  // 游客登陆
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }
    }
}
