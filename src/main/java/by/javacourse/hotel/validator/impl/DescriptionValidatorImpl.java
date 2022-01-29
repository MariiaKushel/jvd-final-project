package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.DescriptionValidator;

public class DescriptionValidatorImpl implements DescriptionValidator {
    private static final String DESCRIPTION_REGEX = "[^><]+";

    private static final DescriptionValidatorImpl instance = new DescriptionValidatorImpl();

    private DescriptionValidatorImpl() {

    }

    public static DescriptionValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateContent(String content) {
        return content.matches(DESCRIPTION_REGEX);
    }
}
