package com.yupi.xuojcodesandbox.security;

import java.security.Permission;

/**
 * 禁用所有权限安全管理器
 */
public class DenySecurityManager extends SecurityManager{

    // 检查所有的权限
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何限制");
        throw new SecurityException("权限异常" + perm.toString());
//        super.checkPermission(perm);
    }
}
