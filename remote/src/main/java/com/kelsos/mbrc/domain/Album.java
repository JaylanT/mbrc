package com.kelsos.mbrc.domain;

public class Album {
  private long id;
  private String name;
  private String artist;
  private String cover;

  public Album(long id, String name, String artist, String cover) {
    this.id = id;
    this.name = name;
    this.artist = artist;
    this.cover = cover;
  }

  public String getCover() {
    return cover;
  }

  public String getArtist() {
    return artist;
  }

  public String getName() {
    return name;
  }

  public long getId() {
    return id;
  }
}