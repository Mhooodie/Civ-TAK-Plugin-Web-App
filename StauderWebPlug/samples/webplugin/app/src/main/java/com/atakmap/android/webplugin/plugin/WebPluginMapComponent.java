package com.atakmap.android.webplugin.plugin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atakmap.android.dropdown.DropDownMapComponent;
import com.atakmap.android.maps.MapView;

public class WebPluginMapComponent
        extends DropDownMapComponent {
    private WebPluginDropDownReceiver webPluginDropDown = null;
    WebPluginMapComponent() {

    }

    public void onCreate(Context context, Intent intent, MapView view) {
        super.onCreate(context, intent, view);

        webPluginDropDown = new WebPluginDropDownReceiver(view, context);
        registerReceiver(context, webPluginDropDown, webPluginDropDown.getIntentFilter());
    }

    protected void onDestroyImpl(Context context, MapView view) {
        unregisterReceiver(context, webPluginDropDown);
    }
}