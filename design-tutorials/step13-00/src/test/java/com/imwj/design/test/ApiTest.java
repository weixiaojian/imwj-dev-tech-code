package com.imwj.design.test;

import com.imwj.design.Cmd;

/**
 * program arguments：-version
 * @author wj
 * @create 2023-06-08 17:14
 */
public class ApiTest {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            System.out.println("java version \"1.8.0\"");
        }
    }
}
