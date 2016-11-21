package com.ccj.permission;

/**
 * Created by ccj on 2016/11/21.
 * 修改文字信息的接口
 */

public interface CallPermissionMsg {

    /**
     * 可以在activity中设置setResetPermissionMsg(true),
     * 然后实现接口{@link CallPermissionMsg },达到想要的文字样式
     * 可参考 getDefaultMsg 方法,进行实现
     * @param permission
     * @return
     */
     String callPermissionMsg(String permission);

}
