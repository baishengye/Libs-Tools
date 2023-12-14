/*
 * Copyright 2018-2019 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaoyingbo.lib_audio;

import androidx.annotation.IntDef;

import com.xiaoyingbo.lib_audio.bean.base.BaseMusicCollectionItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseAuthorItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by KunMinX at 18/9/24
 */
public class PlayingInfoManager<
        MC extends BaseMusicCollectionItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem> {

    /**正在播放的音乐在当前播放模式下的列表中的下标*/
    private int mPlayIndex = 0;
    /**正在播放的音乐在正常列表下的列表的下标*/
    private int mMusicCollectionIndex = 0;
    /**播放模式*/
    private @RepeatMode
    int mRepeatMode;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RepeatMode.SINGLE_CYCLE, RepeatMode.LIST_CYCLE, RepeatMode.RANDOM})
    public @interface RepeatMode {
        /**
         * 单曲循环
         */
        int SINGLE_CYCLE = 0x001;
        /**
         * 列表循环
         */
        int LIST_CYCLE = 0x002;
        /**
         * 随机循环
         */
        int RANDOM = 0x003;
    }

    /**正常顺序的播放列表*/
    private final List<M> mOriginPlayingList = new ArrayList<>();
    /**洗牌后的播放列表*/
    private final List<M> mShufflePlayingList = new ArrayList<>();
    /**播放的音乐来自集合*/
    private MC mMusicCollection;

    /**
     * 判断是否初始化
     */
    boolean isInit() {
        return mMusicCollection != null;
    }

    /**
     * 打乱播放列表
     */
    private void fitShuffle() {
        mShufflePlayingList.clear();
        mShufflePlayingList.addAll(mOriginPlayingList);
        Collections.shuffle(mShufflePlayingList);
    }

    /**
     * 改变播放模式
     */
    @RepeatMode
    int changeMode() {
        switch (mRepeatMode) {
            case RepeatMode.LIST_CYCLE:
                mRepeatMode = RepeatMode.RANDOM;
                break;
            case RepeatMode.RANDOM:
                mRepeatMode = RepeatMode.SINGLE_CYCLE;
                break;
            case RepeatMode.SINGLE_CYCLE:
                mRepeatMode = RepeatMode.LIST_CYCLE;
                break;
        }
        return mRepeatMode;
    }

    /**
     * 获取当前播放音乐的集合
     */
    MC getMusicCollection() {
        return mMusicCollection;
    }

    void setMusicCollection(MC musicCollection) {
        this.mMusicCollection = musicCollection;
        mOriginPlayingList.clear();
        mOriginPlayingList.addAll(mMusicCollection.getMusics());
        fitShuffle();
    }

    /**获取正在播放的列表*/
    List<M> getPlayingList() {
        if (mRepeatMode == RepeatMode.RANDOM) {
            return mShufflePlayingList;
        } else {
            return mOriginPlayingList;
        }
    }

    /**获取正常顺序的播放列表*/
    List<M> getOriginPlayingList() {
        return mOriginPlayingList;
    }

    /**获取正在播放的音乐*/
    M getCurrentPlayingMusic() {
        if (getPlayingList().isEmpty()) {
            return null;
        }
        return getPlayingList().get(mPlayIndex);
    }

    /**获取循环模式*/
    @RepeatMode
    int getRepeatMode() {
        return mRepeatMode;
    }

    /**计算上一曲后正在播放音乐在当前的播放模式下的列表的下标
     * 计算上一曲后正在播放音乐在正常顺序列表的下标*/
    void countPreviousIndex() {
        if (mPlayIndex == 0) {
            mPlayIndex = (getPlayingList().size() - 1);
        } else {
            --mPlayIndex;
        }
        mMusicCollectionIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    /**计算下一曲后正在播放音乐在当前的播放模式下的列表的下标
     * 计算下一曲后正在播放音乐在正常顺序列表的下标*/
    void countNextIndex() {
        if (mPlayIndex == (getPlayingList().size() - 1)) {
            mPlayIndex = 0;
        } else {
            ++mPlayIndex;
        }
        mMusicCollectionIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    int getMusicCollectionIndex() {
        return mMusicCollectionIndex;
    }

    void setMusicCollectionIndex(int musicCollectionIndex) {
        mMusicCollectionIndex = musicCollectionIndex;
        mPlayIndex = getPlayingList().indexOf(mOriginPlayingList.get(mMusicCollectionIndex));
    }
}
