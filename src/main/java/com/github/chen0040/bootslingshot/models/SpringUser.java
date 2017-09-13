package com.github.chen0040.bootslingshot.models;


import java.util.Date;


/**
 * Created by xschen on 24/12/2016.
 */
public interface SpringUser {


   boolean isSuperUser();

   boolean isValid();

   void setCreatedTime(Date createdTime);

   Date getCreatedTime();

   void setUpdatedTime(Date updatedTime);

   Date getUpdatedTime();

   long getId();

   void setId(long id);


   String getUsername();

   void setUsername(String username);

   String getPassword();

   void setPassword(String password);

   String getEmail();

   void setEmail(String email);

   String getRoles();

   void setRoles(String roles);

   String getFirstName();

   void setFirstName(String firstName);

   String getLastName();

   void setLastName(String lastName);

   long getCreatedBy();

   void setCreatedBy(long createdBy);

   long getLastUpdatedBy();

   void setLastUpdatedBy(long lastUpdatedBy);

   boolean isEnabled();

   void setEnabled(boolean enabled);

   String getToken();

   void setToken(String token);

   default void copyProfile(SpringUser rhs){
      setCreatedBy(rhs.getCreatedBy());
      setCreatedTime(rhs.getCreatedTime());
      setEmail(rhs.getEmail());
      setFirstName(rhs.getFirstName());
      setLastName(rhs.getLastName());
      setLastUpdatedBy(rhs.getLastUpdatedBy());
      setPassword(rhs.getPassword());
      setRoles(rhs.getRoles());
      setUpdatedTime(rhs.getUpdatedTime());
      setUsername(rhs.getUsername());
      setEnabled(isEnabled());
      setToken(getToken());
   }

   default void copy(SpringUser rhs) {
      copyProfile(rhs);
      setId(rhs.getId());
   }

   default boolean isDemoUser() {
      return getRoles().contains("ROLE_DEMO");
   }
}
