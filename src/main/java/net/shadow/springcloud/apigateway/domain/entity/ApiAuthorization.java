package net.shadow.springcloud.apigateway.domain.entity;

import lombok.*;
import net.shadow.springcloud.apigateway.domain.util.DBString2ListConverter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "api_authorization")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiAuthorization {
    @Id
    @GeneratedValue
    private Integer id;
    private String path;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    @Convert(converter = DBString2ListConverter.class)
    private List<String> params;

    @Convert(converter = DBString2ListConverter.class)
    private List<String> headers;

    @Convert(converter = DBString2ListConverter.class)
    private List<String> authorities;
}

