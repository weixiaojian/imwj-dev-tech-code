package io.imwj.util;

import java.io.Serializable;
import java.sql.Driver;

/**
 * @author langao_q
 * @since 2021-03-17 10:56
 */
public class MAIN implements Serializable {


    public static void main(String[] args) throws Exception {
        Object obj = null;
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Object o = clazz.newInstance();
    }

}
