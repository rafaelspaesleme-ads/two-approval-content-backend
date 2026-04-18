package br.com.twoapprovalcontentbackend.infraestructure.interfaces.consumers;

public interface HexaConsumer<A, B, C, D, E, F> {
    void accept(A a, B b, C c, D d, E e, F f);
}
