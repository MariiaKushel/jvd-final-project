package by.javacourse.hotel.util;

/**
 * {@code ImageEncoder} util class to convert int array to part of sql request
 */
public class IntArrayConvector {

    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String COMMA = ",";
    private static final String QUESTION_MARK = "?";

    /**
     * {@code convertToSqlRequestPart} method to convert int array to part of sql request,
     * which can use like part of sql instruction "IN" in {@link java.sql.PreparedStatement}
     * Examples:
     * [1,2,3] --> (?,?,?)
     * @param array - int array consist values
     * @return part of sql request
     */
    public static String convertToSqlRequestPart(int[] array) {
        StringBuilder sqlRequestPart = new StringBuilder();
        sqlRequestPart.append(LEFT_BRACKET);
        for (int i : array) {
            sqlRequestPart.append(QUESTION_MARK)
                    .append(COMMA);
        }
        sqlRequestPart.deleteCharAt(sqlRequestPart.length() - 1)
                .append(RIGHT_BRACKET);
        return sqlRequestPart.toString();
    }

}
