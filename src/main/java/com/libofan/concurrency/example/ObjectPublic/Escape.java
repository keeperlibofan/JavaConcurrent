package com.libofan.concurrency.example.ObjectPublic;

public class Escape {

    private int thisBeEscape = 0;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass {
        public InnerClass() {
            System.out.printf("%d", Escape.this.thisBeEscape);  // this 代表这个实例化对象
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
