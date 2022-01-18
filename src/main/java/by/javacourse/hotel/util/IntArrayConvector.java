package by.javacourse.hotel.util;

public class IntArrayConvector {

    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String COMMA = ",";
    private static final String QUESTION_MARK = "?";

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
