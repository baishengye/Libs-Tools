package com.xiaoyingbo.lib_audio.bean.base;

import androidx.annotation.NonNull;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**音乐集合
 * 可能来自于专辑,也可能来自于歌单*/
public class BaseMusicCollectionItem<
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem>
        implements Serializable {

    /**歌曲集合id*/
    private String musicCollectionId;
    /**歌曲集合的名称(标题)*/
    private String title;
    /**歌曲集合的描述*/
    private String summary;
    /**歌曲集合的创建者*/
    private List<A> authors;
    /**歌曲集合的封面*/
    private String coverImg;
    /**歌曲集合所有的音乐*/
    private List<M> musics;

    public BaseMusicCollectionItem() {
    }

    public BaseMusicCollectionItem(String musicCollectionId, String title, String summary, List<A> authors, String coverImg, List<M> musics) {
        this.musicCollectionId = musicCollectionId;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.coverImg = coverImg;
        this.musics = musics;
    }

    public String getMusicCollectionId() {
        return musicCollectionId;
    }

    public void setMusicCollectionId(String musicCollectionId) {
        this.musicCollectionId = musicCollectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<A> getAuthors() {
        return authors;
    }

    public void setAuthors(List<A> authors) {
        this.authors = authors;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public List<M> getMusics() {
        return musics;
    }

    public void setMusics(List<M> musics) {
        this.musics = musics;
    }

    @NonNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("musicCollectionId", musicCollectionId)
                .add("title", title)
                .add("summary", summary)
                .add("authors", authors)
                .add("coverImg", coverImg)
                .add("musics", musics)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMusicCollectionItem<?, ?> that = (BaseMusicCollectionItem<?, ?>) o;
        return Objects.equals(musicCollectionId, that.musicCollectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(musicCollectionId);
    }
}
