[![Build Status](https://travis-ci.org/marcelghisi/AuditLog.svg?branch=master)](https://travis-ci.org/marcelghisi/AuditLog

# API AUDITLOG
Projeto OF INTERV


For a better avaliation 

#To change the number of pages
>>To change the number of items in page change application.properties -> paginacao.qtd_por_pagina=

#To request decryption for the pci dss(Payment Card Industry Data Security Standard) and pii(Personally identifiable information) colunms data encrypted use the URL below
```
http://localhost:8080/api/audits/10/?admin=admin&password=123456 
```
>All the data inserted in nullable fields pci and pii will be applied cryptograph and to decrypt use filters as demonstreted in the url above
>>Pss . The admin and password filter code has bean written just for demo purposes

#To test with mysql
```
*Create a mysql database
*Create a user and password for database
*Change application.properties file with below data


spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/audit_logs
spring.datasource.username=some
spring.datasource.password=some

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.type=trace
spring.jpa.properties.hibernate.id.new_generator_mappings=false
spring.jackson.serialization.fail-on-empty-beans=false
```

#To test with H2
```
*the project comes with H2 configured but if you swiched to mysql use the code above instead below

spring.jpa.hibernate.ddl-auto=create
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```


>To include events in log configure postman with the header and body json below
```
POST http://localhost:8080/api/audits
Content-Type
BODY
{
	"date":"2019-04-08T19:16:39.000+0000",
	"event":"compra realizada com sucesso",
	"user":"User1",
	"origin":"Consumer",
	"pci":"Card Number 0000 0123 1234 1233, VA 0205"
}

Response
{
    "data": {
        "id": 23,
        "date": "2019-04-08T19:16:39.000+0000",
        "user": "User1",
        "event": "compra realizada com sucesso",
        "origin": "Consumer",
        "pci": "zu2gSYfxSNXkkQurJ5YlAfc1xUWqXLkzrL8+t5OZ5jqk1ISZyNiF4mcCgGEMiIUL",
        "pii": "vkfcXfmRP3gGSsftpuSMsg=="
    },
    "errors": []
}

GET BY USER http://localhost:8080/api/audits/filter/user/User1?pag=1 and order using  &dir=ASC
GET BY CONSUMER http://localhost:8080/api/audits/filter/type/Consumer?pag=1 and order using  &dir=ASC
GET ALL http://localhost:8080/api/audits -> for page please include /?pag=0 and order using  &dir=ASC


RESPONSE
{
    "data": {
        "content": [
            {
                "id": 23,
                "date": "2019-04-08T19:16:39.000+0000",
                "user": "User1",
                "event": "compra realizada com sucesso",
                "origin": "Consumer",
                "pci": "zu2gSYfxSNXkkQurJ5YlAfc1xUWqXLkzrL8+t5OZ5jqk1ISZyNiF4mcCgGEMiIUL",
                "pii": "vkfcXfmRP3gGSsftpuSMsg=="
            },
            {
                "id": 22,
                "date": "2019-04-08T19:16:39.000+0000",
                "user": "User1",
                "event": "compra realizada com sucesso",
                "origin": "Consumer",
                "pci": "zu2gSYfxSNXkkQurJ5YlAfc1xUWqXLkzrL8+t5OZ5jqk1ISZyNiF4mcCgGEMiIUL",
                "pii": "vkfcXfmRP3gGSsftpuSMsg=="
            }
        ],
        "pageable": {
            "sort": {
                "unsorted": false,
                "sorted": true,
                "empty": false
            },
            "offset": 0,
            "pageSize": 2,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "totalPages": 12,
        "last": false,
        "totalElements": 23,
        "size": 2,
        "number": 0,
        "first": true,
        "numberOfElements": 2,
        "sort": {
            "unsorted": false,
            "sorted": true,
            "empty": false
        },
        "empty": false
    },
    "errors": []
}
```
