package net.nlt.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headerNames;
    private String remoteHost;
    private String queryString;
    private String attributeNames;

    public RequestInfo(HttpServletRequest request) {
        this.attributeNames = convertEnumerationToString(request.getAttributeNames());
        this.headerNames = convertEnumerationToString(request.getHeaderNames());
        this.remoteHost = request.getRemoteHost();
        this.queryString = request.getQueryString();
    }

    private String convertEnumerationToString(Enumeration<String> stringEnumeration) {
        StringBuilder builder = new StringBuilder();
        while (stringEnumeration.hasMoreElements()) {
            builder.append(stringEnumeration.nextElement()).append("\n");
        }
        return builder.toString();
    }

}
