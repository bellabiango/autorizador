package br.com.bellabiango.caju.autorizador.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "merchants")
public class Merchant {
    @Id
    private String id;
    private String name;
    private String mcc;
}

