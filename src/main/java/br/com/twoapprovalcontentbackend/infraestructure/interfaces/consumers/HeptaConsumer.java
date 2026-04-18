package br.com.twoapprovalcontentbackend.infraestructure.interfaces.consumers;

public interface HeptaConsumer<A, B, C, D, E, F, G> {
    void accept(A a, B b, C c, D d, E e, F f, G g);
}
