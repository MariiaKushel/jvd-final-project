package by.javacourse.hotel.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Image extends Entity {
    static Logger logger = LogManager.getLogger();
    private static final byte[] defaultImage;
    private static final String PATH_DEFAULT_IMAGE = "C:\\images\\nophoto.jpg";

    static {
        byte[] imageAsByteArray = null;
        try {
            Path path = Paths.get(PATH_DEFAULT_IMAGE);
            imageAsByteArray = Files.readAllBytes(path);
        } catch (IOException e) {
            logger.debug("IOException in static blok in Image");
            //FIXME add what need to do
        }
        defaultImage = imageAsByteArray;
    }

    private long roomId;
    private byte[] imageContent;
    private boolean preview;

    private Image() {
        this.imageContent = defaultImage;
    }

    public static Builder newBuilder() {
        return new Image().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setEntityId(long entityId) {
            Image.this.setEntityId(entityId);
            return this;
        }

        public Builder setRoomId (long roomId){
            Image.this.roomId = roomId;
            return this;
        }

        public Builder setImageContent(byte[] image){
            Image.this.imageContent = image;
            return this;
        }

        public Builder setPreview(boolean preview){
            Image.this.preview = preview;
            return this;
        }

        public Image build() {
            return Image.this;
        }
    }

    public long getRoomId() {
        return roomId;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public boolean isPreview(){
        return preview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if(getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Image image1 = (Image) o;
        if (roomId != image1.roomId) return false;
        if (preview != image1.preview) return false;
        return Arrays.equals(imageContent, image1.imageContent);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (roomId ^ (roomId >>> 32));
        result = prime * result + Arrays.hashCode(imageContent);
        result = prime * result + (preview ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Image [")
                .append("roomId=")
                .append(roomId)
                .append(", imageContent=")
                .append(Arrays.toString(imageContent))
                .append(", preview=")
                .append(preview)
                .append("]")
                .toString();
    }
}
