
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

public class TransferObject implements Serializable {
    private int state = 0;
    private String status;
    // Image data ; state = 1
    private int imageheight;
    private int imagewidth;
    private byte[] imagedata;

    //File data ; state =2
    private String filename;
    private long filesize;
    private byte[] fileData;
    final private String defaultDestinationFolder = "your default download folder here";

    public int getState() {
        return state;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WritableImage getImage() {
        WritableImage image = new WritableImage(imagewidth, imageheight);
        image.getPixelWriter().setPixels(0, 0, imagewidth, imageheight,
                PixelFormat.getByteBgraInstance(),
                imagedata, 0, imagewidth * 4);
        return image;
    }

    public void setImage(WritableImage image) {
        if (imageheight != image.getHeight() && imagewidth != image.getWidth()) {
            imagedata = new byte[(int) image.getWidth() * (int) image.getHeight() * 4];
        }
        imagewidth = (int) image.getWidth();
        imageheight = (int) image.getHeight();

        image.getPixelReader().getPixels(0, 0, imagewidth, imageheight,
                PixelFormat.getByteBgraInstance(),
                imagedata, 0, imagewidth * 4);
        status = "success";
        state = 1;
    }


    public void setFile(File file) {
        filename = file.getName();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            long len = (int) file.length();
            byte[] filebyte = new byte[(int) len];
            int read = 0;
            int numRead = 0;
            while (read < filebyte.length && (numRead = dis.read(filebyte, read, filebyte.length - read)) >= 0) {
                read = read + numRead;
            }
            filesize = len;
            fileData = filebyte;
            state = 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilename() {
        return filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public byte[] getFileData() {
        return fileData;
    }
    
}