package com.example.coffeeshop.filters;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.exceptions.MoreThan5PendingOrdersToGoException;
import com.example.coffeeshop.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class ToGoPendingOrdersFilter extends OncePerRequestFilter {

    private final OrderService orderService;
    private final HttpServletResponse httpServletResponse;
    private final ObjectMapper objectMapper;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public ToGoPendingOrdersFilter(OrderService orderService,
                                   HttpServletResponse httpServletResponse,
                                   ObjectMapper objectMapper, HandlerExceptionResolver handlerExceptionResolver) {
        this.orderService = orderService;
        this.httpServletResponse = httpServletResponse;
        this.objectMapper = objectMapper;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            boolean isCapacityAchieved = this.orderService.countOrderByStatusIsAndType(OrderEnum.Status.PENDING,
                    OrderEnum.Type.TO_GO) >= 5;
            if (request.getRequestURI().equals("/api/v1/order/to-go") && isCapacityAchieved) {
                throw new MoreThan5PendingOrdersToGoException();
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }

    }
}
