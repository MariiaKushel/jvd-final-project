package by.javacourse.hotel.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * {@code ImageEncoder} util class to help encode image
 */
public class ImageEncoder {

    private static final String CODE_TYPE = "data:image/jpeg;base64,";

    /**
     * {@code encode} method to encode image as byte array to string
     * @param image - image as byte array
     * @return encoded image
     */
    public static String encode(byte[] image) {
        byte[] imageBase64 = Base64.encodeBase64(image, false);
        String imageAsString = StringUtils.newStringUtf8(imageBase64);
        StringBuilder encodedImage = new StringBuilder(CODE_TYPE);
        encodedImage.append(imageAsString);
        return encodedImage.toString();
    }
}
