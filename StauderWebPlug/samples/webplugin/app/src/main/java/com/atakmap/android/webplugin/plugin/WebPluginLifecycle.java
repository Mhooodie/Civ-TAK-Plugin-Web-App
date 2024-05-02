
package com.atakmap.android.webplugin.plugin;

import android.util.Log;

import com.atak.plugins.impl.AbstractPlugin;
import com.atak.plugins.impl.AbstractPluginTool;
import com.atak.plugins.impl.IToolbarItem;
import com.atak.plugins.impl.PluginContextProvider;
import com.atakmap.android.webplugin.plugin.WebPluginTool;

import gov.tak.api.plugin.IServiceController;

public class WebPluginLifecycle extends AbstractPlugin {

    public WebPluginLifecycle(IServiceController serviceController) {
        super(serviceController, new AbstractPluginTool[]{
                new WebPluginTool(serviceController.getService(PluginContextProvider.class).getPluginContext())}, new WebPluginMapComponent());
    }
}
