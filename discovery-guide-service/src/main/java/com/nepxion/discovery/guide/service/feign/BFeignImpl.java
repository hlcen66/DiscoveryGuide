package com.nepxion.discovery.guide.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.guide.service.context.MyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.guide.service.core.CoreImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class BFeignImpl extends CoreImpl{
    private static final Logger LOG = LoggerFactory.getLogger(BFeignImpl.class);

    @GetMapping("/invokeb/{value}")
    public String invoke(@PathVariable(value = "value") String value,HttpServletRequest request) {
        value = getPluginInfo(value);
        String a = request.getHeader("a");
        return "调用路径:----"+value;
    }
}