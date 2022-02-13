package com.tencent.wxcloudrun.common.bean;

/**
 * KeyValueBean
 * - K: key
 * - V: value
 *
 * @author ajia.zjj
 */
public class KeyValueBean<K, V> {
    private K key;
    private V value;

    public KeyValueBean(K k, V v) {
        this.key = k;
        this.value = v;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof KeyValueBean)) {
            return false;
        }

        KeyValueBean<?, ?> keyValueBean = (KeyValueBean<?, ?>) o;

        if (getKey() != null ? !getKey().equals(keyValueBean.getKey()) : keyValueBean.getKey() != null) {
            return false;
        }

        return getValue() != null ? getValue().equals(keyValueBean.getValue()) : keyValueBean.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
