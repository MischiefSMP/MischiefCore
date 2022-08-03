package com.mischiefsmp.core;

import com.mischiefsmp.core.api.MischiefPlugin;
import com.mischiefsmp.core.api.utils.FileUtils;
import com.mischiefsmp.core.api.utils.PluginManager;

public class MischiefCore extends MischiefPlugin {
    private static MischiefCore coreInstance;

    @Override
    public void onLoad() {
        coreInstance = this;
    }

    @Override
    public void onFullLoad() {
        PluginManager.init();
    }

    @Override
    public void onUnload() {

    }

    public static MischiefCore getCore() {
        return coreInstance;
    }
}
