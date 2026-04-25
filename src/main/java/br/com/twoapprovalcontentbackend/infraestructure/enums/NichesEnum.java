package br.com.twoapprovalcontentbackend.infraestructure.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NichesEnum {
    TECHNOLOGY("Technology", "Tecnologia e Software"),
    HEALTH_WELLNESS("Health & Wellness", "Saúde e Bem-estar"),
    FITNESS("Fitness & Exercise", "Fitness e Exercícios"),
    PERSONAL_FINANCE("Personal Finance", "Finanças Pessoais"),
    COOKING("Cooking & Recipes", "Culinária e Receitas"),
    TRAVEL("Travel & Tourism", "Viagens e Turismo"),
    FASHION("Fashion & Style", "Moda e Estilo"),
    BEAUTY("Beauty & Personal Care", "Beleza e Cuidados Pessoais"),
    EDUCATION("Education & Learning", "Educação e Aprendizado"),
    BUSINESS("Business & Entrepreneurship", "Negócios e Empreendedorismo"),
    GAMES("Games & E-sports", "Games e Esports"),
    MOVIES_SERIES("Movies & Series", "Cinema e Séries"),
    MUSIC("Music & Instruments", "Música e Instrumentos"),
    LITERATURE("Literature & Writing", "Literatura e Escrita"),
    ANIME_MANGA("Anime & Manga", "Animes e Mangás"),
    LIFESTYLE("Lifestyle", "Estilo de Vida"),
    PARENTING("Parenting & Motherhood", "Parentalidade e Maternidade"),
    PETS("Pets & Animals", "Pets e Animais"),
    DIY("DIY & Crafts", "Faça Você Mesmo e Artesanato"),
    GARDENING("Gardening & Landscaping", "Jardinagem e Paisagismo"),
    SPORTS("Sports & Leisure", "Esportes e Lazer"),
    AUTOMOTIVE("Automotive & Motorcycles", "Automotivo e Motos"),
    SCIENCE("Science & Popularization", "Ciência e Divulgação Científica"),
    POLITICS("Politics & Current Affairs", "Política e Atualidades"),
    RELIGION("Religion & Spirituality", "Religião e Espiritualidade"),
    PERSONAL_DEVELOPMENT("Personal Development", "Desenvolvimento Pessoal"),
    LAW("Law & Legislation", "Direito e Legislação"),
    ARCHITECTURE("Architecture & Interior Design", "Arquitetura e Design de Interiores"),
    PHOTOGRAPHY("Photography & Editing", "Fotografia e Edição"),
    AGRIBUSINESS("Agribusiness & Rural Life", "Agronegócio e Vida Rural"),
    SUSTAINABILITY("Sustainability & Ecology", "Sustentabilidade e Ecologia"),
    CRYPTO("Cryptocurrencies & Blockchain", "Criptomoedas e Blockchain"),
    GENERATIVE_AI("Generative AI", "Inteligência Artificial Generativa"),
    PODCAST("Podcast & Audio Production", "Podcast e Produção de Áudio"),
    INFLUENCER_MARKETING("Influencer Marketing", "Marketing de Influência");

    private final String descriptionEng;
    private final String descriptionPtBr;

    NichesEnum(String descriptionEng, String descriptionPtBr) {
        this.descriptionEng = descriptionEng;
        this.descriptionPtBr = descriptionPtBr;
    }

    public static NichesEnum findByAny(String niche) {
        return Arrays.stream(NichesEnum.values())
                .filter(n -> n.name().equalsIgnoreCase(niche)
                        || n.getDescriptionPtBr().equalsIgnoreCase(niche)
                        || n.getDescriptionEng().equalsIgnoreCase(niche))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Nicho não encontrado. Entre em contato com nosso suporte técnico."));
    }
}
