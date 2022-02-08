**HOTEL**

Project represents hotel web system.
Functional depend on the user's role.

**GUEST** available opportunities:
- sing in
- create new account
- look through all rooms which are used in the hotel
- look through concrete room with it's parameters, images and reviews
- change locale

**CLIENT** available opportunities:
- sing out
- look through all rooms which are used in the hotel
- look through concrete room with it's parameters, images and reviews
- find by parameters and book room for a concrete date 
- update personal data
- change password
- replenish balance
- look through his orders
- leave review by order
- cancel order if it is not confirm by ADMIN

**ADMIN** available opportunities:
- sing out
- look through all rooms which are used in the hotel
- look through concrete room with it's parameters, images and reviews
- find by parameters and book room for a concrete date 
- change password
- find by parameters and update users
- find by different parameter and update orders
- find by different parameter, update and create rooms
- find by different parameter and update reviews
- find by different parameter, update, create and remove discounts

**System peculiarities**:
- room have a daily room state which mark the day when the room is booked or occupied
	it is generated based order status
- room rating is automatically generated based on the last 20 marks
- order status can be changed in a certain order which is described in business logic
- entity which have an important role in system can not be remove
	but there is an option not to display them on the site

![Alt-текст](https://github.com/MariyaKushel/jvd-final-project/blob/main/src/main/resources/db_shema.png "db_shema")

