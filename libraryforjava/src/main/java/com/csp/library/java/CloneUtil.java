package com.csp.library.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 深度克隆工具
 * Created by csp on 2016/12/14.
 * Modified by csp on 2016/12/14.
 *
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class CloneUtil {

    /**
     * 使用 Serializable 深度克隆
     *
     * @param datum 对象
     * @param <T>   对象类型，需要继承 Serializable
     * @return 克隆结果
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T datum) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(datum);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Object result = ois.readObject();
        ois.close();

        return (T) result;
    }
}