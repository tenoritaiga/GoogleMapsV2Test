GoogleMapsV2Test
================

This is a simple Android activity demonstrating some Maps API v2 features,
such as custom markers, polygon and circle overlays, and snippet text.

To connect to Google Maps, you need to add your own developer API key into a local file.
In the ```res/values``` folder, add a new XML file called ```apikeys.xml``` with a string like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="MapsAPIKey">your_api_key_goes_here</string>
</resources>
```
You can either generate a developer key specifically for this app on Google Console by using the app's SHA1
key, or you can generate a blank key that is usable with any app. When Google prompts you for the SHA1
key and app package name, just don't enter anything and you'll receive a blank key.