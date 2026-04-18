package br.com.twoapprovalcontentbackend.infraestructure.interfaces.functions;

public interface HeptaFunction<A, B, C, D, E, F, G, R> {
    R apply(A a, B b, C c, D d, E e, F f, G g);
}
