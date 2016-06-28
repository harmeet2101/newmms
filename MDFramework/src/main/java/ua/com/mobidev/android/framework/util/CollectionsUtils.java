package ua.com.mobidev.android.framework.util;

import java.util.Map;

public class CollectionsUtils {

    public static  <K,V> boolean equalMaps(Map<K,V> m1, Map<K,V>m2) {
        if (m1.size() != m2.size())
            return false;
        for (K key: m1.keySet())
            if (!m1.get(key).equals(m2.get(key)))
                return false;
        return true;
    }
}
