package com.heima.utils.thread;

import com.heima.model.user.pojos.ApUser;


public class ApThreadLocalUtils {
        // final: 引用的指向不能变，指向的实例可以变
        private final static ThreadLocal<ApUser> AP_USER_THREAD_LOCAL = new ThreadLocal<>();

        /**
         * 添加用户
         * @param apUser
         */
        public static void setUser(ApUser apUser){
            AP_USER_THREAD_LOCAL.set(apUser);
        }

        /**
         * 获取用户
         */
        public static ApUser getUser(){
            return AP_USER_THREAD_LOCAL.get();
        }

        /**
         * 清理用户
         */
        public static void clear(){
            AP_USER_THREAD_LOCAL.remove();
        }
}
