package se2.alpha.riskapp.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBus{
    private static final Map<Class, List<Consumer<Object>>> packageHandler = new HashMap<>();

    public static void invoke(Object packageObj) {
        List<Consumer<Object>> consumerList = packageHandler.get(packageObj.getClass());

        if (consumerList != null){
            for (Consumer consumer : consumerList){
                 consumer.call(packageObj);
            }
        }
    }

    public static void registerCallback(Class c, Consumer<Object> consumer){
        List<Consumer<Object>> subscriptions = getSubscriptionList(c);
        subscriptions.add(consumer);
    }

    private static List<Consumer<Object>> getSubscriptionList(Class c){
        List<Consumer<Object>> subscriptionList = packageHandler.get(c);

        if (subscriptionList == null){
            subscriptionList = new ArrayList<>();
            packageHandler.put(c, subscriptionList);
        }

        return subscriptionList;
    }
}

