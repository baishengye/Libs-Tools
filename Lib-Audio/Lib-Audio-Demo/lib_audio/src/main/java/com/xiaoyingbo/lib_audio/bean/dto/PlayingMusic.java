package com.xiaoyingbo.lib_audio.bean.dto;

import androidx.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicCollectionItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseAuthorItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicItem;

import java.io.Serializable;
import java.util.List;

public class PlayingMusic<
        MC extends BaseMusicCollectionItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem>
        extends ChangeMusic<MC, M, A> implements Serializable {

    private String nowTime;
    private String allTime;
    private int duration;
    private int playerPosition;

    public PlayingMusic(String nowTime, String allTime) {
        this.nowTime = nowTime;
        this.allTime = allTime;
    }

    public PlayingMusic(MC musicCollection, int playIndex, String nowTime, String allTime) {
        super(musicCollection, playIndex);
        this.nowTime = nowTime;
        this.allTime = allTime;
    }

    public PlayingMusic(String title, String musicCollectionId, String musicId, String img, List<A> authors, String nowTime, String allTime) {
        super(title, musicCollectionId, musicId, img, authors);
        this.nowTime = nowTime;
        this.allTime = allTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getAllTime() {
        return allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
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
                .add("nowTime", nowTime)
                .add("allTime", allTime)
                .add("duration", duration)
                .add("playerPosition", playerPosition)
                .toString();
    }


}
