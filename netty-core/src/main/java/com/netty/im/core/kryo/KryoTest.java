package com.netty.im.core.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.netty.im.core.message.Message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by yr on 2018/8/18.
 */

public class KryoTest {
    public static void main(String[] args) throws FileNotFoundException{
        Kryo kryo=new Kryo();
        Output output=new Output(new FileOutputStream("file.bin"));
        Message message=new Message();
        message.setContent("gmg");
        kryo.writeObject(output,message);
        output.close();

        Input input=new Input(new FileInputStream("file.bin"));
        Message message1=kryo.readObject(input,Message.class);
        System.out.println(message1.getContent());
        input.close();

    }
}
