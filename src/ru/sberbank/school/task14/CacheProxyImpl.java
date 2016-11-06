package ru.sberbank.school.task14;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


public class CacheProxyImpl implements InvocationHandler {

    private Map<String, Object> cache = new ConcurrentHashMap<>();
    private Object obj;

    public static Object newInstance(Object obj) {
        return Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new CacheProxyImpl(obj));
    }

    private CacheProxyImpl(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object value;
        if (!method.isAnnotationPresent(Cache.class)) {
            value = method.invoke(obj, args);
        } else {

            StringBuilder temp = new StringBuilder(method.getName());
            for (Object arg : args) {
                temp.append(arg.toString());
            }

            String key = temp.toString();

            value = cache.get(key);
            if (value == null) {
                value = method.invoke(obj, args);
                cache.putIfAbsent(key, value);
            }
        }
        return value;
    }
}