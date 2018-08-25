package com.songsy.admin.service.impl;

import com.songsy.admin.dao.LoginHistoryMapper;
import com.songsy.admin.entity.Address;
import com.songsy.admin.entity.LoginHistory;
import com.songsy.admin.service.LoginHistoryService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import com.songsy.core.utils.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户登入记录
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
@Service
public class LoginHistoryServiceImpl extends BaseServiceImpl<LoginHistory, Integer> implements LoginHistoryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginHistoryMapper mapper;

    @Override
    public BaseMapper<LoginHistory, Integer> getMappser() {
        return mapper;
    }

    /**
     * 插入用户登录记录
     * @param loginHistory
     */
    @Override
    public void insertUserLoginHistory(LoginHistory loginHistory) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userAgentStr = request.getHeader("user-agent");
        // 获取用户代理
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        Browser browser = userAgent.getBrowser();
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        // ip
        loginHistory.setIp(IpUtils.getRemoteIp(request));
        Address address = IpUtils.getCityInfoByIp(loginHistory.getIp());
        // 省
        loginHistory.setIpLocationProvince(address.getProvince());
        // 市
        loginHistory.setIpLocationCity(address.getCity());
        // 详细地址
        loginHistory.setIpLocation(address.getProvince() +" "+ address.getCity());
        // useragent
        loginHistory.setUseragent(userAgentStr);
        // 浏览器
        loginHistory.setBrowser(userAgent.getBrowser().getName());
        // 设备系统
        loginHistory.setDeviceSystem(userAgent.getOperatingSystem().getName());
        // 电脑还是手机
        loginHistory.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
        // 浏览器版本
        if (version.getVersion() != null) {
            loginHistory.setDeviceVersion(version.getVersion());
        }
        mapper.insertSelective(loginHistory);
    }
    /**
     * 获取地址信息不为空的数据
     * @return
     */
    @Override
    public List<LoginHistory> getAddressInfo(){
        return mapper.getAddressInfo();
    }

}
