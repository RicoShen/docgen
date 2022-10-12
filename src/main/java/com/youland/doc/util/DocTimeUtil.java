package com.youland.doc.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DocTimeUtil {

    public static String getDocDate(Instant instant){

        if (Objects.isNull(instant)){
            return null;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
                .withLocale(Locale.ENGLISH)
                .withZone(ZoneId.systemDefault());

        return format.format(instant);
    }

    public static void getDoclo(){


    }
}
