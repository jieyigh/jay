package me.biezhi.weixin;

import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.UIManager;




import blade.kit.DateKit;
import blade.kit.StringKit;
import blade.kit.http.HttpRequest;
import blade.kit.json.JSON;
import blade.kit.json.JSONArray;
import blade.kit.json.JSONObject;
import blade.kit.logging.Logger;
import blade.kit.logging.LoggerFactory;
import me.biezhi.weixin.util.CookieUtil;
import me.biezhi.weixin.util.JSUtil;
import me.biezhi.weixin.util.Matchers;
import me.biezhi.weixin.vo.BatchGetContactResponse;
import me.biezhi.weixin.vo.BatchUser;
import me.biezhi.weixin.vo.InitResponse;

/**
 * Hello world!
 *
 */
public class App {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	private String uuid;
	private int tip = 0;
	private String base_uri, redirect_uri, webpush_url = "https://webpush2.weixin.qq.com/cgi-bin/mmwebwx-bin";
	
	private String skey, synckey, wxsid, wxuin, pass_ticket, deviceId = "e" + DateKit.getCurrentUnixTime();
	
	private String cookie;
	private QRCodeFrame qrCodeFrame;
	
	private JSONObject SyncKey, User, BaseRequest;
	
	// 微信联系人列表，可聊天的联系人列表
	private JSONArray MemberList, ContactList;
	
