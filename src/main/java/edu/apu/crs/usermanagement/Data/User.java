package edu.apu.crs.usermanagement.Data;

public class User 
{
    public String username;
    public String password;
    public String role;
    public boolean isActive;


public User(String username, String password, String role, boolean isActive)
{

     this.username = username;
     this.password = password;
     this.role=role;
     this.isActive = isActive;

}

//Getters 
public String getUsername()
{
return this.username;
}

public String getPassword()
{
return this.password;
}

public String getRole()
{
return this.role;
}

public boolean isActive()
{
return this.isActive;
}

//Setters 

public void setPassword(String password)
{
this.password = password;
}

public void setRole(String role)
{
this.role = role;
}

public void setActive(boolean active)
{
this.isActive = active;
}
@Override
public String toString()
{
 return "User{" +
         "username='" + username + '\'' +
         ", role='" + role + '\'' +
         ", active=" + isActive +
         '}';
}
}

