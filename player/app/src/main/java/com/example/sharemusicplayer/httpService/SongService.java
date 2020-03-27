package com.example.sharemusicplayer.httpService;
import com.example.sharemusicplayer.entity.Song;

/**
 * 歌曲服务
 */
public class SongService {

    public static SongService songService;

    public static SongService getInstance() {
        if (songService == null) {
            songService = new SongService();
        }
        return songService;
    }
    BaseHttpService httpService = new BaseHttpService();

    /**
     * 搜索歌曲 会调函数中传递歌曲列表
     * @param callBack
     */
    public void search(BaseHttpService.CallBack callBack, String name) {
        httpService.get("search/" + name, callBack, Song[].class);
    }
}
