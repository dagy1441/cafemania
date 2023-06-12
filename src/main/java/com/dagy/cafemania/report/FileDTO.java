package com.dagy.cafemania.report;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String fileContent;
    private String fileName;
}
