String fozu =   
                "                   _ooOoo_"+"\n"+  
                "                  o8888888o"+"\n"+  
                "                  88\" . \"88"+"\n"+  
                "                  (| -_- |)"+"\n"+  
                "                  O\\  =  /O"+"\n"+  
                "               ____/`---'\\____"+"\n"+  
                "             .'  \\\\|     |//  `."+"\n"+  
                "            /  \\\\|||  :  |||//  \\"+"\n"+  
                "           /  _||||| -卍- |||||-  \\"+"\n"+  
                "           |   | \\\\\\  -  /// |   |"+"\n"+  
                "           | \\_|  ''\\---/''  |   |"+"\n"+  
                "           \\  .-\\__  `-`  ___/-. /"+"\n"+  
                "         ___`. .'  /--.--\\  `. . __"+"\n"+  
                "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\"."+"\n"+  
                "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |"+"\n"+  
                "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /"+"\n"+  
                "======`-.____`-.___\\_____/___.-`____.-'======"+"\n"+  
                "                   `=---='"+"\n"+  
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+"\n"+  
                "                 佛祖保佑       永无BUG";  


logger.info("\n"+fozu);

//BASE64

String fozuStr = "ICAgICAgICAgICAgICAgICAgIF9vb09vb18KICAgICAgICAgICAgICAgICAgbzg4ODg4ODhvCiAgICAgICAgICAgICAgICAgIDg4IiAuICI4OAogICAgICAgICAgICAgICAgICAofCAtXy0gfCkKICAgICAgICAgICAgICAgICAgT1wgID0gIC9PCiAgICAgICAgICAgICAgIF9fX18vYC0tLSdcX19fXwogICAgICAgICAgICAgLicgIFxcfCAgICAgfC8vICBgLgogICAgICAgICAgICAvICBcXHx8fCAgOiAgfHx8Ly8gIFwKICAgICAgICAgICAvICBffHx8fHwgLTotIHx8fHx8LSAgXAogICAgICAgICAgIHwgICB8IFxcXCAgLSAgLy8vIHwgICB8CiAgICAgICAgICAgfCBcX3wgICcnXC0tLS8nJyAgfCAgIHwKICAgICAgICAgICBcICAuLVxfXyAgYC1gICBfX18vLS4gLwogICAgICAgICBfX19gLiAuJyAgLy0tLi0tXCAgYC4gLiBfXwogICAgICAuIiIgJzwgIGAuX19fXF88fD5fL19fXy4nICA+JyIiLgogICAgIHwgfCA6ICBgLSBcYC47YFwgXyAvYDsuYC8gLSBgIDogfCB8CiAgICAgXCAgXCBgLS4gICBcXyBfX1wgL19fIF8vICAgLi1gIC8gIC8KPT09PT09YC0uX19fX2AtLl9fX1xfX19fXy9fX18uLWBfX19fLi0nPT09PT09CiAgICAgICAgICAgICAgICAgICBgPS0tLT0nCl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXgogICAgICAgICAgICAgICAgIOS9m+elluS/neS9kSAgICAgICDmsLjml6BCVUc=";  
byte[] decode = Base64.decode(fozuStr.toCharArray());  
logger.info("\n"+new String(decode));  