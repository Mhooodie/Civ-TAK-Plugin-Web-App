
package com.atakmap.android.webplugin.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.atak.plugins.impl.AbstractPluginTool;
import com.atak.plugins.impl.PluginLayoutInflater;
import com.atakmap.android.ipc.AtakBroadcast;

import gov.tak.api.util.Disposable;

public class WebPluginTool extends AbstractPluginTool implements Disposable {

    private final static String TAG = "WebPluginTool";

    public WebPluginTool(final Context context) {
        super(context,
                context.getString(R.string.app_name),
                context.getString(R.string.app_name),
                context.getResources().getDrawable(R.drawable.ic_launcher),
                WebPluginDropDownReceiver.ACTION_SHOW_MY_INTENT);
    }

    @Override
    public void dispose() {

    }

}
