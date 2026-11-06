package com.shenm.createfuelburner;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin; // 🌟 新增导入

@WailaPlugin // 🌟 加上这个注解
public class FuelBurnerJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        // 只需要注册提供器，优先级在 Provider 内部设置
        registration.registerBlockComponent(FuelBurnerJadeProvider.INSTANCE, FuelBurnerBlock.class);
    }
}