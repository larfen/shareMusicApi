# 网易云爬虫服务

# 根据关键词搜索歌曲
import asyncio
import functools
from data.song import Song

url = 'http://localhost:3000'
session = None


# 搜索接口 返回Song实体集合
async def search(key):
    async with session.get(url + '/search/', params={'keywords': key, 'limit': 10}) as resp:
        search_res = await resp.json()

        songs = search_res['result']['songs']
        ids = ''
        album_ids = []
        # 提取出歌曲id和专辑id
        for item in songs:
            ids += str(item['id']) + ','
            album_ids.append(item['album']['id'])

        # 根据歌曲id获取歌曲链接 保存到map中
        id_url_map = {}
        song_url_task = asyncio.create_task(find(ids[:-1]))

        def when_song_url_finish(task):
            songs_url_res = task.result()
            for item in songs_url_res['data']:
                id_url_map[item['id']] = item['url']

        song_url_task.add_done_callback(when_song_url_finish)

        # 根据专辑id获取专辑图片链接  保存到map中
        album_tasks = []
        album_url_map = {}

        def when_album_done(id, task):
            res = task.result()
            if len(res['songs']) > 0:
                album_url_map[id] = res['songs'][0]['al']['picUrl']

        for id in album_ids:
            task = asyncio.create_task(album(id))
            task.add_done_callback(functools.partial(when_album_done, id))
            album_tasks.append(task)

        await asyncio.gather(song_url_task, asyncio.gather(*album_tasks))

        # 获取完毕 解析实体
        result = []
        for item in songs:
            if id_url_map[item['id']] is not None:
                result.append(
                    Song(item['name'], item['artists'][0]['name'], item['album']['name'], id_url_map[item['id']],
                         album_url_map[item['album']['id']],
                         'neteaseMusic'))
        return result


# 根据歌曲id获取歌曲链接 可由,做分割 1,2,3
async def find(id):
    async with session.get(url + '/song/url', params={'id': str(id)}) as resp:
        return await resp.json()


# 根据专辑id获取专辑详情 包含图片链接
async def album(id):
    async with session.get(url + '/album', params={'id': str(id)}) as resp:
        return await resp.json()
