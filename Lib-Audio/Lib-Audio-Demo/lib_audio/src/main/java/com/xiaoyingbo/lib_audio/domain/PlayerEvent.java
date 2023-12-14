package com.xiaoyingbo.lib_audio.domain;


import com.xiaoyingbo.lib_audio.PlayingInfoManager;
import com.xiaoyingbo.lib_audio.bean.dto.ChangeMusic;
import com.xiaoyingbo.lib_audio.bean.dto.PlayingMusic;

public class PlayerEvent {
  public final static int EVENT_CHANGE_MUSIC = 1;
  public final static int EVENT_PROGRESS = 2;
  public final static int EVENT_PLAY_STATUS = 3;
  public final static int EVENT_REPEAT_MODE = 4;

  public final int eventId;
  public final ChangeMusic changeMusic;
  public final PlayingMusic playingMusic;
  public final boolean toPause;
  public final @PlayingInfoManager.RepeatMode int repeatMode;

  public PlayerEvent(int eventId,
                     ChangeMusic changeMusic,
                     PlayingMusic playingMusic,
                     boolean toPause,
                     @PlayingInfoManager.RepeatMode int repeatMode) {
    this.eventId = eventId;
    this.changeMusic = changeMusic;
    this.playingMusic = playingMusic;
    this.toPause = toPause;
    this.repeatMode = repeatMode;
  }

  public PlayerEvent(int eventId, ChangeMusic changeMusic) {
    this.eventId = eventId;
    this.changeMusic = changeMusic;
    this.playingMusic = null;
    this.toPause = false;
    this.repeatMode = PlayingInfoManager.RepeatMode.SINGLE_CYCLE;
  }

  public PlayerEvent(int eventId, PlayingMusic playingMusic) {
    this.eventId = eventId;
    this.changeMusic = null;
    this.playingMusic = playingMusic;
    this.toPause = false;
    this.repeatMode = PlayingInfoManager.RepeatMode.SINGLE_CYCLE;
  }

  public PlayerEvent(int eventId, boolean toPause) {
    this.eventId = eventId;
    this.changeMusic = null;
    this.playingMusic = null;
    this.toPause = toPause;
    this.repeatMode = PlayingInfoManager.RepeatMode.SINGLE_CYCLE;
  }

  public PlayerEvent(int eventId, @PlayingInfoManager.RepeatMode int repeatMode) {
    this.eventId = eventId;
    this.changeMusic = null;
    this.playingMusic = null;
    this.toPause = false;
    this.repeatMode = repeatMode;
  }
}
