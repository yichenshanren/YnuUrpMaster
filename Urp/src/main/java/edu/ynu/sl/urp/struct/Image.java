package edu.ynu.sl.urp.struct;

/**
 * Created by ku on 2015/1/5.
 */
public class Image {

    private String title;
    private String imaUrl;
    private String downloadUrl;

    public Image() {
        setTitle("");
        setDownloadUrl("");
        setImaUrl("");
    }

    public Image(String title, String imgUrl) {
        setTitle(title);
        setImaUrl(imgUrl);
        setDownloadUrl("");
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImaUrl() {
        return imaUrl;
    }

    public void setImaUrl(String imaUrl) {
        this.imaUrl = imaUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
