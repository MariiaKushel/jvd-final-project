package by.javacourse.hotel.entity;

import by.javacourse.hotel.util.ImageEncoder;
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
        byte[] imageContent = null;
        try {
            Path path = Paths.get(PATH_DEFAULT_IMAGE);
            logger.debug("Path>> " + path);
            imageContent = Files.readAllBytes(path);
            //.debug("byte[] >> " + Arrays.toString(imageContent));
        } catch (IOException e) {
            logger.debug("IOException in static blok in Image");
            //FIXME add what need to do
        }
        defaultImage = imageContent;
    }

    private long roomId;
    private byte[] image;
    private boolean preview;

    private Image() {
        this.image = defaultImage;
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

        public Builder setImage (byte[] image){
            Image.this.image = image;
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

    public byte[] getImage() {
        return image;
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
        return Arrays.equals(image, image1.image);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (roomId ^ (roomId >>> 32));
        result = prime * result + Arrays.hashCode(image);
        result = prime * result + (preview ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Image [")
                .append("roomId=")
                .append(roomId)
                .append(", image=")
                .append(Arrays.toString(image))
                .append(", preview=")
                .append(preview)
                .append("]")
                .toString();
    }
}
