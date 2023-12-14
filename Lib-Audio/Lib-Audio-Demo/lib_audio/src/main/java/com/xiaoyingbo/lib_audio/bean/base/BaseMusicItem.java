package com.xiaoyingbo.lib_audio.bean.base;

import java.io.Serializable;
import java.util.List;

/**音乐*/
public class BaseMusicItem<A extends BaseAuthorItem> implements Serializable {

    /**音乐id*/
    private String musicId;
    /**音乐封面*/
    private String coverImg;
    /**音乐url*/
    private String url;
    /**音乐标题*/
    private String title;
    /**音乐创作者*/
    private List<A> authors;

    public BaseMusicItem() {
    }

    public BaseMusicItem(
            String musicId,
            String coverImg,
            String url,
            String title,
            List<A> authors
    ) {
        this.musicId = musicId;
        this.coverImg = coverImg;
        this.url = url;
        this.title = title;
        this.authors = authors;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<A> getAuthors() {
        return authors;
    }

    public void setAuthors(List<A> authors) {
        this.authors = authors;
    }
}