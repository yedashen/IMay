package shen.da.ye.imay.Entities;

/**
 * Created by cy on 2017/9/4 0004 17:40
 */

public class LocalAlbumEntity {
    public String albumName;//相册名称
    public String displayUrl;//显示的相册的url
    public int imageNums;//当前这个相册总共有多少张图片

    public LocalAlbumEntity(String albumName, String displayUrl, int imageNums) {
        this.albumName = albumName;
        this.displayUrl = displayUrl;
        this.imageNums = imageNums;
    }
}
