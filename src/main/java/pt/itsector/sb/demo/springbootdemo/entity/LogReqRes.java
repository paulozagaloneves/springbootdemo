package pt.itsector.sb.demo.springbootdemo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "LOG_REQ_RES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogReqRes {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private long id;

    @Column(name = "CLIENT_IP", length = 255)
    private String clientIp;

    @Column(name = "ENDPOINT", length = 4000)
    private String uri;

    @Column(name = "HTTP_METHOD")
    private String httpMethod;

    @Column(name = "REQUEST")
    @Lob
    private String request;

    @Column(name = "RESPONSE")
    @Lob
    private String response;

    @Column(name = "REQUEST_HEADERS")
    @Lob
    private String requestHeaders;

    @Column(name = "RESPONSE_HEADERS")
    @Lob
    private String responseHeaders;

    @CreationTimestamp
    @Column(name="TIMESTAMP_REQ_RES")
    private Date createdon;

}