package com.libofan.concurrency.example.art;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrentHashMap<K, V> {

     static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }

         public final boolean equals(Object o) {
             Object k, v, u; Map.Entry<?,?> e;
             return ((o instanceof Map.Entry) &&
                     (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                     (v = e.getValue()) != null &&
                     (k == key || k.equals(key)) &&
                     (v == (u = val) || v.equals(u)));
         }

         /**
          * Virtualized support for map.get(); overridden in subclasses.
          */
         Node<K,V> find(int h, Object k) {
             Node<K,V> e = this;
             if (k != null) {
                 do {
                     K ek;
                     if (e.hash == h &&
                             ((ek = e.key) == k || (ek != null && k.equals(ek))))
                         return e;
                 } while ((e = e.next) != null);
             }
             return null;
         }
     }

    static class Segment<K, V> extends ReentrantLock {
        final float loadFactor;
        Segment(float lf) {
            loadFactor = lf;
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {

    }





    public static void main(String[] args) {

    }
}
