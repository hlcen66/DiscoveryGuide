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
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.guide.service.core.CoreImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-a")
public class AFeignImpl extends CoreImpl {
    private static final Logger LOG = LoggerFactory.getLogger(AFeignImpl.class);

    @Autowired
    private BFeign bFeign;

    @GetMapping("/invokea/{value}")
    public String invoke(@PathVariable(value = "value") String value,HttpServletRequest request) {
        value = getPluginInfo(value);
        String sync = request.getHeader("sync");
       return "sync".equals(sync)?callSync(value):async(value);
    }

    private String async(String value) {
        CountDownLatch latch = new CountDownLatch(1);
        Map<String,String> map = new HashMap<>();
        /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(attributes,true);*/
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        /*executorService.submit(() -> {
            String temp = bFeign.invoke(value);
            map.put("result", temp);
            latch.countDown();
        });*/
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String temp = bFeign.invoke(value);
                map.put("result", temp);
                latch.countDown();
            }
        };
        executorService.execute(runnable);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return map.get("result");
    }

    public String callSync(String value){
        return bFeign.invoke(value);
    }

}