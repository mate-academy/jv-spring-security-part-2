# Spring mate.academy.spring.security part 2

- Configure DB authentication instead of In memory authentication
- Add Role entity, Dao and Service layer for it.
    ```java
      public interface RoleService {
          Role add(Role role);
      
          Role getByName(String roleName);
      }
    ```

- Configure role access to specific resources for `ADMIN` and for `USER`.
  You should configure access to __all endpoints__ in your application. Example:
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
...
``` 

HINT:
- Let's store role names as enums and add enum `RoleName` inside `Role` class.
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
```
- You can specify the different HTTP method access for the same endpoint. For example:

```plainjava
        protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/movies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/movies/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
```

__You can check yourself using this__ [checklist](https://mate-academy.github.io/jv-program-common-mistakes/java-spring/security-part-2/jv-spring-security-checklist)
