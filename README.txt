SET UP
----
Ensure you have Maven and Docker installed on your machine
free port 8080

Run commands:
Step 1: Build the application using Maven:
enter to the root of the project inposttest
run  “mvn install”

Step 2: Build the Docker image:
"docker build -t inposttest ."

Run the application in a Docker container:
"docker run -p 8080:8080 inposttest"

TECHNICAL DESCRIPTION
----
The application is designed as a REST API with a single endpoint dedicated to calculating the shopping cart
URL : "http://localhost:8080/cart/calculate" method: "POST"
Below is an example using the command line, but you can also use the attached Postman collection for convenience


1. reqeust example with applied discounts

curl -X POST http://localhost:8080/cart/calculate \
-H "Content-Type: application/json" \
-d '{
    "products": [
        {
            "uuid": "P1",
            "qty": "4",
            "price": "40"
        },
        {
            "uuid": "P2",
            "qty": "2",
            "price": "10"
        },
        {
            "uuid": "P3",
            "qty": "2",
            "price": "12.3"
        }
    ],
    "appliedDiscount": [
        {
            "type": "percentage-based",
            "productUUID": "P1",
            "threshold": "1",
            "discountValue": "50"
        },
        {
            "type": "amount-based",
            "productUUID": "P2",
            "threshold": "1",
            "discountValue": "12"
        }
    ]
}'


2. request exaple with not applied discounts 

curl -X POST http://localhost:8080/cart/calculate \
-H "Content-Type: application/json" \
-d '{
    "products": [
        {
            "uuid": "P1",
            "qty": "4",
            "price": "40"
        },
        {
            "uuid": "P2",
            "qty": "2",
            "price": "10"
        }
    ]
}'



example of response
API returns a calculated cart

{
    "cartBasePrice": 204.6,
    "discount": 88.0,
    "cartFinalPrice": 116.6,
    "calculatedCartEntryList": [
        {
            "uuid": "P1",
            "basePrice": 160,
            "discount": 80.0,
            "finalPrice": 80.0
        },
        {
            "uuid": "P2",
            "basePrice": 20,
            "discount": 8.0,
            "finalPrice": 12.0
        },
        {
            "uuid": "P3",
            "basePrice": 24.6,
            "discount": 0,
            "finalPrice": 24.6
        }
    ]
}

1. Cart level 
---
cartBasePrice - calculated price without discount 
discount - total caluclated discount for cart 
cartFinalPrice - final calculated price with discount 

2. Cart enry level 

uuid - product id 
basePrice - calculated price without discount of the product entry
discount - calculated discount of the product entry
finalPrice - final porice of product  entry 



APPLICATION RESTRICTIONS 
-----
1. cart must to  not empty products list
2. cart can not have duplicated products (uuid)
3. one product can not have more then one applied discount e.g 
    "appliedDiscount": [
        {
            "type": "percentage-based",
            "productUUID": "P1",
            "threshold": "1",
            "discountValue": "50"
        },
        {
            "type": "amount-based",
            "productUUID": "P1",
            "threshold": "1",
            "discountValue": "12"
        }
    ]
4 discount with type 'percentage-based' can not have discountValue > 99;

all not valid values would be restricted 
 
     
