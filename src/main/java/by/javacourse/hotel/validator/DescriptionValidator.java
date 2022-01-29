package by.javacourse.hotel.validator;

public interface DescriptionValidator {
    String WRONG_DATA_MARKER = "Wrong data";

    boolean validateContent (String content);

}
