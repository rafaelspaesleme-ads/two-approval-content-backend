package br.com.twoapprovalcontentbackend.infraestructure.configs.securities.services;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories.UserEvaluatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigUserDetailsService implements UserDetailsService {

    private final UserEvaluatorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário avaliador não encontrado."));
    }
}
