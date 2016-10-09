package ru.sberbank.school.task8;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        String path = pluginRootDirectory + File.separator;
        File file = new File(path);
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        URLClassLoader loader = new URLClassLoader(new URL[]{url}, getClass().getClassLoader()) {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (name.contains("ru.sberbank.school"))
                    return findSystemClass(name);
                else
                    return super.loadClass(name);
            }
        };
		
        Plugin result = null;
        try {
            String name = pluginName + "." + pluginClassName;
            result = (Plugin) loader.loadClass(name).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
