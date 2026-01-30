/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r2m.praticar.taskapplication.models.Address;

/**
 *
 * @author caioq
 */
public interface AddressRepository extends JpaRepository<Address, Long> {}
