# Cinema App

```
POST: /register - all
GET: /cinema-halls - user/admin
POST: /cinema-halls - admin
GET: /movies - user/admin
POST: /movies - admin
GET: /movie-sessions/available - user/admin
POST: /movie-sessions - admin
PUT: /movie-sessions/{id} - admin
DELETE: /movie-sessions/{id} - admin
GET: /orders - user
POST: /orders/complete - user
PUT: /shopping-carts/movie-sessions - user
GET: /shopping-carts/by-user - user
GET: /users/by-email - admin

``` 
- We have role names as enums (ADMIN, USER) inside `Role` class.
- Roles and first Admin user can be injected inside DataInitializer class using annotation @PostConstruct.
```java
@PostConstruct
public void inject() {
    Role adminRole = new Role();
    adminRole.setRoleName(Role.RoleName.ADMIN);
    roleService.add(adminRole);
    Role userRole = new Role();
    userRole.setRoleName(Role.RoleName.USER);
    roleService.add(userRole);
    User user = new User();
    user.setEmail("admin@i.ua");
    user.setPassword("admin123");
    user.setRoles(Set.of(adminRole));
    userService.add(user);
}


