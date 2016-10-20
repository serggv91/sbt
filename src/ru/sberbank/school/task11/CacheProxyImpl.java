package ru.sberbank.school.task11;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.*;


public class CacheProxyImpl implements InvocationHandler {

    private Map<String, Object> cache = new HashMap<>();
    private ArrayList<String> names = new ArrayList<>();
    private Object obj;
    private MemoryType memoryType;
    private String directory;

    public static Object newInstance(Object obj, MemoryType memoryType, String directory) {
        return Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new CacheProxyImpl(obj, memoryType, directory));
    }

    private CacheProxyImpl(Object obj, MemoryType memoryType, String directory) {
        this.obj = obj;
        this.memoryType = memoryType;
        this.directory = directory;
        if (this.memoryType == MemoryType.MEMORY_AND_FILE)
            deserialize();

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object value;
        if (!method.isAnnotationPresent(Cache.class)) {
            value = method.invoke(obj, args);
        } else {

            StringBuilder temp = new StringBuilder(method.getName());
            for (Object arg : args)
                temp.append(arg.toString());

            String key = temp.toString();

            if (this.memoryType == MemoryType.FILE) {
                String filename = key + ".ser";
                try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis)) {
                    value = ois.readObject();
                } catch (Exception e) {
                    value = method.invoke(obj, args);
                    try (FileOutputStream fos = new FileOutputStream(filename);
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                            oos.writeObject(value);
                    } catch (Exception e2) {}
                }

            } else {
                value = cache.get(key);
                if (value == null) {
                    value = method.invoke(obj, args);
                    cache.put(key, value);
                }
            }

        }
        return value;
    }

    private void deserialize() {
        File[] files = new File(this.directory).listFiles();
        for (File file : files) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                 cache.put(file.getName(), ois.readObject());
            } catch (Exception e) {
            }
        }

    }

    enum MemoryType {
        MEMORY, FILE, MEMORY_AND_FILE;
    }
}