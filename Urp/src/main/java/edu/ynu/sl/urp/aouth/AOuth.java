package edu.ynu.sl.urp.aouth;

/**
 * Created by ku on 2014/12/27.
 */

public class AOuth {

    private String auth;
    private String TempGuid;
    private String ImgGuid;

    public AOuth() {

    }


    private static AOuth single = null;

    public synchronized static AOuth getInstance() {
        if (single == null) {
            single = new AOuth();
        }
        return single;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getTempGuid() {
        return TempGuid;
    }

    public void setTempGuid(String tempGuid) {
        TempGuid = tempGuid;
    }

    public String getImgGuid() {
        return ImgGuid;
    }

    public void setImgGuid(String imgGuid) {
        ImgGuid = imgGuid;
    }

}
