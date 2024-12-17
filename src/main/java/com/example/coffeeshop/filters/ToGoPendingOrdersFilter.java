package com.example.coffeeshop.filters;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.exceptions.MoreThan5PendingOrdersToGoException;
import com.example.coffeeshop.services.OrderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class ToGoPendingOrdersFilter extends OncePerRequestFilter {

    private final OrderService orderService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public ToGoPendingOrdersFilter(OrderService orderService,
                                   HandlerExceptionResolver handlerExceptionResolver) {
        this.orderService = orderService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            boolean isCapacityAchieved = orderService.countOrderByStatusIsAndType(OrderEnum.Status.PENDING,
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