	// 微信特殊账号
	private List<String> SpecialUsers = Arrays.asList("newsapp", "fmessage", "filehelper", "weibo", "qqmail", "fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote", "qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip", "blogappweixin", "weixin", "brandsessionholder", "weixinreminder", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "officialaccounts", "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "wxitil", "userexperience_alarm", "notification_messages");
	
	// 最近联系人 by guangguang
    List<String> mRecentContacts = new ArrayList<String>();
    InitResponse initResult;
//    GetContactResponse getContactResult;
    Set<String> groupUsernameSet = new HashSet<String>();//用于记录已经获取或群的信息
    //=======================
    
    
	public App() {
		System.setProperty("jsse.enableSNIExtension", "false");
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public String getUUID() {
		String url = "https://login.weixin.qq.com/jslogin";
		HttpRequest request = HttpRequest.get(url, true, 
				"appid", "wx782c26e4c19acffb", 
				"fun", "new",
				"lang", "zh_CN",
				"_" , DateKit.getCurrentUnixTime());
		
		LOGGER.info("[*] " + request);
		
		String res = request.body();
		request.disconnect();

		if(StringKit.isNotBlank(res)){
			String code = Matchers.match("window.QRLogin.code = (\\d+);", res);
			if(null != code){
				if(code.equals("200")){
					this.uuid = Matchers.match("window.QRLogin.uuid = \"(.*)\";", res);
					return this.uuid;
				} else {
					LOGGER.info("[*] 错误的状态码: %s", code);
				}
			}
		}
		return null;
	}
	
	/**
	 * 显示二维码
	 * @return
	 */
	public void showQrCode() {
		
		String url = "https://login.weixin.qq.com/qrcode/" + this.uuid;
		
		final File output = new File("temp.jpg");
		
		HttpRequest.post(url, true, 
				"t", "webwx", 
				"_" , DateKit.getCurrentUnixTime())
				.receive(output);

		if(null != output && output.exists() && output.isFile()){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
						qrCodeFrame = new QRCodeFrame(output.getPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	/**
	 * 等待登录
	 */
	public String waitForLogin(){
		this.tip = 1;
		String url = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login";
		HttpRequest request = HttpRequest.get(url, true, 
				"tip", this.tip, 
				"uuid", this.uuid,
				"_" , DateKit.getCurrentUnixTime());
		
		LOGGER.info("[*] " + request.toString());
		
		String res = request.body();
		request.disconnect();

		if(null == res){
			LOGGER.info("[*] 扫描二维码验证失败");
			return "";
		}
		
		String code = Matchers.match("window.code=(\\d+);", res);
		if(null == code){
			LOGGER.info("[*] 扫描二维码验证失败");
			return "";
		} else {
			if(code.equals("201")){
				LOGGER.info("[*] 成功扫描,请在手机上点击确认以登录");
				tip = 0;
			} else if(code.equals("200")){
				LOGGER.info("[*] 正在登录...");
				String pm = Matchers.match("window.redirect_uri=\"(\\S+?)\";", res);

				String redirectHost = "wx.qq.com";
				try {
					URL pmURL = new URL(pm);
					redirectHost = pmURL.getHost();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				String pushServer = JSUtil.getPushServer(redirectHost);
				webpush_url = "https://" + pushServer + "/cgi-bin/mmwebwx-bin";

				this.redirect_uri = pm + "&fun=new";
				LOGGER.info("[*] redirect_uri=%s", this.redirect_uri);
				this.base_uri = this.redirect_uri.substring(0, this.redirect_uri.lastIndexOf("/"));
				LOGGER.info("[*] base_uri=%s", this.base_uri);
			} else if(code.equals("408")){
				LOGGER.info("[*] 登录超时");
			} else {
				LOGGER.info("[*] 扫描code=%s", code);
			}
		}
		return code;
	}
	
	private void closeQrWindow() {
		qrCodeFrame.dispose();
	}
	
	/**
	 * 登录
	 */
	public boolean login(){
		
		HttpRequest request = HttpRequest.get(this.redirect_uri);
		
		LOGGER.info("[*] " + request);
		
		String res = request.body();
		this.cookie = CookieUtil.getCookie(request);

		request.disconnect();
		
		if(StringKit.isBlank(res)){
			return false;
		}
		
		this.skey = Matchers.match("<skey>(\\S+)</skey>", res);
		this.wxsid = Matchers.match("<wxsid>(\\S+)</wxsid>", res);
		this.wxuin = Matchers.match("<wxuin>(\\S+)</wxuin>", res);
		this.pass_ticket = Matchers.match("<pass_ticket>(\\S+)</pass_ticket>", res);
		
		LOGGER.info("[*] skey[%s]", this.skey);
		LOGGER.info("[*] wxsid[%s]", this.wxsid);
		LOGGER.info("[*] wxuin[%s]", this.wxuin);
		LOGGER.info("[*] pass_ticket[%s]", this.pass_ticket);
		
		this.BaseRequest = new JSONObject();
		BaseRequest.put("Uin", this.wxuin);
		BaseRequest.put("Sid", this.wxsid);
		BaseRequest.put("Skey", this.skey);
		BaseRequest.put("DeviceID", this.deviceId);
		
		return true;
	}
	
	/**
	 * 微信初始化
	 */
	public boolean wxInit(){
		
		String url = this.base_uri + "/webwxinit?r=" + DateKit.getCurrentUnixTime() + "&pass_ticket=" + this.pass_ticket +
				"&skey=" + this.skey;
		
		JSONObject body = new JSONObject();
		body.put("BaseRequest", this.BaseRequest);
		
		HttpRequest request = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", this.cookie)
				.send(body.toString());
		
		LOGGER.info("[*] " + request);
		String res = request.body();
		request.disconnect();
	//	LOGGER.info("[*] >>>>>>>>>>>init>>" + res);
		
		if(StringKit.isBlank(res)){
			return false;
		}
		// by guangguang
		initResult = com.alibaba.fastjson.JSONObject.parseObject(res, InitResponse.class);
		
		try {
			
			
			
			JSONObject jsonObject = JSON.parse(res).asObject();
			if(null != jsonObject){
				JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
				if(null != BaseResponse){
					int ret = BaseResponse.getInt("Ret", -1);
					if(ret == 0){
						this.SyncKey = jsonObject.getJSONObject("SyncKey");
						this.User = jsonObject.getJSONObject("User");
						
						StringBuffer synckey = new StringBuffer();
						
						JSONArray list = SyncKey.getJSONArray("List");
						for(int i=0, len=list.size(); i<len; i++){
							JSONObject item = list.getJSONObject(i);
							synckey.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
						}
						
						this.synckey = synckey.substring(1);
						
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 微信状态通知
	 */
	public boolean wxStatusNotify (){
		
		String url = this.base_uri + "/webwxstatusnotify?lang=zh_CN&pass_ticket=" + this.pass_ticket;
		
		JSONObject body = new JSONObject();
		body.put("BaseRequest", BaseRequest);
		body.put("Code", 3);
		body.put("FromUserName", this.User.getString("UserName"));
		body.put("ToUserName", this.User.getString("UserName"));
		body.put("ClientMsgId", DateKit.getCurrentUnixTime());
		
		HttpRequest request = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", this.cookie)
				.send(body.toString());
		
		LOGGER.info("[*] " + request);
		String res = request.body();
		request.disconnect();

		if(StringKit.isBlank(res)){
			return false;
		}
		
		try {
			JSONObject jsonObject = JSON.parse(res).asObject();
			JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
			if(null != BaseResponse){
				int ret = BaseResponse.getInt("Ret", -1);
				return ret == 0;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 获取联系人
	 */
	public boolean getContact(){
		
		String url = this.base_uri + "/webwxgetcontact?pass_ticket=" + this.pass_ticket + "&skey=" + this.skey + "&r=" + DateKit.getCurrentUnixTime();
		
		JSONObject body = new JSONObject();
		body.put("BaseRequest", BaseRequest);
		
		HttpRequest request = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", this.cookie)
				.send(body.toString());
		
		LOGGER.info("[*] " + request);
		String res = request.body();
		request.disconnect();
	
		if(StringKit.isBlank(res)){
			return false;
		}
		
		try {
			JSONObject jsonObject = JSON.parse(res).asObject();
			JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
			if(null != BaseResponse){
				int ret = BaseResponse.getInt("Ret", -1);
				if(ret == 0){
					this.MemberList = jsonObject.getJSONArray("MemberList");
					this.ContactList = new JSONArray();
					if(null != MemberList){
						for(int i=0, len=MemberList.size(); i<len; i++){
							JSONObject contact = this.MemberList.getJSONObject(i);
							//公众号/服务号
							if(contact.getInt("VerifyFlag", 0) == 8){
								continue;
							}
							//特殊联系人
							if(SpecialUsers.contains(contact.getString("UserName"))){
								continue;
							}
							//群聊
							if(contact.getString("UserName").indexOf("@@") != -1){
								continue;
							}
							//自己
							if(contact.getString("UserName").equals(this.User.getString("UserName"))){
								continue;
							}
							ContactList.add(contact);
						}
						return true;
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 消息检查
	 */
	public int[] syncCheck(){
		
		int[] arr = new int[2];
		
		String url = this.webpush_url + "/synccheck";
		
		JSONObject body = new JSONObject();
		body.put("BaseRequest", BaseRequest);
		
		HttpRequest request = HttpRequest.get(url, true,
				"r", DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5),
				"skey", this.skey,
				"uin", this.wxuin,
				"sid", this.wxsid,
				"deviceid", this.deviceId,
				"synckey", this.synckey,
				"_", System.currentTimeMillis())
				.header("Cookie", this.cookie);
		
		LOGGER.info("[*] " + request);
		String res = request.body();
		request.disconnect();

		if(StringKit.isBlank(res)){
			return arr;
		}
		
		String retcode = Matchers.match("retcode:\"(\\d+)\",", res);
		String selector = Matchers.match("selector:\"(\\d+)\"}", res);
		if(null != retcode && null != selector){
			arr[0] = Integer.parseInt(retcode);
			arr[1] = Integer.parseInt(selector);
			return arr;
		}
		return arr;
	}
	
	private void webwxsendmsg(String content, String to) {
		
		String url = this.base_uri + "/webwxsendmsg?lang=zh_CN&pass_ticket=" + this.pass_ticket;
		
		JSONObject body = new JSONObject();
		
		String clientMsgId = DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5);
		JSONObject Msg = new JSONObject();
		Msg.put("Type", 1);
		Msg.put("Content", content);
		Msg.put("FromUserName", User.getString("UserName"));
		Msg.put("ToUserName", to);
		Msg.put("LocalID", clientMsgId);
		Msg.put("ClientMsgId", clientMsgId);
		
		body.put("BaseRequest", this.BaseRequest);
		body.put("Msg", Msg);
		
		HttpRequest request = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", this.cookie)
				.send(body.toString());
		
		LOGGER.info("[*] " + request);
		request.body();
		request.disconnect();
	}
	
	/**
	 * 获取最新消息
	 */
	public JSONObject webwxsync(){
		
		String url = this.base_uri + "/webwxsync?lang=zh_CN&pass_ticket=" + this.pass_ticket
				 + "&skey=" + this.skey + "&sid=" + this.wxsid + "&r=" + DateKit.getCurrentUnixTime();
		
		JSONObject body = new JSONObject();
		body.put("BaseRequest", BaseRequest);
		body.put("SyncKey", this.SyncKey);
		body.put("rr", DateKit.getCurrentUnixTime());
		
		HttpRequest request = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", this.cookie)
				.send(body.toString());
		
		LOGGER.info("[*] " + request);
		String res = request.body();
		request.disconnect();
		
		if(StringKit.isBlank(res)){
			return null;
		}
		
		JSONObject jsonObject = JSON.parse(res).asObject();
		JSONObject BaseResponse = jsonObject.getJSONObject("BaseResponse");
		if(null != BaseResponse){
			int ret = BaseResponse.getInt("Ret", -1);
			if(ret == 0){
				this.SyncKey = jsonObject.getJSONObject("SyncKey");
				
				StringBuffer synckey = new StringBuffer();
				JSONArray list = SyncKey.getJSONArray("List");
				for(int i=0, len=list.size(); i<len; i++){
					JSONObject item = list.getJSONObject(i);
					synckey.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
				}
				this.synckey = synckey.substring(1);
				//by guangguang
				batchGetContactBywebwxsync(jsonObject);
			}
		}
		return jsonObject;
	}

	
	/**
	 * 获取最新消息
	 */
	public void handleMsg(JSONObject data){
		if(null == data){
			return;
		}
		
		JSONArray AddMsgList = data.getJSONArray("AddMsgList");
		
		for(int i=0,len=AddMsgList.size(); i<len; i++){
			LOGGER.info("[*] 你有新的消息，请注意查收");
			JSONObject msg = AddMsgList.getJSONObject(i);
			int msgType = msg.getInt("MsgType", 0);
			String name = getUserRemarkName(msg.getString("FromUserName"));
			String content = msg.getString("Content");
			
			if(msgType == 51){
				LOGGER.info("[*] 成功截获微信初始化消息");
			} else if(msgType == 1){
				if(SpecialUsers.contains(msg.getString("ToUserName"))){
					continue;
				} else if(msg.getString("FromUserName").equals(User.getString("UserName"))){
					continue;
				} else if (msg.getString("ToUserName").indexOf("@@") != -1) {
					String[] peopleContent = content.split(":<br/>");
					LOGGER.info("|" + name + "| " + peopleContent[0] + ":\n" + peopleContent[1].replace("<br/>", "\n"));
				} else {
					LOGGER.info(name + ": " + content);
				
					
			//		String ans = xiaodoubi(content);
					
					String ans = "你说啥";
					
					webwxsendmsg(ans, msg.getString("FromUserName"));
					LOGGER.info("自动回复 " + ans);
				}
			} else if(msgType == 3){
				webwxsendmsg("二蛋还不支持图片呢", msg.getString("FromUserName"));
			} else if(msgType == 34){
				webwxsendmsg("二蛋还不支持语音呢", msg.getString("FromUserName"));
			} else if(msgType == 42){
				LOGGER.info(name + " 给你发送了一张名片:");
				LOGGER.info("=========================");
			}
		}
	}
	private JSONArray getList(List<BatchUser> requestContacts){
		JSONArray arr = new JSONArray();
		for (BatchUser user : requestContacts) {
			JSONObject obj = new JSONObject();
			obj.put("UserName", user.UserName);
			if(user.ChatRoomId == null){
				obj.put("EncryChatRoomId", "");
			}else{
				obj.put("ChatRoomId", "");
			}
			arr.add(obj);
		}
		return arr;
	}
	public BatchGetContactResponse batchGetContact(List<BatchUser> requestContacts){

		String url = this.base_uri + "/webwxbatchgetcontact?pass_ticket=" + this.pass_ticket + "&type=ex&lang=zh_CN&r=" + DateKit.getCurrentUnixTime();
	//	String url = this.base_uri + "/webwxbatchgetcontact?type=ex&r=" + DateKit.getCurrentUnixTime();
		JSONObject body = new JSONObject();
		body.put("BaseRequest", BaseRequest);
		body.put("Count", requestContacts.size());
		
		body.put("List", getList(requestContacts));
		
		LOGGER.info("[*] body:" + body.toString());
		HttpRequest request = HttpRequest.post(url)
				.header("Content-Type", "application/json;charset=utf-8")
				.header("Cookie", this.cookie)
				.send(body.toString());
		
		LOGGER.info("[*] " + request);
		String res = request.body();
		request.disconnect();
		System.out.println("batchGetContact request body:"+body.toString());
		
		System.out.println("batchGetContact:"+res);
		//如果成功，则添加到set(这里先假设都成功)
		if(res != null && !res.equals("")){
			for (BatchUser user : requestContacts) {
				groupUsernameSet.add(user.UserName);
			}
		}
		
	    return null;
	}
	/**
	 * 获取群成员信息 
	 * by 光光
	 */
	public void getGroupMember(){
		
		
        String[] chatsets = initResult.ChatSet.split(",");//"ChatSet": "filehelper,weixin,@@ee2cbd27d952a7cf3fb8a9abf39840b012a70a07840b09004f03816163644285,",
        
        List<BatchUser> firstBatUsers = new ArrayList<BatchUser>();
        for (String username : chatsets) {
        	if (!username.startsWith("@")) continue;
        	BatchUser user = new BatchUser();
        	user.UserName = username;
        	user.ChatRoomId = "";
        	firstBatUsers.add(user);
		}
        this.batchGetContact(firstBatUsers);
		
	}
	/**
	 * by guangguang
	 * @param jsonObject
	 */
	private void batchGetContactBywebwxsync(JSONObject jsonObject){
		//================by guangguang 批量获取不活跃群联系人
		int msgCount = jsonObject.getInt("AddMsgCount", 0);
		if(msgCount >0){
			JSONArray jsonArr = jsonObject.getJSONArray("AddMsgList");
			try {
				JSONObject obj = jsonArr.get(0).asObject();
				
				String fromUsername = obj.getString("FromUserName");
				String toUserName = obj.getString("ToUserName");
				if(fromUsername == null || toUserName == null ||
						!fromUsername.equals(toUserName)){
					return;
				}
				
				
				String StatusNotifyUserName = obj.getString("StatusNotifyUserName");
				
				if(StatusNotifyUserName != null && !StatusNotifyUserName.equals("")){
					List<BatchUser> secondtBatUsers = new ArrayList<BatchUser>();
					String[] users = StatusNotifyUserName.split(",");
					for (String username : users) {
			        	if (!username.startsWith("@") || groupUsernameSet.contains(username)) continue;
			        	BatchUser user = new BatchUser();
			        	user.UserName = username;
			        	user.EncryChatRoomId = "";
			        	secondtBatUsers.add(user);
					}
					System.out.println("获取不活跃群成员....");
					this.batchGetContact(secondtBatUsers);
					System.out.println("获取不活跃群成员成功....");
				}
				
			} catch (Exception e) {
				System.out.println("batchGetContactBywebwxsync解析错误了。。。。");
			}
		}
	}
	
	
	
	
	
	
	private String getUserRemarkName(String id) {
		String name = "这个人物名字未知";
		for(int i=0, len=MemberList.size(); i<len; i++){
			JSONObject member = this.MemberList.getJSONObject(i);
			if(member.getString("UserName").equals(id)){
				if(StringKit.isNotBlank(member.getString("RemarkName"))){
					name = member.getString("RemarkName");
				} else {
					name = member.getString("NickName");
				}
				return name;
			}
		}
		return name;
	}
	
	public void listenMsgMode(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("[*] 进入消息监听模式 ...");
				int playWeChat = 0;
				while(true){
					long start = System.currentTimeMillis();
					LOGGER.info("syncCheck start.....");
					int[] arr = syncCheck();
					long end = System.currentTimeMillis();
					LOGGER.info("syncCheck end.....time:"+(end -start));
					LOGGER.info("[*] retcode=%s,selector=%s", arr[0], arr[1]);
					
					if(arr[0] == 1100){
//						LOGGER.info("[*] 你在手机上登出了微信，债见");
//						break;
						arr = syncCheck();
					}
					
					if(arr[0] == 0){
						if(arr[1] == 2){
							JSONObject data = webwxsync();
							handleMsg(data);
						} else if(arr[1] == 6){
							JSONObject data = webwxsync();
							handleMsg(data);
						} else if(arr[1] == 7){
							playWeChat += 1;
							LOGGER.info("[*] 你在手机上玩微信被我发现了 %d 次", playWeChat);
							webwxsync();
						} else if(arr[1] == 3){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} else if(arr[1] == 0){
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					} else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "listenMsgMode").start();
	}
	
	public static void main(String[] args) throws InterruptedException {

		System.out.println(JSUtil.getPushServer("wx.qq.com"));

		App app = new App();
		String uuid = app.getUUID();
		if(null == uuid){
			LOGGER.info("[*] uuid获取失败");
		} else {
			LOGGER.info("[*] 获取到uuid为 [%s]", app.uuid);
			app.showQrCode();
			while(!app.waitForLogin().equals("200")){
				Thread.sleep(2000);
			}
			app.closeQrWindow();
			
			if(!app.login()){
				LOGGER.info("微信登录失败");
				return;
			}
			
			LOGGER.info("[*] 微信登录成功");
			
			if(!app.wxInit()){
				LOGGER.info("[*] 微信初始化失败");
				return;
			}
			
			LOGGER.info("[*] 微信初始化成功");
			
			if(!app.wxStatusNotify()){
				LOGGER.info("[*] 开启状态通知失败");
				return;
			}
			
			LOGGER.info("[*] 开启状态通知成功");
			
			if(!app.getContact()){
				LOGGER.info("[*] 获取联系人失败");
				return;
			}
			
			LOGGER.info("[*] 获取联系人成功");
			LOGGER.info("[*] 共有 %d 位联系人", app.ContactList.size());
			
			app.getGroupMember();
			
			
			// 监听消息
			app.listenMsgMode();
			
			//mvn exec:java -Dexec.mainClass="me.biezhi.weixin.App"
		}
	}
	
}