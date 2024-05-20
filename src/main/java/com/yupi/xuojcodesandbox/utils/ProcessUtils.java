package com.yupi.xuojcodesandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.yupi.xuojcodesandbox.model.ExecuteMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程工具类
 */
public class ProcessUtils {

    /**
     * 执行进程并获取信息
     * @param runPrecess
     * @param psName
     * @return
     */
//    public static ExecuteMessage runProcessAndGetMessage(Process runPrecess, String psName) {
//        ExecuteMessage executeMessage = new ExecuteMessage();
//        try {
//            StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            // 等待程序执行，获取错误码
//            int exitValue = runPrecess.waitFor();
//            executeMessage.setExitValue(exitValue);
//            // 正常退出
//            if (exitValue == 0) {
//                System.out.println(psName + "成功");
//                // 分批获取进程的输出
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runPrecess.getInputStream()));
//                StringBuilder compileOutputStringBuilder = new StringBuilder();
//                // 逐行读取
//                String compileOutputLine ;
//                while ((compileOutputLine = bufferedReader.readLine()) != null) {
//                    compileOutputStringBuilder.append(compileOutputLine);
//                }
//                executeMessage.setMessage(compileOutputStringBuilder.toString());
//            } else {
//                // 异常退出
//                System.out.println(psName + "失败，错误码： " + exitValue);
//                // 分批获取进程的正常输出
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runPrecess.getInputStream()));
//                StringBuilder compileOutputStringBuilder = new StringBuilder();
//                // 逐行读取
//                String compileOutputLine ;
//                while ((compileOutputLine = bufferedReader.readLine()) != null) {
//                    compileOutputStringBuilder.append(compileOutputLine);
//                }
//                executeMessage.setMessage(compileOutputStringBuilder.toString());
//                // 分批获取进程的输出
//                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runPrecess.getErrorStream()));
//                StringBuilder errorcompileOutputStringBuilder = new StringBuilder();
//                // 逐行读取
//                String errorCompileOutputLine ;
//                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
//                    errorcompileOutputStringBuilder.append(errorCompileOutputLine);
//                }
//                executeMessage.setErrorMessage(errorcompileOutputStringBuilder.toString());
//            }
//            stopWatch.stop();
//            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return executeMessage;
//    }

    /**
     * 执行进程并获取信息
     *
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            // 等待程序执行，获取错误码
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            // 正常退出
            if (exitValue == 0) {
                System.out.println(opName + "成功");
                // 分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                // 逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));
            } else {
                // 异常退出
                System.out.println(opName + "失败，错误码： " + exitValue);
                // 分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                // 逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));

                // 分批获取进程的错误输出
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                // 逐行读取
                List<String> errorOutputStrList = new ArrayList<>();
                // 逐行读取
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorOutputStrList.add(errorCompileOutputLine);
                }
                executeMessage.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }


    /**
     * 执行交互式进程并获取信息
     * @param runPrecess
     * @param args
     * @return
     */
    public static ExecuteMessage runInterProcessAndGetMessage(Process runPrecess, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            OutputStream outputStream = runPrecess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] arguments = args.split(" ");
            String join = StrUtil.join("\n", arguments) + "\n";
            outputStreamWriter.write(join);
            // 回车返回参数
            outputStreamWriter.flush();

            InputStream inputStream = runPrecess.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runPrecess.getInputStream()));
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                // 逐行读取
                String compileOutputLine ;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
                // 释放资源
                outputStream.close();
                inputStream.close();
                outputStreamWriter.close();
                runPrecess.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }
}
