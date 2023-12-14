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

import android.annotation.SuppressLint;
import android.content.Context;

import com.xiaoyingbo.lib_audio.bean.DefaultMusicCollection;
import com.xiaoyingbo.lib_audio.domain.PlayerInfoDispatcher;
import com.xiaoyingbo.lib_audio.liaison.ICacheProxy;
import com.xiaoyingbo.lib_audio.liaison.IPlayController;
import com.xiaoyingbo.lib_audio.liaison.IServiceNotifier;

import java.util.List;

/**
 * Create by KunMinX at 19/10/31
 */
public class DefaultPlayerManager implements IPlayController<DefaultMusicCollection, DefaultMusicCollection.DefaultMusic, DefaultMusicCollection.DefaultArtist> {

    @SuppressLint("StaticFieldLeak")
    private static final DefaultPlayerManager sManager = new DefaultPlayerManager();

    private final PlayerController<DefaultMusicCollection, DefaultMusicCollection.DefaultMusic, DefaultMusicCollection.DefaultArtist> mController;

    private DefaultPlayerManager() {
        mController = new PlayerController<>();
    }

    public static DefaultPlayerManager getInstance() {
        return sManager;
    }

    @Override
    public void init(Context context, IServiceNotifier iServiceNotifier, ICacheProxy iCacheProxy) {
        mController.init(context.getApplicationContext(), iServiceNotifier, iCacheProxy);
    }

    @Override
    public void loadMusicCollection(DefaultMusicCollection musicCollection) {
        mController.loadMusicCollection(musicCollection);
    }

    @Override
    public void loadMusicCollection(DefaultMusicCollection musicCollection, int playIndex) {
        mController.loadMusicCollection(musicCollection, playIndex);
    }

    @Override
    public void playAudio() {
        mController.playAudio();
    }

    @Override
    public void playAudio(int musicCollectionIndex) {
        mController.playAudio(musicCollectionIndex);
    }

    @Override
    public void playNext() {
        mController.playNext();
    }

    @Override
    public void playPrevious() {
        mController.playPrevious();
    }

    @Override
    public void playAgain() {
        mController.playAgain();
    }

    @Override
    public void pauseAudio() {
        mController.pauseAudio();
    }

    @Override
    public void resumeAudio() {
        mController.resumeAudio();
    }

    @Override
    public void clear() {
        mController.clear();
    }

    @Override
    public void changeMode() {
        mController.changeMode();
    }

    @Override
    public boolean isPlaying() {
        return mController.isPlaying();
    }

    @Override
    public boolean isPaused() {
        return mController.isPaused();
    }

    @Override
    public boolean isInit() {
        return mController.isInit();
    }

    @Override
    public void requestLastPlayingInfo() {
        mController.requestLastPlayingInfo();
    }

    @Override
    public void setSeek(int progress) {
        mController.setSeek(progress);
    }

    @Override
    public String getTrackTime(int progress) {
        return mController.getTrackTime(progress);
    }

    @Override
    public PlayerInfoDispatcher getDispatcher() {
        return mController.getDispatcher();
    }

    @Override
    public DefaultMusicCollection getMusicCollection() {
        return mController.getMusicCollection();
    }

    @Override
    public List<DefaultMusicCollection.DefaultMusic> getMusicCollectionMusics() {
        return mController.getMusicCollectionMusics();
    }

    @Override
    public void setChangingPlayingMusic(boolean changingPlayingMusic) {
        mController.setChangingPlayingMusic(changingPlayingMusic);
    }

    @Override
    public int getMusicCollectionIndex() {
        return mController.getMusicCollectionIndex();
    }

    @Override
    public @PlayingInfoManager.RepeatMode
    int getRepeatMode() {
        return mController.getRepeatMode();
    }

    @Override
    public void togglePlay() {
        mController.togglePlay();
    }

    @Override
    public DefaultMusicCollection.DefaultMusic getCurrentPlayingMusic() {
        return mController.getCurrentPlayingMusic();
    }
}
