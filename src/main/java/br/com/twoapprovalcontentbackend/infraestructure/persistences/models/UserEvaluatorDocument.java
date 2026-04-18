package br.com.twoapprovalcontentbackend.infraestructure.persistences.models;

import br.com.twoapprovalcontentbackend.domain.AuditEntity;
import br.com.twoapprovalcontentbackend.infraestructure.enums.NichesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(value = "user_evaluator_doc")
public class UserEvaluatorDocument extends AuditEntity implements UserDetails {

    private String name;

    private String email;

    private NichesEnum niche;

    private String secret;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.secret;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
