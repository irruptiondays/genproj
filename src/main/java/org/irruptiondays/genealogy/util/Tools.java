package org.irruptiondays.genealogy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String dateString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        return dateFormat.format(date).toString();
    }
}
