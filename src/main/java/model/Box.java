package model;

import lombok.Data;

/**
 * @author lqq
 * @date 2020/3/29
 */

@Data
public class Box<T, E> {

    private T t;
    private E e;

    public Box(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public Class<?> getTClassType() {
        return t.getClass();
    }

    public Class<?> getEClassType() {
        return e.getClass();
    }
}
