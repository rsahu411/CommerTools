package com.CommerceTool.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesDTO {

    private String url;
    private String label;
    private Integer h;
    private Integer w;
}
