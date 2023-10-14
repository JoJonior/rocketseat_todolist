

package br.com.juniorbarata.todolist.filter;

import java.io.IOException;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.juniorbarata.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                    var serveletPath = request.getServletPath();

                    if (serveletPath.startsWith("/tasks/")){

                            /// Pegar Autentificação.
                            var authorization = request.getHeader("Authorization");

                            
                            var AuthEncode = authorization.substring("Basic".length()).trim();
                            byte[] AuthDecode = Base64.getDecoder().decode(AuthEncode);

                            var authString = new String(AuthDecode);
                            System.out.println("Authorization");    

                            String[] credencias = authString.split(":");
                            String username = credencias[0];
                            String password = credencias[1];

                

                            var user = this.userRepository.findByUsername(username);
                            if(user == null){
                                response.sendError(401);
                            }else{
                                //Validar senha
                                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
                                if (passwordVerify.verified){
                                //segue Viagem
                                    request.setAttribute("idUser", user.getId());
                                    filterChain.doFilter(request, response);
                                }else{
                                    response.sendError(401);
                                }

                            }

                    }else{
                        filterChain.doFilter(request, response);
                    }
     
    }

}