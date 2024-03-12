package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.IAdUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import com.heima.utils.common.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 管理员用户信息表 服务实现类
 * </p>
 *
 * @author PW
 * @since 2024-03-02
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements IAdUserService {

    @Resource
    AdUserMapper adUserMapper;

    @Override
    public ResponseResult<Map<String, Object>> login(AdUserDto dto) {
        // 1.校验参数
        if(StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 2.查询用户是否存在
        AdUser user = adUserMapper.selectOne(new LambdaQueryWrapper<AdUser>()
                .eq(AdUser::getName, dto.getName()));
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
        }
        // 3.校验密码
        String password = DigestUtils.md5DigestAsHex((dto.getPassword() + user.getSalt()).getBytes());
        if(!password.equals(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        // 4.返回数据（不含password和salt的用户信息 + token）
        Map<String, Object> res = new HashMap<>();
        user.setPassword("");
        user.setSalt("");
        res.put("user", user);
        res.put("token", AppJwtUtil.getToken(user.getId().longValue()));
        return ResponseResult.okResult(res);
    }
}
