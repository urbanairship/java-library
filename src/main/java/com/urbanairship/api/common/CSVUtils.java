package com.urbanairship.api.common;

public class CSVUtils {

    /**
     * Formats an array of strings as a single CSV row.
     *
     * @param columns Array of strings representing the columns of the CSV row.
     * @return A formatted CSV row as a String.
     */
    public static String formatRowForCSV(String[] columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            String column = columns[i];
            sb.append(column);
        }
        return sb.toString();
    }
}