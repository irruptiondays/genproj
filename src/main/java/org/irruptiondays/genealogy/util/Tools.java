package org.irruptiondays.genealogy.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Helper methods
 */
public class Tools {

    public static boolean invalidId(Long id) {
        return id == null || id == 0;
    }

    public static Set iterableToSet(Iterable iterable) {
        Set set = new HashSet();
        iterable.forEach(set::add);
        return set;
    }
}
