package xyz.khamim.slash.ecommerce.payload;

import lombok.Data;

@Data
public class Response <T> {
    private String status = "Success";
    private T payload;
}
