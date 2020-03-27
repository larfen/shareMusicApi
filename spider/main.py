import json

import aiohttp
from aiohttp import web
from service import neteaseMusicService

routes = web.RouteTableDef()


@routes.get('/search/{name}')
async def hello(request):
    timeout = aiohttp.ClientTimeout(total=10)
    async with aiohttp.ClientSession(timeout=timeout) as session:
        neteaseMusicService.session = session
        netease_result = await neteaseMusicService.search(request.match_info['name'])
        return web.json_response(json.dumps([ob.__dict__ for ob in netease_result]))


app = web.Application()
app.add_routes(routes)
web.run_app(app)
