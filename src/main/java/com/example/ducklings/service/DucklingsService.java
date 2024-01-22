package com.example.ducklings.service;


import com.example.ducklings.models.InvoiceModel;
import com.example.ducklings.repository.DucklingsRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DucklingsService {
    private DucklingsRepository ducklingsRepository = new DucklingsRepository();

    public List<InvoiceModel> getAll(String owner) {
        return ducklingsRepository.getAll(owner);
    }

    public void create(String title, Date date, String description, String category, BigDecimal price, String owner) {
        ducklingsRepository.create(title, (java.sql.Date) date, description, category, price, owner);
    }

    public void delete(int id) {
        ducklingsRepository.delete(id);
    }

    public void update(int id, String title, Date date, String description, String category, BigDecimal price, String owner){
        ducklingsRepository.update(id, title, (java.sql.Date) date, description, category, price, owner);
    }

}
