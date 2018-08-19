package com.netty.im.core.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * Created by yr on 2018/8/18.
 */
public class ThreadLocalKryoFactory extends KryoFactory {
    private final ThreadLocal<Kryo> holder  = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return createKryo();
        }
    };

    public Kryo getKryo() {
        return holder.get();
    }
}
