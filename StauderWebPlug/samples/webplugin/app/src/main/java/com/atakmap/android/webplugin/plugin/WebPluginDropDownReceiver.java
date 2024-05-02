package com.atakmap.android.webplugin.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;

import com.atakmap.android.dropdown.DropDown;
import com.atakmap.android.dropdown.DropDownReceiver;
import com.atakmap.android.icons.UserIcon;
import com.atakmap.android.ipc.AtakBroadcast;
import com.atakmap.android.maps.MapView;
import com.atakmap.android.maps.Marker;
import com.atakmap.android.user.PlacePointTool;
import com.atakmap.coremap.maps.coords.GeoPoint;
import com.atakmap.map.CameraController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class WebPluginDropDownReceiver extends DropDownReceiver
        implements DropDown.OnStateListener {

    public static final String ACTION_SHOW_MY_INTENT = "com.staudertech.webplugin.ACTION_SHOW_WEBVIEW";

    private WebView webView;
    private Button goButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private EditText searchBar;
    public String webUrl = "staudertech.com";
    ViewGroup _rootView = null;
    Context _pluginContext;
    Context atakContext;
    double latitude = 0.0;
    double longitude = 0.0;
    double altitude = 0.0;
    boolean button1b = true;
    boolean button2b = true;
    boolean button3b = true;
    private GeoPoint myGeoPoint;

    private void toast(String str) {
        Toast.makeText(getMapView().getContext(), str,
                Toast.LENGTH_LONG).show();
    }

    private void createMarker() {
            PlacePointTool.MarkerCreator mc = new PlacePointTool.MarkerCreator(
                    myGeoPoint = new GeoPoint(latitude, longitude, altitude));
        mc.setUid(UUID.randomUUID().toString());
        mc.setCallsign("WebPlugMarker");
        mc.setType("a-f-A");
        mc.showCotDetails(false);
        mc.setNeverPersist(true);
        Marker m = mc.placePoint();
        m.refresh(getMapView().getMapEventDispatcher(), null,
                this.getClass());
    }
    private void buttonupdate() {
        button1.setVisibility(button1b ? View.VISIBLE : View.GONE);
        button2.setVisibility(button2b ? View.VISIBLE : View.GONE);
        button3.setVisibility(button3b ? View.VISIBLE : View.GONE);

    }

    protected WebPluginDropDownReceiver(MapView mapView, Context context) {
        super(mapView);
        _pluginContext = context;
    }

    @Override
    public void onReceive(Context context, final Intent intent) {
        Log.d("WebPluginLog", _pluginContext+" 2");
        atakContext = context;
        init();
        setRetain(true);
        if (false == isVisible()) {
            if (false == isClosed()) {
                closeDropDown();
            }

            showDropDown(_rootView,
                    getMapView().getContext().getResources().getBoolean(com.atakmap.app.R.bool.isTablet) ? THIRD_WIDTH : FIVE_TWELFTHS_WIDTH,
                    FULL_HEIGHT,
                    FULL_WIDTH, HALF_HEIGHT, false, this);

        }
    }

    @Override
    protected void disposeImpl() {
    }

    @Override
    public void onDropDownSelectionRemoved() {
    }

    @Override
    public void onDropDownClose() {
        //Use if needed
    }

    @Override
    public void onDropDownSizeChanged(double v, double v1) {
    }

    @Override
    public void onDropDownVisible(boolean b) {
        //Use if needed
    }

    public AtakBroadcast.DocumentedIntentFilter getIntentFilter() {
        AtakBroadcast.DocumentedIntentFilter intentFilter = new AtakBroadcast.DocumentedIntentFilter();
        intentFilter.addAction(ACTION_SHOW_MY_INTENT);
        return intentFilter;
    }

    private static boolean isValidURL(String webUrl) {
        try {
            Connection.Response response = Jsoup.connect(webUrl).method(Connection.Method.HEAD).execute();
            return (response.statusCode() == 200);
        } catch (IOException e) {
            return false;
        }
    }

    private void init() {
        if (_rootView == null) {
            LayoutInflater pluginInflater = LayoutInflater.from(_pluginContext);
            _rootView = (ViewGroup) pluginInflater.inflate(R.layout.main_webview, null, false);
            LinearLayout ll = _rootView.findViewById(R.id.webview);
            goButton = _rootView.findViewById(R.id.toolbarBtn);
            searchBar = _rootView.findViewById(R.id.searchBar);
            button1 = _rootView.findViewById(R.id.button1);
            button2 = _rootView.findViewById(R.id.button2);
            button3 = _rootView.findViewById(R.id.button3);
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webUrl = searchBar.getText().toString();

                    if (webUrl.isEmpty()) {
                        Log.i("Emprty URL", "URL field empty when search");
                    } else {
                        webView.loadUrl(webUrl);
                    }
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        public void run() {
                            if (webUrl.contains("10") && webUrl.length() < 25) {
                                CameraController.Programmatic.zoomTo(
                                        getMapView().getRenderer3(),
                                        .00001d, false);
                                for (int i = 0; i < 3; ++i) {
                                    CameraController.Programmatic.panTo(
                                            getMapView().getRenderer3(),
                                            myGeoPoint,
                                            false);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        }
                    }).start();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (webUrl.contains("10") && webUrl.length() < 25) {
                        toast(latitude + " " + longitude + " " + altitude);
                    } else toast("No data available");
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl(webUrl + "/game");
                    webUrl += "/game";
                }
            });

            webView=new WebView(atakContext);
            webView.setVerticalScrollBarEnabled(true);
            webView.setHorizontalScrollBarEnabled(true);

            WebSettings webSettings = webView.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setAllowContentAccess(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setGeolocationEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.i("WebViewClient", "Page finished loading: " + webUrl);

                    new Thread(() -> {

                        if (!webUrl.startsWith("https://") && isValidURL("https://" + webUrl)) {
                            webUrl = "https://" + webUrl;
                        } else if (!webUrl.startsWith("http://")) {
                            webUrl = "http://" + webUrl;
                        }
                        try {

                            Document doc = Jsoup.connect(webUrl).get();

                            Element hiddendivsElement = doc.select(".hiddendivs").first();

                            if (hiddendivsElement != null) {
                                Element latitudeElement = hiddendivsElement.select(".latitude").first();
                                Element longitudeElement = hiddendivsElement.select(".longitude").first();
                                Element altitudeElement = hiddendivsElement.select(".altitude").first();

                                Element button1bElement = hiddendivsElement.select(".button1b").first();
                                Element button2bElement = hiddendivsElement.select(".button2b").first();
                                Element button3bElement = hiddendivsElement.select(".button3b").first();

                                if (latitudeElement != null) {
                                    latitude = Double.parseDouble(latitudeElement.text());
                                }
                                if (longitudeElement != null) {
                                    longitude = Double.parseDouble(longitudeElement.text());
                                }
                                if (altitudeElement != null) {
                                    altitude = Double.parseDouble(altitudeElement.text());
                                }

                                if (button1bElement != null) {
                                    button1b = Boolean.parseBoolean(button1bElement.text());
                                }
                                if (button2bElement != null) {
                                    button2b = Boolean.parseBoolean(button2bElement.text());
                                }
                                if (button3bElement != null) {
                                    button3b = Boolean.parseBoolean(button3bElement.text());
                                }

                                Log.i("Hidden Divs", "Locations: " + latitude + " " + longitude + " " + altitude + " Buttons: " + button1b + " " + button2b + " " + button3b);
                                createMarker();
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        buttonupdate();
                                    }
                                });

                            } else {
                                Log.e("ScrapedData", "Hiddendivs element not found.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });

            webView.setWebChromeClient(new ChromeClient());
            webView.loadUrl(webUrl);
            Log.d("webUrl", "webUrl Loaded");

            webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            ll.addView(webView);
        }
    }

    private static class ChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            com.atakmap.coremap.log.Log.d("webPlugin", consoleMessage.message() + " -- From line "
                    + consoleMessage.lineNumber() + " of "
                    + consoleMessage.sourceId());
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            com.atakmap.coremap.log.Log.d("webPlugin", "loading progress: " + newProgress);
        }
    }
}