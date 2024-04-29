package com.example.coffeeshop.filters;

import com.example.coffeeshop.enums.OrderEnum;
import com.example.coffeeshop.repositories.OrderRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ToGoPendingOrdersFilter extends OncePerRequestFilter {

    private final OrderRepository orderRepository;

    @Autowired
    public ToGoPendingOrdersFilter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        boolean isCapacityAchieved = this.orderRepository.countOrderByStatusIsAndType(OrderEnum.Status.PENDING,
                OrderEnum.Type.TO_GO) >= 5;
        if (request.getRequestURI().equals("/api/order/to-go") && isCapacityAchieved) {
            throw new RuntimeException("We can't take your order right now...");
        }
        filterChain.doFilter(request, response);
    }
}
