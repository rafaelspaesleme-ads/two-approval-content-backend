package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.filters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchersFilter {

    public static String[] getGets() {
        return new String[] {
                "/user/niches",
                "/user/niches/**",
        };
    }

    public static String[] getPosts() {
        return new String[] {
                "/user/register",
                "/user/register/**",
                "/user/login",
                "/user/login/**"
        };
    }

    public static String[] getPuts() {
        return new String[] {};
    }

    public static String[] getPatchs() {
        return new String[] {};
    }

    public static String[] getDeletes() {
        return new String[] {};
    }

    public static String[] getOptions() {
        return new String[] {
                "/**"
        };
    }

    public static String[] getDocs() {
        return new String[] {
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/license.html",
                "/docs.html"
        };
    }

}
