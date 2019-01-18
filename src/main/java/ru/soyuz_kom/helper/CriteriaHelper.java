package ru.soyuz_kom.helper;

import java.util.ArrayList;
import java.util.List;

public class CriteriaHelper {

    public static String parseAndBuildLessAndGreatThan (String key, Object val) {
        String str = "";
        ArrayList data = (ArrayList) val;
        if(data.get(0) != null && data.get(0) != "") {
            str += key + ">=" + data.get(0) + ";";
        }
        if(data.get(1) != null && data.get(1) != "") {
            str += key + "<=" + data.get(1) + ";";
        }
        return str;
    }

    public static String parseAndBuildIn (String key, Object val) {
        String str = "";
        List arr = (List) val;
        if (arr.size() != 0) {
            String listString = arr.toString();
            String result = listString.substring(1, listString.length()-1);
            str += key + "=in=("+result+");";
        }

        return str;
    }

    public static String parseAndBuildEqual (String key, Object val) {
        String str = "";
        str += key + "==" + val + ";";

        return str;
    }

    public static String parseAndBuildEqualMore (String key, Object val) {
        String str = "";
        str += key + "==" + val + "*;";

        return str;
    }

    public static String parseAndBuildEqualBool (String key, Object val) {
        String str = "";
        Boolean bool = (Boolean) val;
        str += key + "==" + bool + ";";

        return str;
    }
}
