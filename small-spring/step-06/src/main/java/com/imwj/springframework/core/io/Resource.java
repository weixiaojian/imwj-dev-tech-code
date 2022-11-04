package com.imwj.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wj
 * @create 2022-11-01 17:27
 */
public interface  Resource {

    InputStream getInputStream() throws IOException;

}
