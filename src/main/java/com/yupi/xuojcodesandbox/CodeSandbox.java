package com.yupi.xuojcodesandbox;


import com.yupi.xuojcodesandbox.model.ExecuteCodeRequest;
import com.yupi.xuojcodesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
