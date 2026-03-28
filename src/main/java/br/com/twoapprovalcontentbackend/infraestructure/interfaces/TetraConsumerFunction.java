package br.com.twoapprovalcontentbackend.infraestructure.interfaces;

public interface TetraConsumerFunction<A, B, C, D, R> {
    R apply(A a, B b, C c, D d);
    void accept(A a, B b, C c, D d);
}
