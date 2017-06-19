# exam
蓝鸥IT教育课程考评系统

下载源代码，eclipse导入该项目。


# api

## 用户模块api

### 用户注册
* url : /exam/api/user/
* method : POST
* param :  
{  
  "userName" : "name",  
  "userMail" : "xxxx@xx.com",  
  "userPosition" : 2,  
  "userPassword" : "sbsad2131",  
}  
* 返回信息  
{  
 "statusCode" : "xx",  
 "msg" : "xx",  
 "data" :  
 {  
  "id" : 7,  
  "userCreatetime" : 1497860131775,  
  "userMail" : "18826107119@163.com",  
  "userName" : "kenken",  
  "userPassword" : "5f4dcc3b5aa765d61d8327deb882cf99",  
  "userPosition" : 3,  
  "userStatus" : 0,  
  "userToken" : "3ea2cade73749ee9f0f6855d61545851",  
 }  
}   
statusCode
   
 
