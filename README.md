[![Build Status](https://travis-ci.org/marcelghisi/AuditLog.svg?branch=master)](https://travis-ci.org/marcelghisi/AuditLog

# API AUDITLOG
Projeto OF INTERV

Please to test just create a 
mysql connection with name audit_logs and change the password and user in application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/audit_logs
spring.datasource.username=some
spring.datasource.password=some

To change the number of items in page change application.properties -> paginacao.qtd_por_pagina=

All the data inserted in nullable fields pci and pii will be applied cryptograph and to decrypt use filters as follow
http://localhost:8080/api/audits/10/?admin=admin&password=123456 (The admin and password filter code has been written just for demo

>To use MYSQL change application.properties to:

spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/audit_logs
spring.datasource.username=root
spring.datasource.password=m6a6r2c9

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.type=trace
spring.jpa.properties.hibernate.id.new_generator_mappings=false
spring.jackson.serialization.fail-on-empty-beans=false



>To USE H2 change application.properties to:

spring.jpa.hibernate.ddl-auto=create
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa



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
