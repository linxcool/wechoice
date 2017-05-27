package com.linxcool.wechoice.util;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by huchanghai on 2017/5/27.
 */
public class WeChatTool {

    private static IWXAPI api;

    public static void init(Context context) {
        api = WXAPIFactory.createWXAPI(context, "wxc2035b5570fc07e8", true);
        api.registerApp("wxc2035b5570fc07e8");
    }

    public static void shareWeb(String url) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "aaaa";
        msg.description = "bbbb";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "wxtran" + System.currentTimeMillis();
        req.message = msg;
        api.sendReq(req);
    }

}
