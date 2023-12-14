package com.xiaoyingbo.lib_audio.bean.dto;

import androidx.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicCollectionItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseAuthorItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicItem;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ChangeMusic<
        MC extends BaseMusicCollectionItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem>
        implements Serializable {

    /**音乐的标题*/
    protected String title;
    /**音乐的作者*/
    protected List<A> authors;
    /**音乐的专辑id*/
    protected String musicCollectionId;
    /**音乐的id*/
    protected String musicId;
    /**音乐的图片*/
    protected String img;

    public ChangeMusic() {
    }

    public ChangeMusic(
            String title,
            String musicCollectionId,
            String musicId,
            String img,
            List<A> authors
    ) {
        this.title = title;
        this.musicCollectionId = musicCollectionId;
        this.musicId = musicId;
        this.img = img;
        this.authors = authors;
    }

    public ChangeMusic(MC musicCollection, int playIndex) {
        this.title = musicCollection.getTitle();
        this.musicCollectionId = musicCollection.getMusicCollectionId();
        this.musicId = ((M) musicCollection.getMusics().get(playIndex)).getMusicId();
        this.img = musicCollection.getCoverImg();
        this.authors = musicCollection.getAuthors();
    }

    public void setBaseInfo(MC musicCollection, M music) {
        this.title = music.getTitle();
        this.musicCollectionId = musicCollection.getMusicCollectionId();
        this.musicId = music.getMusicId();
        this.img = music.getCoverImg();
        this.authors = music.getAuthors();
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

    public String getMusicCollectionId() {
        return musicCollectionId;
    }

    public void setMusicCollectionId(String musicCollectionId) {
        this.musicCollectionId = musicCollectionId;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @NonNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("authors", authors)
                .add("musicCollectionId", musicCollectionId)
                .add("musicId", musicId)
                .add("img", img)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeMusic<?, ?, ?> that = (ChangeMusic<?, ?, ?>) o;
        return Objects.equals(musicId, that.musicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(musicId);
    }
}
