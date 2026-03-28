package br.com.twoapprovalcontentbackend.application.mappers;

import br.com.twoapprovalcontentbackend.infraestructure.interfaces.HeptaConsumerFunction;
import br.com.twoapprovalcontentbackend.infraestructure.interfaces.HexaConsumerFunction;
import br.com.twoapprovalcontentbackend.infraestructure.interfaces.PentaConsumerFunction;
import br.com.twoapprovalcontentbackend.infraestructure.interfaces.TetraConsumerFunction;
import br.com.twoapprovalcontentbackend.infraestructure.utils.PageableUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
public abstract class AbstractFunctionsMapper<I, O, R> {

    @Getter
    @Setter
    protected I input;

    protected O output;

    @Getter
    @Setter
    protected R response;

    protected I[] multiInput;

    protected List<O> outputList;

    protected Page<O> outputPage;

    protected Set<O> outputSet;

    protected Class<R> destinationResponseClass;

    protected ObjectMapper mapper = new ObjectMapper();

    protected AbstractFunctionsMapper(Class<R> destinationResponseClass) {
        this.mapper.registerModule(new JavaTimeModule());
        this.destinationResponseClass = destinationResponseClass;
    }

    protected AbstractFunctionsMapper(O output, Class<R> destinationResponseClass) {
        this.mapper.registerModule(new JavaTimeModule());
        this.output = output;
        this.destinationResponseClass = destinationResponseClass;
    }

    protected <T> I mapRequest(T request, Class<I> destinationInputClass) {
        return this.mapper.convertValue(request, destinationInputClass);
    }

    protected R getBuild() {
        return this.mapper.convertValue(this.output, this.destinationResponseClass);
    }

    protected List<R> getBuildList() {
        if (CollectionUtils.isEmpty(this.outputList)) return Collections.emptyList();
        return this.outputList.stream().map(o -> this.mapper.convertValue(o, this.destinationResponseClass)).toList();
    }

    protected Page<R> getBuildPage() {
        if (PageableUtils.isEmpty(this.outputPage)) return PageableUtils.emptyPage();
        return this.outputPage.map(o -> this.mapper.convertValue(o, this.destinationResponseClass));
    }

    protected Set<R> getBuildSet() {
        if (CollectionUtils.isEmpty(this.outputSet)) return Collections.emptySet();
        return this.outputSet.stream().map(o -> this.mapper.convertValue(o, this.destinationResponseClass)).collect(Collectors.toSet());
    }

    public void setVoid(Consumer<I> result) {
        result.accept(this.input);
    }

    @SuppressWarnings("unchecked")
    public <A, B>  void setVoid(BiConsumer<A, B> result) {
        result.accept(
                (A) this.multiInput[0],
                (B) this.multiInput[1]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C>  void setVoid(TriConsumer<A, B, C> result) {
        result.accept(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D>  void setVoid(TetraConsumerFunction<A, B, C, D, Void> result) {
        result.accept(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E>  void setVoid(PentaConsumerFunction<A, B, C, D, E, Void> result) {
        result.accept(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F>  void setVoid(HexaConsumerFunction<A, B, C, D, E, F, Void> result) {
        result.accept(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F, G>  void setVoid(HeptaConsumerFunction<A, B, C, D, E, F, G, Void> result) {
        result.accept(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5],
                (G) this.multiInput[6]
        );
    }

    public void setOutput(Function<I, O> result) {
        this.output = result.apply(this.input);
    }

    public void setOutputList(Function<I, List<O>> result) {
        this.outputList = result.apply(this.input);
    }

    public void setOutputPage(Function<I, Page<O>> result) {
        this.outputPage = result.apply(this.input);
    }

    public void setOutputSet(Function<I, Set<O>> result) {
        this.outputSet = result.apply(this.input);
    }

    @SuppressWarnings("unchecked")
    public <A, B> void setOutput(BiFunction<A, B, O> result) {
        this.output = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B> void setOutputList(BiFunction<A, B, List<O>> result) {
        this.outputList = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B> void setOutputPage(BiFunction<A, B, Page<O>> result) {
        this.outputPage = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C> void setOutput(TriFunction<A, B, C, O> result) {
        this.output = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C> void setOutputList(TriFunction<A, B, C, List<O>> result) {
        this.outputList = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C> void setOutputPage(TriFunction<A, B, C, Page<O>> result) {
        this.outputPage = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D> void setOutput(TetraConsumerFunction<A, B, C, D, O> result) {
        this.output = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D> void setOutputList(TetraConsumerFunction<A, B, C, D, List<O>> result) {
        this.outputList = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D> void setOutputPage(TetraConsumerFunction<A, B, C, D, Page<O>> result) {
        this.outputPage = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E> void setOutput(PentaConsumerFunction<A, B, C, D, E, O> result) {
        this.output = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E> void setOutputList(PentaConsumerFunction<A, B, C, D, E, List<O>> result) {
        this.outputList = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E> void setOutputPage(PentaConsumerFunction<A, B, C, D, E, Page<O>> result) {
        this.outputPage = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F> void setOutput(HexaConsumerFunction<A, B, C, D, E, F, O> result) {
        this.output = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F> void setOutputList(HexaConsumerFunction<A, B, C, D, E, F, List<O>> result) {
        this.outputList = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F> void setOutputPage(HexaConsumerFunction<A, B, C, D, E, F, Page<O>> result) {
        this.outputPage = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F, G> void setOutput(HeptaConsumerFunction<A, B, C, D, E, F, G, O> result) {
        this.output = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5],
                (G) this.multiInput[6]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F, G> void setOutputList(HeptaConsumerFunction<A, B, C, D, E, F, G, List<O>> result) {
        this.outputList = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5],
                (G) this.multiInput[6]
        );
    }

    @SuppressWarnings("unchecked")
    public <A, B, C, D, E, F, G> void setOutputPage(HeptaConsumerFunction<A, B, C, D, E, F, G, Page<O>> result) {
        this.outputPage = result.apply(
                (A) this.multiInput[0],
                (B) this.multiInput[1],
                (C) this.multiInput[2],
                (D) this.multiInput[3],
                (E) this.multiInput[4],
                (F) this.multiInput[5],
                (G) this.multiInput[6]
        );
    }

    @SafeVarargs
    protected final I[] buildMultiParamsInput(I... params) {
        return params;
    }
}
