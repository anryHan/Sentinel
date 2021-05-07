package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;

@Service
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private NacosPropertiesConfiguration nacosPropertiesConfiguration;

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<List<FlowRuleEntity>, String> converter;

    @Override
    public void publish(String appName, List<FlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(appName, " app name cannot be empty.");
        if (rules == null) {
            return;
        }
        String dataId = new StringBuffer(appName).append(NacosConstants.DATA_ID_POSTFIX).toString();
        configService.publishConfig(dataId, nacosPropertiesConfiguration.getGroupId(), converter.convert(rules));
    }

}
