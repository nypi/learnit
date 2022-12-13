import java.nio.file.Paths;

public class ImageTextRecord {
    private final String picturePath;
    private final String name;
    private final String textForSave;

    public ImageTextRecord(String path, String name, String textForSave) {
        this.picturePath = path;
        this.name = name;
        this.textForSave = textForSave.toUpperCase();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getName() {
        return name;
    }

    public String getTextForSave() {
        return textForSave;
    }
}
