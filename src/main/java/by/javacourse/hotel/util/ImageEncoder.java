package by.javacourse.hotel.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class ImageEncoder {
    private static final String CODE_TYPE = "data:image/jpeg;base64,";

    public static String encode(byte[] image) {
        byte[] imageBase64 = Base64.encodeBase64(image, false);
        String imageAsString = StringUtils.newStringUtf8(imageBase64);
        StringBuilder encodedImage = new StringBuilder(CODE_TYPE);
        encodedImage.append(imageAsString);
        return encodedImage.toString();
    }
}
