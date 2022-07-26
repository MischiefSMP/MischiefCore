package com.mischiefsmp.core;

import com.mischiefsmp.core.utils.FileUtils;

public class MischiefCore extends MischiefPlugin {

    @Override
    public void onEnable() {
        FileUtils.init(this);
    }

    @Override
    public void onDisable() {

    }
}
