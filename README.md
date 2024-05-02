<body>
    <div class="container">
        <h1>ATAC-Civ Plugin Installation Guide</h1>
        <ol>
            <li>You will need an Android device to download ATAK-Civ.</li>
            <li>Go to the <a href="https://tak.gov/">TAK.gov</a> website and download the developer version from there.</li>
            <li>You will also need to download Android Studio to load it onto the device.</li>
            <li>After you have downloaded both of these, open up Android Studio on your computer.</li>
            <li>Start ATAK-Civ on your tablet.</li>
            <li>Load the plugin file into Android Studio.</li>
            <li>Plug the tablet into the computer.</li>
            <li>Once you are on ATAK-Civ and have loaded the plugin in Android Studio, press the green button in the top
                left of Android Studio to build/load the plugin onto the device.</li>
            <li>After the plugin has loaded onto the device, you can use it to either browse the web in ATAK-Civ or move on
                to part 2.</li>
            <li>To continue, you will need to start the Node.js server by running the command <pre>node app.js</pre>
                then wait for it to start.</li>
            <li>After it starts, you can input coordinates into the input route by navigating to the IP you're locally serving
                it on and adding <pre>/input</pre> after your IP:port.</li>
            <li>After you submit the coordinates, navigate back to the tablet and go to the main route, which is <pre>ip:port</pre>.
                After you click "Go", it should drop the marker on the map.</li>
        </ol>
    </div>
</body>

</html>
