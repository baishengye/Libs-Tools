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

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseAuthorItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicCollectionItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicItem;
import com.xiaoyingbo.lib_audio.bean.dto.ChangeMusic;
import com.xiaoyingbo.lib_audio.bean.dto.PlayingMusic;
import com.xiaoyingbo.lib_audio.domain.PlayerEvent;
import com.xiaoyingbo.lib_audio.domain.PlayerInfoDispatcher;
import com.xiaoyingbo.lib_audio.liaison.ICacheProxy;
import com.xiaoyingbo.lib_audio.liaison.IServiceNotifier;

import java.util.List;

public class PlayerController<
        MC extends BaseMusicCollectionItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem> {

    /**播放信息管理者单例*/
    private final PlayingInfoManager<MC, M, A> mPlayingInfoManager = new PlayingInfoManager<>();
    /**是否是改变正在播放的音乐*/
    private boolean mIsChangingPlayingMusic;

    /**缓存接口*/
    private ICacheProxy mICacheProxy;
    /**服务通知接口*/
    private IServiceNotifier mIServiceNotifier;
    /**播放状态分发者*/
    private final PlayerInfoDispatcher mDispatcher = new PlayerInfoDispatcher();

    /**正在播放的音乐*/
    private final PlayingMusic<MC, M, A> mCurrentPlay = new PlayingMusic<>("00:00", "00:00");
    /**改变的音乐*/
    private final ChangeMusic<MC, M, A> mChangeMusic = new ChangeMusic<>();

    /**ExoPlayer播放器*/
    private ExoPlayer mPlayer;
    /**handler：用于update事件*/
    private final static Handler mHandler = new Handler();
    /**更新进度条的runnable*/
    private final Runnable mProgressAction = this::updateProgress;

    /**有音视频缓存和服务通知代理的初始化*/
    public void init(Context context, IServiceNotifier iServiceNotifier, ICacheProxy iCacheProxy) {
        mIServiceNotifier = iServiceNotifier;
        mICacheProxy = iCacheProxy;
        mPlayer = new ExoPlayer.Builder(context).build();
    }

    /**管理者是否已经初始化*/
    public boolean isInit() {
        return mPlayingInfoManager.isInit();
    }

    /**加载新的音乐集合*/
    public void loadMusicCollection(MC MusicCollection) {
        setMusicCollection(MusicCollection, 0);
    }

    /**更新播放进度*/
    private void updateProgress() {
        mCurrentPlay.setNowTime(calculateTime(mPlayer.getCurrentPosition() / 1000));
        mCurrentPlay.setAllTime(calculateTime(mPlayer.getDuration() / 1000));
        mCurrentPlay.setDuration((int) mPlayer.getDuration());
        mCurrentPlay.setPlayerPosition((int) mPlayer.getCurrentPosition());
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PROGRESS, mCurrentPlay));
        if (mCurrentPlay.getAllTime().equals(mCurrentPlay.getNowTime())) {
            if (getRepeatMode() == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) playAgain();
            else playNext();
        }
        mHandler.postDelayed(mProgressAction, 1000);
    }

    /**设置音乐集合
     * @param musicCollection 音乐集合
     * @param musicCollectionIndex 想要播放音乐的音乐在集合中的index*/
    private void setMusicCollection(MC musicCollection, int musicCollectionIndex) {
        mPlayingInfoManager.setMusicCollection(musicCollection);
        mPlayingInfoManager.setMusicCollectionIndex(musicCollectionIndex);
        setChangingPlayingMusic(true);
    }

    /**加载音乐集合
     * @param musicCollection 音乐集合
     * @param musicCollectionIndex 想要播放音乐的音乐在集合中的index*/
    public void loadMusicCollection(MC musicCollection, int musicCollectionIndex) {
        setMusicCollection(musicCollection, musicCollectionIndex);
        playAudio();
    }

    /**是否正在播放*/
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    /**是否已经暂停*/
    public boolean isPaused() {
        return !mPlayer.getPlayWhenReady();
    }

    /**播放音乐
     * @param musicCollectionIndex 音乐在集合中的下标*/
    public void playAudio(int musicCollectionIndex) {
        if (isPlaying() && musicCollectionIndex == mPlayingInfoManager.getMusicCollectionIndex()) {
            return;
        }
        mPlayingInfoManager.setMusicCollectionIndex(musicCollectionIndex);
        setChangingPlayingMusic(true);
        playAudio();
    }

    /**播放音乐*/
    public void playAudio() {
        if (mIsChangingPlayingMusic) getUrlAndPlay();
        else if (isPaused()) resumeAudio();
    }

    /**切换音乐的时候播放*/
    private void getUrlAndPlay() {
        String url;
        M Music;
        Music = mPlayingInfoManager.getCurrentPlayingMusic();
        url = Music.getUrl();

        if (TextUtils.isEmpty(url)) {
            pauseAudio();
        } else {
            MediaItem item;
            if ((url.contains("http:") || url.contains("ftp:") || url.contains("https:"))) {
                item = MediaItem.fromUri(mICacheProxy.getCacheUrl(url));
            } else if (url.contains("storage")) {
                item = MediaItem.fromUri(url);
            } else {
                item = MediaItem.fromUri(Uri.parse("file:///android_asset/" + url));
            }
            mPlayer.setMediaItem(item, true);
            mPlayer.prepare();
            mPlayer.play();
            afterPlay();
        }
    }

    /**exoplayer开始播放之后做的事情(比如刷新ui)*/
    private void afterPlay() {
        setChangingPlayingMusic(false);
        mHandler.post(mProgressAction);
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, false));
        if (mIServiceNotifier != null) mIServiceNotifier.notifyService(true);
    }

    /**刷新播放信息*/
    public void requestLastPlayingInfo() {
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PROGRESS, mCurrentPlay));
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_CHANGE_MUSIC, mChangeMusic));
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, isPaused()));
    }

    /**设置播放进度*/
    public void setSeek(int progress) {
        mPlayer.seekTo(progress);
    }

    /**当前某一进度下的对应时间*/
    public String getTrackTime(int progress) {
        return calculateTime(progress / 1000);
    }

    /**时间戳转化成时间格式*/
    private String calculateTime(long _time) {
        int time = (int) _time;
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            return (minute < 10 ? "0" + minute : "" + minute) + (second < 10 ? ":0" + second : ":" + second);
        } else {
            second = time;
            if (second < 10) return "00:0" + second;
            return "00:" + second;
        }
    }

    /**播放下一曲*/
    public void playNext() {
        mPlayingInfoManager.countNextIndex();
        setChangingPlayingMusic(true);
        playAudio();
    }

    /**播放上一曲*/
    public void playPrevious() {
        mPlayingInfoManager.countPreviousIndex();
        setChangingPlayingMusic(true);
        playAudio();
    }

    /**再次播放*/
    public void playAgain() {
        setChangingPlayingMusic(true);
        playAudio();
    }

    /**暂停播放*/
    public void pauseAudio() {
        mPlayer.pause();
        mHandler.removeCallbacks(mProgressAction);
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, true));
        if (mIServiceNotifier != null) mIServiceNotifier.notifyService(true);
    }

    /**从暂停状态到播放*/
    public void resumeAudio() {
        mPlayer.play();
        mHandler.post(mProgressAction);
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, false));
        if (mIServiceNotifier != null) mIServiceNotifier.notifyService(true);
    }

    /***/
    public void clear() {
        mPlayer.stop();
        mPlayer.clearMediaItems();
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, true));
        resetIsChangingPlayingChapter();
        if (mIServiceNotifier != null) mIServiceNotifier.notifyService(false);
    }

    public void resetIsChangingPlayingChapter() {
        mIsChangingPlayingMusic = true;
        setChangingPlayingMusic(true);
    }

    /**改变播放模式*/
    public void changeMode() {
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_REPEAT_MODE, mPlayingInfoManager.changeMode()));
    }

    /**获取音乐几个*/
    public MC getMusicCollection() {
        return mPlayingInfoManager.getMusicCollection();
    }


    /**获取正在播放的音乐集合中的音乐*/
    public List<M> getMusicCollectionMusics() {
        return mPlayingInfoManager.getOriginPlayingList();
    }

    /**设置切换音乐的需要的相关信息*/
    public void setChangingPlayingMusic(boolean changingPlayingMusic) {
        mIsChangingPlayingMusic = changingPlayingMusic;
        if (mIsChangingPlayingMusic) {
            mChangeMusic.setBaseInfo(mPlayingInfoManager.getMusicCollection(), getCurrentPlayingMusic());
            mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_CHANGE_MUSIC, mChangeMusic));
            mCurrentPlay.setBaseInfo(mPlayingInfoManager.getMusicCollection(), getCurrentPlayingMusic());
            mCurrentPlay.setNowTime("00:00");
            mCurrentPlay.setAllTime("00:00");
            mCurrentPlay.setPlayerPosition(0);
            mCurrentPlay.setDuration(0);
        }
    }

    /**获取当前音乐在集合中的下标*/
    public int getMusicCollectionIndex() {
        return mPlayingInfoManager.getMusicCollectionIndex();
    }

    /**获取播放的循环模式*/
    public @PlayingInfoManager.RepeatMode
    int getRepeatMode() {
        return mPlayingInfoManager.getRepeatMode();
    }

    /**播放音乐状态切换
     * 如果正在播放就暂停,反之则播放音乐*/
    public void togglePlay() {
        if (isPlaying()) pauseAudio();
        else playAudio();
    }

    /**获取正在播放的音乐信息*/
    public M getCurrentPlayingMusic() {
        return mPlayingInfoManager.getCurrentPlayingMusic();
    }

    /**h获取音乐信息分发者*/
    public PlayerInfoDispatcher getDispatcher() {
        return mDispatcher;
    }
}
