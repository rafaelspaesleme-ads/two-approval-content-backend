package br.com.twoapprovalcontentbackend.infraestructure.interfaces;

public interface PentaConsumerFunction<A, B, C, D, E, R> {
    R apply(A a, B b, C c, D d, E e);
    void accept(A a, B b, C c, D d, E e);
}
