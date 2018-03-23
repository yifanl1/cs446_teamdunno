package com.example.cosine.myapplication;

import android.support.v4.app.ListFragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class SongsModel extends ListFragment {
    private List<Song> songs;

    public SongsModel() {
        songs = new ArrayList<>();
    }

    // code modified from https://stackoverflow.com/a/21333187
    public void loadSongs() {
        ContentResolver cr = getActivity().getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count;

        if(cur != null) {
            count = cur.getCount();

            if(count > 0) {
                while(cur.moveToNext()) {
                    String filePath = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    songs.add(new Song(filePath, title));
                    System.out.println(filePath);
                }
            }

            cur.close();
        }
    }

    public List<Song> getSongs() {
        return songs;
    }
}