package br.com.twoapprovalcontentbackend.infraestructure.interfaces;

public interface HexaConsumerFunction<A, B, C, D, E, F, R> {
    R apply(A a, B b, C c, D d, E e, F f);
    void accept(A a, B b, C c, D d, E e, F f);
}
