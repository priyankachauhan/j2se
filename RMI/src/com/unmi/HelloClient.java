package com.unmi;   
  
import java.rmi.Naming;   

import com.pugwoo.remote.HelloInterface;
import com.pugwoo.remote.ResultInterface;
  
public class HelloClient   
{   
   /**  
    * 查找远程对象并调用远程方法  
    */  
   public static void main(String[] argv)   
   {   
      try  
      {   
         HelloInterface hello = (HelloInterface) Naming.lookup("Hello");   
            
         //如果要从另一台启动了RMI注册服务的机器上查找hello实例   
         //HelloInterface hello = (HelloInterface)Naming.lookup("//192.168.1.105:1099/Hello");   
            
         //调用远程方法   
         System.out.println(hello.say("hello world"));
         
         // 调用远程方法获得remote对象
         ResultInterface resultInterface = hello.remoteSay("hello remote");
         
         System.out.println(resultInterface.getMsg());
         // 设置远程remote对象
         resultInterface.setMsg("new msg");
         System.out.println(resultInterface.getMsg());
         
      }   
      catch (Exception e)   
      {   
         System.out.println("HelloClient exception: " + e);   
      }   
   }   
}   