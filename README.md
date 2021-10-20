# AccountManagerAPI
Simple Spring boot application to demo API for Account Manager

### Basic API Information
| Method | Path | Usage | Sample Request  | Sample Response |
| --- | --- | --- | --- | --- |
| GET | /authenticate | get jwt token to be used in /balance and /transfer request | curl --location --request POST 'http://localhost:8080/authenticate' \ <br>--header 'Content-Type: application/json' \ <br>--data-raw '{<br>"username": "12345678",<br>"password": "1234567890"<br>}'| {<br>    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3OCIsImV4cCI6MTYzNDc3NTQ2NCwiaWF0IjoxNjM0NzM5NDY0fQ.KfLcFdHOkx4PnIaUUpM_teVhsxCUaYsolLT8ixU8yOE"<br>} | 
| GET | /balance | retrieve account balance | curl --location --request GET 'http://localhost:8080/balance' \ <br>--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3OCIsImV4cCI6MTYzNDc3NTQ2NCwiaWF0IjoxNjM0NzM5NDY0fQ.KfLcFdHOkx4PnIaUUpM_teVhsxCUaYsolLT8ixU8yOE' \ <br>--data-raw '' | 1000050.00 | 
| POST | /transfer | create transaction | curl --location --request POST 'http://localhost:8080/transfer' \ <br>--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3OCIsImV4cCI6MTYzNDc3NTQ2NCwiaWF0IjoxNjM0NzM5NDY0fQ.KfLcFdHOkx4PnIaUUpM_teVhsxCUaYsolLT8ixU8yOE' \ <br>--header 'Content-Type: application/json' \ <br>--data-raw '{<br>"amount": 50,<br>"toAccount":"88888888"<br>}' | SUCCESS |


