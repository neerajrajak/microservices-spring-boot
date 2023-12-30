package com.ordermicros.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordermicros.example.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
