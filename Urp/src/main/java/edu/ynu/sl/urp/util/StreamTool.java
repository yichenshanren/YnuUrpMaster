package edu.ynu.sl.urp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class StreamTool {
    /**
     * ���������л�ȡ����
     *
     * @param inputStream��������
     */
    public static byte[] readInputStream(InputStream inputStream) throws Exception {
//ʵ����һ�������?
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//һ��1024�ֽڵĻ����ֽ�����
        byte[] buffer = new byte[1024];
        int len = 0;
//�����Ļ���֪ʶ
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
//����Ҫ��
        inputStream.close();
        return outputStream.toByteArray();
    }
}
