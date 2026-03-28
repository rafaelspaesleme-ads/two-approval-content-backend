package br.com.twoapprovalcontentbackend.infraestructure.utils;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.lang.Contract;

import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableUtils {

    @Contract("null -> true")
    public static boolean isEmpty(@Nullable Page<?> page) {
        return page == null || page.isEmpty();
    }

    public static <T> Page<T> emptyPage() {
        return new PageImpl<>(Collections.emptyList());
    }

}
