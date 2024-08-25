package com.utkubayrak.jetbusservice.component;

import com.utkubayrak.jetbusservice.client.user.UserClient;
import com.utkubayrak.jetbusservice.client.user.dto.response.RoleEnum;
import com.utkubayrak.jetbusservice.client.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    private final ApplicationContext applicationContext;

    @Autowired
    public RoleInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");

        if (jwt != null && !jwt.isEmpty()) {
            // UserClient'ı applicationContext'ten alın
            UserClient userClient = applicationContext.getBean(UserClient.class);

            // Kullanıcı bilgilerini al
            ResponseEntity<UserResponse> userResponseEntity = userClient.getUserByJwt(jwt);
            UserResponse user = userResponseEntity.getBody();

            // Kullanıcının rolünü kontrol et
            if (user != null && RoleEnum.ADMIN.equals(user.getRole())) {
                return true; // Eğer kullanıcı admin ise, işlemi gerçekleştir
            }
        }

        // Eğer kullanıcı admin değilse, 403 Forbidden döndür
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Erişim reddedildi!");
        return false; // İsteğin devam etmesini engelle
    }
}
