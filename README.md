### Salon-It! API Documentation

## Add User:
```
POST: localhost:8080/api/auth/register
```
```
{
    "name": "Misha",
    "email": "misha40693@gmail.com",
    "phoneNumber": "9826445675",
    "password": "Anuj@2001",
    "role": "BARBER"
}
```
## Login User:
```
POST: localhost:8080/api/auth/login
```
```
{
    "username": "anujkhandelwal187@gmail.com",
    "password": "Anuj@2001"
}
```
## Add Salon:
```
POST: localhost:8080/api/salons/register
```
```
{
    "owner": { "id": 1 },
    "name": "The Style Lounge",
    "address": "12, Arera Colony, Bhopal",
    "phoneNumber": "798-456-7890",
    "openingTime": "09:00:00",
    "closingTime": "21:00:00"
}
```
 ## Add Barber:
 ```
POST: localhost:8080/api/salons/{salonId}/addBarber
```
```
{
    "salon": {"id":"2"},
    "name": "Amrut"
}
```
## Add Service:
```
POST: localhost:8080/api/salons/{salonId}/addServices
```
```

[
        {
            "name": "Waxing",
            "price": 200,
            "duration": 40
        },
        {
            "name": "Shaving",
            "price": 50,
            "duration": 20
        }
    ]
```
## Logout:
```
POST: localhost:8080/api/auth/logout
```
## View profile:
```
GET: localhost:8080/api/auth/profile?userId={userId}
```
## Update Profile:
```
PUT: localhost:8080/api/auth/profile/update?userId={userId}
```
```
{
    "name": "Anuj",
    "email": "anujkhandelwal187@gmail.com",
    "phoneNumber": "9826445618",
    "password": "Anuj@2001",
    "role": "USER"
}
```
## All Salons Appointments:
```
GET: localhost:8080/api/salons/{salonId}/allAppointments
```

## All Barbers Appointments:
```
GET:localhost:8080/api/barbers/{barberId}/allAppointments
```

## User Dashboard:
```
GET:localhost:8080/api/users/{userId}/dashboard
```

## Salon Dashboard:
```
GET: localhost:8080/api/salons/{salonId}/dashboard
```

## Update Status:
```
PUT: localhost:8080/api/salons/{salonId}/appointments/{appointmentId}/status
```
```
{
    "status": "COMPLETED"
}
```
## Barber Dashboard:
```
GET:localhost:8080/api/barbers/{barberId}/dashboard
```

## Available Slots:
```
GET: localhost:8080/api/users/salon/{salonId}/available?salonId={salonId}&date={date}&duration={duration}
```
<ol>
  <li>salonId=1</li>
  <li>date=2025-03-03</li>
  <li>duration=30</li>
</ol>

## Book Slot:
```
POST: localhost:8080/api/users/book
```
```
{
    "slotIds": [54],
    "customerId": 2,
    "serviceIds": [1,2]
}
```
