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

package com.xiaoyingbo.lib_audio.liaison;

import android.content.Context;

import com.xiaoyingbo.lib_audio.bean.base.BaseMusicCollectionItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseAuthorItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicItem;
import com.xiaoyingbo.lib_audio.domain.PlayerInfoDispatcher;

public interface IPlayController<
        MC extends BaseMusicCollectionItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem>
        extends IPlayInfoManager<MC, M, A> {

  void init(Context context, IServiceNotifier iServiceNotifier, ICacheProxy iCacheProxy);

  void loadMusicCollection(MC musicCollection);

  void loadMusicCollection(MC musicCollection, int playIndex);

  void playAudio();

  void playAudio(int musicCollectionIndex);

  void playNext();

  void playPrevious();

  void playAgain();

  void togglePlay();

  void pauseAudio();

  void resumeAudio();

  void clear();

  void changeMode();

  boolean isPlaying();

  boolean isPaused();

  boolean isInit();

  void setSeek(int progress);

  String getTrackTime(int progress);

  PlayerInfoDispatcher getDispatcher();
}
