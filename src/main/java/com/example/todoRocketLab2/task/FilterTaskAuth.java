package com.example.todoRocketLab2.task;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todoRocketLab2.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("FilterTaskAuth.doFilterInternal");

        String serveletPath = request.getServletPath();

        if (!serveletPath.startsWith("/tasks")) {
            filterChain.doFilter(request, response);
            return;
        }

        // VERIFICAR AUTENTICAÇÃO COM BASIC AUTH
        var auth = request.getHeader("Authorization");
        System.out.println(auth);

        var authEncoded = auth.substring("Basic".length()).trim();
        System.out.println(authEncoded);

        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        System.out.println(Arrays.toString(authDecoded));

        var authString = new String(authDecoded);
        System.out.println(authString);

        String[] credentials = authString.split(":");

        // usuário passou credenciais vazias ou apenas um dos valores
        if (credentials.length == 0 || credentials.length == 1) {
            response.sendError(401, "Credenciais em falta");
            return;
        }

        String username = credentials[0];
        String password = credentials[1];
        System.out.println(Arrays.toString(credentials));

        // VALIDAR USERNAME
        var user = this.userRepository.findByUsername(username);
        if (user == null){
            response.sendError(401);
            return;
        }

        // VALIDAR SENHA
        var passVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!passVerify.verified) {
            response.sendError(401);
            return;
        }

        request.setAttribute("userId", user.getId());

        filterChain.doFilter(request, response);


    }
}
