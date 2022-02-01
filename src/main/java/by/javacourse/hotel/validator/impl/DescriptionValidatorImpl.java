package by.javacourse.hotel.validator.impl;

import by.javacourse.hotel.validator.DescriptionValidator;

import java.util.Map;

import static by.javacourse.hotel.controller.command.SessionAttribute.*;
import static by.javacourse.hotel.controller.command.SessionAttribute.WRONG_DESCRIPTION_EN_SES;

/**
 * {@code DescriptionValidatorImpl} class implements functional of {@link DescriptionValidator}
 */
public class DescriptionValidatorImpl implements DescriptionValidator {
    private static final String DESCRIPTION_REGEX = "[^><]+";

    private static final DescriptionValidatorImpl instance = new DescriptionValidatorImpl();

    private DescriptionValidatorImpl() {

    }

    public static DescriptionValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateDescriptionData(Map<String, String> descriptionData) {
        boolean isValid = true;
        String descriptionRu = descriptionData.get(DESCRIPTION_RU_SES);
        String descriptionEn = descriptionData.get(DESCRIPTION_EN_SES);
        if (!descriptionRu.matches(DESCRIPTION_REGEX)) {
            descriptionData.put(WRONG_DESCRIPTION_RU_SES, DescriptionValidator.WRONG_DATA_MARKER);
            isValid = false;
        }
        if (!descriptionEn.matches(DESCRIPTION_REGEX)) {
            descriptionData.put(WRONG_DESCRIPTION_EN_SES, DescriptionValidator.WRONG_DATA_MARKER);
            isValid = false;
        }
        return isValid;
    }
}
