package org.hx.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author Upoint0002
 */
public class ProtoTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("哈哈哈")
                .setAge(20)
                .setAddress("武汉").build();
        byte[] bytes = student.toByteArray();
        DataInfo.Student obj = DataInfo.Student.parseFrom(bytes);
        System.out.println(obj.getName());
    }
}
