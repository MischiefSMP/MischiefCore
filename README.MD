# MischiefCore

Utility Core of the MischiefSMP plugin suite.

## Components

### LogManager
Simple Logging Class

### CacheManager
Cache Utility that allows you to save things, optionally with a lifetime

### LangManager
Load language files in a simple and fast way

### Easy Config Loading
Load .yml files in a more intuitive way using the classes/annotations of the config package

### CMDInfo
A easy way of loading info about commands/subcommands

### Other utilities
Other utilities are featured in the "utils" package

## How to use
If you want your plugin to automatically download MischiefCore add this snippet to your main Plugin class:

```
public void ensureCore(boolean required) {
    if(getServer().getPluginManager().getPlugin("MischiefCore") != null) return;
    getLogger().log(Level.INFO, "Downloading MischiefCore...");
    File pluginFile = new File(new File("").getAbsolutePath() + File.separator + "plugins" + File.separator + "MischiefCore.jar");
    try {
        InputStream in = new URL("https://github.com/MischiefSMP/MischiefCore/releases/latest/download/MischiefCore.jar").openStream();
        Files.copy(in, pluginFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        getServer().getPluginManager().enablePlugin(getServer().getPluginManager().loadPlugin(pluginFile));
    } catch (Exception e) {
        getLogger().log(Level.SEVERE, "Error downloading MischiefCore! Message: " + e.getMessage());
        if(required) getServer().shutdown();
    }
    getLogger().log(Level.INFO, "Done downloading MischiefCore!");
}
```

and then add ```ensureCore(true/false);``` to your onEnable function before you use MischiefCore.

If you call ensureCore with "true" the server will stop if the download fails.

License
----

MIT

Copyright 2022 MischiefSMP.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

