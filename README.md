#Ordering tasking

* ###/products

1. return 201 when POST product  
 estimate: 3 min  
 real: 3 min

2. create product with parameters and find product by product id  
 e: 20 min  
  
  
3. return 201 when POST product with parameters  
 e: 5 min  
 r: 4 min
 
  
4. return 400 when POST product with invalid parameters  
 e: 8 min  
 r: 10 min
   
    
5. return url in location when POST product with parameters
  e: 5 min  
  r: 3 min
   
 
  
6. return 200 when GET products  
 e: 3 min  


7. find all products  
 e: 8 min  
  
  
8. return a list of product json when GET products  
 e: 5 min  
 r: 10 min (6-8 in totalg)
  

* ###/products/{productId}

1. return product json when GET product by product id  
 e: 10 min  
 r: 5 min
    
  
2. return 404 when GET product by product id fails  
 e: 3 min  
 r: 3 min
  
  
* ###/users

1. return 201 when POST user  
 e: 5 min  
 r: 3 min
  
  
  
 
2. create user with parameters and find user by user id  
 e: 15 min  
 r: 10 min
  
  
  
3. return 201 when POST user with parameters  
 e: 5 min  


4. return 400 when POST user with invalid parameters  
 e: 8 min  
  


5. return url in location when POST user with parameters
  e: 5 min
   

    
* ###/users/{userId}

1. return user json when GET user by user id  
e: 10 min  
 
 
2. return 404 when GET by user id not found  
 e: 5 min  
  
  
* ###/users/{userId}/orders

1. return 201 when POST order  
 e: 3 min  
  
  

2. create order without orderItems and find by user id and order id  
 e: 20 min  
    
   
3. create order with orderItems and find by user id and order id  
 e: 15 min  
  
    
    
4. return 201 when POST order with parameters  
 e: 8 min  
  
 
5. return url in location when POST order with parameters
  e: 5 min
   
   
     
6. return 400 when POST order with invalid parameters  
 e: 8 min  
  
    
7. return 200 when GET orders  
 e: 3 min  
  
  
8. find all orders  
 e: 5 min  
  
  
 
9. return a list of order json when GET orders  
 e: 10 min  
  
  
  
    
 

* ###/users/{userId}/orders/{orderId}

1. return order json when GET order by user id and order id  
 e: 10 min  
  

2. return 404 when GET order by user id and order id fails  
 e: 3 min  
  
  
   
* ###/users/{userId}/orders/{orderId}/payment

1. return 201 when POST payment  
 e: 3 min  
  
  
 
2. create payment with parameters and find payment by order id  
 e: 15 min  
  
  

3. return 201 when POST with parameters  
 e: 10 min  
  
  
4. return 400 when post with invalid parameters
  e: 6 min
  
  
5. return url in location when POST user with parameters
  e: 5 min
   

6. return payment json when GET payment by order id   
  e: 5 min  
   
  
7. return 404 when GET payment by order id fails  
  e: 3 min  
   