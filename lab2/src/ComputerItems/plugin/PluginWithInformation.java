package ComputerItems.plugin;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginWithInformation {

    public String pluginClass;
    public String pluginExtention;
    public String pluginName;
    public Plugin plugin;

    public PluginWithInformation() {
      this.pluginClass = "";
      this.plugin = null;
      this.pluginExtention = "";
      this.pluginName = "Without encoding";
    }

    public PluginWithInformation(File pluginFile){
        try {


            JarFile jarFile = new JarFile(pluginFile);
            Enumeration en = jarFile.entries();
            Properties properties = new Properties();
            while (en.hasMoreElements()) {
                JarEntry entryFile = (JarEntry) en.nextElement();
                if (entryFile.getName().equals("plugins.properties")) {
                    try (InputStream in = jarFile.getInputStream(entryFile)) {
                        properties.load(in);
                    }
                }
            }
            this.pluginName = properties.getProperty("name");
            this.pluginClass = properties.getProperty("class");
            this.pluginExtention = properties.getProperty("extension");

            ClassLoader loader = URLClassLoader.newInstance(
                    new URL[] {pluginFile.toURI().toURL()},
                    getClass().getClassLoader()

            );

            Class pluginClass = loader.loadClass(this.pluginClass);
            this.plugin = (Plugin) pluginClass.newInstance();

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}
