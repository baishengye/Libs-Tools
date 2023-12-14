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

import com.xiaoyingbo.lib_audio.PlayingInfoManager;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicCollectionItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseAuthorItem;
import com.xiaoyingbo.lib_audio.bean.base.BaseMusicItem;

import java.util.List;

public interface IPlayInfoManager<
        B extends BaseMusicCollectionItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseAuthorItem> {

  B getMusicCollection();

  List<M> getMusicCollectionMusics();

  void setChangingPlayingMusic(boolean changingPlayingMusic);

  int getMusicCollectionIndex();

  @PlayingInfoManager.RepeatMode int getRepeatMode();

  M getCurrentPlayingMusic();

  void requestLastPlayingInfo();
}
