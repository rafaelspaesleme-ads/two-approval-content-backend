package br.com.twoapprovalcontentbackend.infraestructure.interfaces;

public interface HeptaConsumerFunction<A, B, C, D, E, F, G, R> {
    R apply(A a, B b, C c, D d, E e, F f, G g);
    void accept(A a, B b, C c, D d, E e, F f, G g);
}
