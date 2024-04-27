package se2.alpha.riskapp.logic;

import java.util.HashMap;
import java.util.Map;

public final class EventBus{
    private static final Map<Class, Consumer<Object>> packageHandler = new HashMap<>();

    public static void invoke(Object packageObj) {
        Consumer<Object> consumer = packageHandler.get(packageObj.getClass());
        if(consumer != null)
            consumer.call(packageObj);
    }

    public static void registerCallback(Class c, Consumer<Object> consumer){
        packageHandler.put(c, consumer);
    }
}

